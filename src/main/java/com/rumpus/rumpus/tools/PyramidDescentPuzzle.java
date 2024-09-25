package com.rumpus.rumpus.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * PyramidDescentPuzzle
 * 
 * A program that finds all paths in a pyramid that multiply to a target value.
 */
public class PyramidDescentPuzzle {

    private static final String LEFT = "L";
    private static final String RIGHT = "R";
    private int target = -1;
    private List<String> paths = new ArrayList<>();
    private List<List<Integer>> pyramid;


    private int product(int... values) {
        int product = 1;
        for(int value : values) {
            product *= value;
        }
        return product;
    }

    /**
     * Recursive function to find all paths in the pyramid that multiply to the target value
     * 
     * @param parentRow index of the parent row
     * @param parentColumn index of the parent column
     * @param currentProduct product of the path so far
     * @param pathBuilder string builder to build the path
     * @return the path if found, otherwise NOT_A_PATH
     */
    public void findPyramidPaths(
        int parentRow,
        int parentColumn,
        int currentProduct,
        String currentPath) {

            // Debug
            // System.out.println("Row: " + parentRow + ", Column: " + parentColumn + ", Product: " + currentProduct + ", Path: " + currentPath);

            // Base case
            if(currentProduct > this.target) {
                return;
            }
            if(parentRow == this.pyramid.size() - 1) {
                if(currentProduct == this.target) {
                    this.paths.add(currentPath);
                }
                return;
            }

            // Recursive case
            StringBuilder pathBuilder = new StringBuilder();
            pathBuilder.append(currentPath);
            int leftChild = this.pyramid.get(parentRow + 1).get(parentColumn);
            int rightChild = this.pyramid.get(parentRow + 1).get(parentColumn + 1);
            findPyramidPaths(
                parentRow + 1,
                parentColumn, 
                product(currentProduct, leftChild),
                pathBuilder.append(LEFT).toString());

            pathBuilder = new StringBuilder();
            pathBuilder.append(currentPath);
            findPyramidPaths(
                parentRow + 1,
                parentColumn + 1,
                product(currentProduct, rightChild),
                pathBuilder.append(RIGHT).toString());
    }

    public void setPyramid(List<List<Integer>> pyramid) {
        this.pyramid = pyramid;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void printPaths() {
        // System.out.println("Paths (" + this.paths.size() + ") that multiply to the target value:");
        for(String path : this.paths) {
            System.out.println(path);
        }
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            PyramidDescentPuzzle pyramidDescentPuzzle = new PyramidDescentPuzzle();

            // Read and set the target value
            final String line = br.readLine();
            // System.out.println(line);
            final String[] parts = line.split(" ");
            final int target = Integer.parseInt(parts[1]);
            // System.out.println("The target value: " + target);
            pyramidDescentPuzzle.setTarget(target);

            // Read and set the pyramid values
            List<List<Integer>> pyramid = new ArrayList<>();
            String input;
            // int row = 0;
            while((input = br.readLine()) != null) {
                // System.out.println("Row " + row + ": " + input);
                // row++;
                final String[] pyramidLevel = input.split(",");
                List<Integer> pyramidLevelInt = new ArrayList<>();
                for (int i = 0; i < pyramidLevel.length; i++) {
                    pyramidLevelInt.add(Integer.parseInt(pyramidLevel[i]));
                }
                pyramid.add(pyramidLevelInt);
            }
            pyramidDescentPuzzle.setPyramid(pyramid);

            // Find all paths that multiply to the target value
            pyramidDescentPuzzle.findPyramidPaths(0, 0, pyramid.get(0).get(0), "");
            pyramidDescentPuzzle.printPaths();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
