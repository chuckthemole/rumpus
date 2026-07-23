#!/usr/bin/env python3
"""
runner.py - Developer CLI wrapper around Gradle for the Rumpus project.

This replaces the old collection of ad-hoc shell string commands
(build, buildDebug, buildTest, run_chuck, run_bs, ...) with a small
set of subcommands that take flags instead.

Examples
--------
    python runner.py build
    python runner.py build --debug --tests
    python runner.py build --xlint --refresh
    python runner.py run
    python runner.py run chuck
    python runner.py run buildshift --debug
    python runner.py test
    python runner.py dependencies
    python runner.py tool JsonFormatter
    python runner.py tool JsonFormatter input.json
    python runner.py dev
    python runner.py dev chuck
"""

from __future__ import annotations

import argparse
import subprocess
import sys
from pathlib import Path

# --------------------------------------------------------------------------
# Configuration
# --------------------------------------------------------------------------

# Project root is wherever this script lives; every other path is relative
# to it so the script works regardless of the caller's current directory.
ROOT_DIR = Path(__file__).resolve().parent

SRC_DIR = ROOT_DIR / "src" / "main" / "java"
BUILD_CLASSES_DIR = ROOT_DIR / "build" / "classes" / "java" / "main"
TOOLS_DIR = ROOT_DIR / "tools"
TOOLS_OUT_DIR = TOOLS_DIR / "out"

LOG_DIR = ROOT_DIR / "src" / "main" / "java" / "com" / "rumpus" / "rumpus" / "log"
TEST_LOG_DIR = ROOT_DIR / "src" / "test" / "java" / "com" / "rumpus" / "rumpus" / "log"

BUILD_LOG = LOG_DIR / "build.log"
SPRING_LOG = LOG_DIR / "spring.log"
DEPENDENCIES_LOG = LOG_DIR / "dependencies.log"
TEST_LOG = TEST_LOG_DIR / "test.log"

TOOLS_PACKAGE = "com.rumpus.rumpus.tools"

# Gradle wrapper lives one directory up from this script (".././gradlew" in
# the original), but resolving it relative to ROOT_DIR keeps things correct
# no matter where the script is invoked from.
GRADLEW = ROOT_DIR.parent / "gradlew"

# Valid Spring Boot apps this project can run.
APPS = {
    "rumpus": "rumpus",
    "chuck": "chuck",
    "buildshift": "buildshift",
}
DEFAULT_APP = "rumpus"

# ANSI colors for simple console highlighting.
COLOR_RED = "\x1b[31m"
COLOR_YELLOW = "\x1b[33m"
COLOR_CYAN = "\x1b[36m"
COLOR_RESET = "\x1b[0m"


# --------------------------------------------------------------------------
# Utility functions
# --------------------------------------------------------------------------

def ensure_parent_dirs(*paths: Path) -> None:
    """Create parent directories for each given path if they don't exist."""
    for path in paths:
        path.parent.mkdir(parents=True, exist_ok=True)


def colorize_line(line: str) -> str:
    """Color a line of output if it looks like an error or warning."""
    lowered = line.lower()
    if "error" in lowered:
        return f"{COLOR_RED}{line}{COLOR_RESET}"
    if "warning" in lowered:
        return f"{COLOR_YELLOW}{line}{COLOR_RESET}"
    return line


def run_command(cmd: list[str], log_file: Path | None = None, cwd: Path | None = None) -> int:
    """
    Run a command as a list of arguments (no shell string parsing, no
    shell injection risk). Streams output to the console with simple
    color highlighting, and optionally tees it to a log file.

    Returns the process's exit code.
    """
    print(f"{COLOR_CYAN}$ {' '.join(cmd)}{COLOR_RESET}")

    if log_file is not None:
        ensure_parent_dirs(log_file)
        log_handle = log_file.open("w", encoding="utf-8")
    else:
        log_handle = None

    try:
        process = subprocess.Popen(
            cmd,
            cwd=str(cwd) if cwd else None,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            bufsize=1,
        )

        assert process.stdout is not None
        for line in process.stdout:
            line = line.rstrip("\n")
            print(colorize_line(line))
            if log_handle is not None:
                log_handle.write(line + "\n")

        process.wait()
        return process.returncode

    except FileNotFoundError as exc:
        print(f"{COLOR_RED}Error: command not found - {exc}{COLOR_RESET}")
        return 1
    finally:
        if log_handle is not None:
            log_handle.close()


# --------------------------------------------------------------------------
# Gradle commands
# --------------------------------------------------------------------------

def build_command(args: argparse.Namespace) -> int:
    """Build the project, with optional flags mirroring the old variants."""
    cmd = [str(GRADLEW), "clean", "build"]

    if args.refresh:
        cmd.append("--refresh-dependencies")
    if args.debug:
        cmd.append("--debug")
    if args.info:
        cmd.append("--info")
    if not args.tests:
        cmd.extend(["-x", "test"])
    if args.xlint:
        cmd.append("-Xlint:unchecked")

    return run_command(cmd, log_file=BUILD_LOG, cwd=ROOT_DIR)


def run_command_app(args: argparse.Namespace) -> int:
    """Start Spring Boot for the given app."""
    app = APPS.get(args.app, DEFAULT_APP)
    cmd = [str(GRADLEW), "bootRun", f"--args=--app={app}"]

    if args.debug:
        cmd.append("--debug")

    return run_command(cmd, log_file=SPRING_LOG, cwd=ROOT_DIR)


def test_command(_args: argparse.Namespace) -> int:
    """Run the Gradle test suite."""
    cmd = [str(GRADLEW), "test"]
    return run_command(cmd, log_file=TEST_LOG, cwd=ROOT_DIR)


def dependencies_command(_args: argparse.Namespace) -> int:
    """Print the Gradle dependency report."""
    cmd = [str(GRADLEW), "dependencies"]
    return run_command(cmd, log_file=DEPENDENCIES_LOG, cwd=ROOT_DIR)


def dev_command(args: argparse.Namespace) -> int:
    """Convenience command: build, then run the chosen app."""
    build_args = argparse.Namespace(
        refresh=False, debug=False, info=False, tests=False, xlint=False
    )
    build_result = build_command(build_args)
    if build_result != 0:
        print(f"{COLOR_RED}Build failed, aborting before run.{COLOR_RESET}")
        return build_result

    run_args = argparse.Namespace(app=args.app, debug=args.debug)
    return run_command_app(run_args)


# --------------------------------------------------------------------------
# Java tool commands
# --------------------------------------------------------------------------

def get_gradle_classpath() -> str:
    """Fetch the project's runtime classpath via Gradle."""
    result = subprocess.run(
        [str(GRADLEW), "-q", "printClasspath"],
        cwd=str(ROOT_DIR),
        capture_output=True,
        text=True,
        check=False,
    )
    if result.returncode != 0:
        print(f"{COLOR_RED}Failed to fetch Gradle classpath:{COLOR_RESET}\n{result.stderr}")
        return ""
    return result.stdout.strip()


def tool_command(args: argparse.Namespace) -> int:
    """
    Compile and run a standalone Java tool from the tools package,
    optionally piping in an input file.
    """
    tool_name = args.tool_name
    input_file = args.input_file

    TOOLS_OUT_DIR.mkdir(parents=True, exist_ok=True)

    classpath = get_gradle_classpath()
    if not classpath:
        return 1

    full_classpath = f"{BUILD_CLASSES_DIR}:{classpath}"
    source_file = SRC_DIR / "com" / "rumpus" / "rumpus" / "tools" / f"{tool_name}.java"

    compile_log = TOOLS_OUT_DIR / f"{tool_name}_compile.out"
    run_log = TOOLS_OUT_DIR / f"{tool_name}_run.out"

    # Compile.
    compile_cmd = [
        "javac", "-cp", full_classpath, "-d", str(TOOLS_DIR), str(source_file),
    ]
    compile_result = run_command(compile_cmd, log_file=compile_log, cwd=ROOT_DIR)
    if compile_result != 0:
        print(f"{COLOR_RED}Compilation of {tool_name} failed. See {compile_log}{COLOR_RESET}")
        return compile_result

    # Run.
    run_cp = f"{BUILD_CLASSES_DIR}:{classpath}:{TOOLS_DIR}"
    run_cmd = ["java", "-cp", run_cp, f"{TOOLS_PACKAGE}.{tool_name}"]

    if input_file:
        input_path = Path(input_file)
        if not input_path.exists():
            print(f"{COLOR_RED}Input file not found: {input_path}{COLOR_RESET}")
            return 1
        ensure_parent_dirs(run_log)
        with input_path.open("r", encoding="utf-8") as stdin_file, \
                run_log.open("w", encoding="utf-8") as log_handle:
            print(f"{COLOR_CYAN}$ {' '.join(run_cmd)} < {input_path}{COLOR_RESET}")
            process = subprocess.run(
                run_cmd,
                cwd=str(ROOT_DIR),
                stdin=stdin_file,
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                text=True,
                check=False,
            )
            print(process.stdout)
            log_handle.write(process.stdout)
            return process.returncode
    else:
        return run_command(run_cmd, log_file=run_log, cwd=ROOT_DIR)


# --------------------------------------------------------------------------
# CLI
# --------------------------------------------------------------------------

def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(
        prog="runner.py",
        description="Developer CLI wrapper around Gradle for the Rumpus project.",
    )
    subparsers = parser.add_subparsers(dest="command", required=True)

    # build
    build_p = subparsers.add_parser("build", help="Build the project")
    build_p.add_argument("--debug", action="store_true", help="Run Gradle with --debug")
    build_p.add_argument("--info", action="store_true", help="Run Gradle with --info")
    build_p.add_argument("--tests", action="store_true", help="Include tests (omit -x test)")
    build_p.add_argument("--refresh", action="store_true", help="Use --refresh-dependencies")
    build_p.add_argument("--xlint", action="store_true", help="Enable -Xlint:unchecked")
    build_p.set_defaults(func=build_command)

    # run
    run_p = subparsers.add_parser("run", help="Run a Spring Boot app")
    run_p.add_argument(
        "app", nargs="?", default=DEFAULT_APP, choices=APPS.keys(),
        help=f"App to run (default: {DEFAULT_APP})",
    )
    run_p.add_argument("--debug", action="store_true", help="Run with --debug")
    run_p.set_defaults(func=run_command_app)

    # test
    test_p = subparsers.add_parser("test", help="Run the Gradle test suite")
    test_p.set_defaults(func=test_command)

    # dependencies
    deps_p = subparsers.add_parser("dependencies", help="Print the Gradle dependency report")
    deps_p.set_defaults(func=dependencies_command)

    # tool
    tool_p = subparsers.add_parser("tool", help="Compile and run a standalone Java tool")
    tool_p.add_argument("tool_name", help="Name of the tool class, e.g. JsonFormatter")
    tool_p.add_argument("input_file", nargs="?", default=None, help="Optional input file")
    tool_p.set_defaults(func=tool_command)

    # dev
    dev_p = subparsers.add_parser("dev", help="Build then run an app in one step")
    dev_p.add_argument(
        "app", nargs="?", default=DEFAULT_APP, choices=APPS.keys(),
        help=f"App to run (default: {DEFAULT_APP})",
    )
    dev_p.add_argument("--debug", action="store_true", help="Run with --debug")
    dev_p.set_defaults(func=dev_command)

    return parser


def main() -> int:
    parser = build_parser()
    args = parser.parse_args()
    return args.func(args)


if __name__ == "__main__":
    sys.exit(main())