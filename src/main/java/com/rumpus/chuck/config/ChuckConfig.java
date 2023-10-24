package com.rumpus.chuck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Config.AbstractCommonConfig;

/**
 * TODO: think about overriding dataSource() to use a different database for different packages.
 */
@Configuration
@ComponentScan("com.rumpus.chuck")
public class ChuckConfig extends AbstractCommonConfig {

}
