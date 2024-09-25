package com.rumpus.rumpus.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Hist {

    /**
     * Array of ages
     */
    private int[] ages;

    /**
     * Keeps track of quantity of ages in each bucket
     */
    private int[] buckets;

    /**
     * Number of buckets
     */
    private static final int NUMBER_OF_BUCKETS = 10;

    Hist(int[] ages) {
        this.ages = ages;
        this.buckets = new int[NUMBER_OF_BUCKETS];
        for(int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            this.buckets[i] = 0;
        }
    }

    public BigDecimal[] get_histo() {

        // populate buckets
        for(int age : this.ages) {
            this.buckets[age / NUMBER_OF_BUCKETS]++;
        }

        BigDecimal[] histo = new BigDecimal[NUMBER_OF_BUCKETS];
        for(int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            histo[i] = BigDecimal.valueOf(buckets[i])
                .divide(
                    BigDecimal.valueOf(ages.length),
                        2,
                        RoundingMode.HALF_UP);
        }
        return histo;
    }

    public static boolean test1() {
        // 0.50
        // 0.00
        // 0.00
        // 0.00
        // 0.17
        // 0.00
        // 0.00
        // 0.00
        // 0.17
        // 0.17

        BigDecimal[] expectedHisto = {
            BigDecimal.valueOf(0.50),
            BigDecimal.valueOf(0.00),
            BigDecimal.valueOf(0.00),
            BigDecimal.valueOf(0.00),
            BigDecimal.valueOf(0.17),
            BigDecimal.valueOf(0.00),
            BigDecimal.valueOf(0.00),
            BigDecimal.valueOf(0.00),
            BigDecimal.valueOf(0.17),
            BigDecimal.valueOf(0.17)
        };

        int[] ages = {1, 2, 3, 40, 80, 90};
        Hist hist = new Hist(ages);
        BigDecimal[] actualeHisto = hist.get_histo();

        // assertTrue(expectedHisto.length == actualeHisto.length);

        printHisto(actualeHisto);
        return expectedHisto.equals(actualeHisto);
    }

    public void printBuckets() {
        if(buckets == null || buckets.length == 0) {
            System.out.println("Buckets is empty");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            sb.append(buckets[i]);
            sb.append(",");
        }
        // remove last comma
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" }");
        System.out.println(sb.toString());
    }

    public static void printHisto(BigDecimal[] histo) {
        if(histo == null || histo.length == 0) {
            System.out.println("Histo is empty");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(int i = 0; i < Hist.NUMBER_OF_BUCKETS; i++) {
            sb.append(histo[i]);
            sb.append(",");
        }
        // remove last comma
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" }");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        System.out.println(test1());
    }
}
