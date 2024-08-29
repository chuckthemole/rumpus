package com.rumpus.rumpus.tools;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.rumpus.common.OCR.OcrTesseract;
import com.rumpus.rumpus.models.RumpusUser;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String out = OcrTesseract.doOCR("src/main/tmp/test.pdf");
        System.out.println("OCR complete!");
        System.out.println(out);
        // RumpusUser user = RumpusUser.create("USERNAME", "CHANGE_PASSWORD", "EMAIL");
        // Comparator<BigDecimal> comp = (a, b) -> b.compareTo(a);
        // PriorityQueue<BigDecimal> pq = new PriorityQueue<BigDecimal>(comp);
        // // create 5 BigDecimal objects to store in the PriorityQueue
        // BigDecimal bd1 = new BigDecimal("10.0");
        // BigDecimal bd2 = new BigDecimal("2.0");
        // BigDecimal bd3 = new BigDecimal("-3.0");
        // BigDecimal bd4 = new BigDecimal("42.0");
        // BigDecimal bd5 = new BigDecimal("-50.0");

        // // add the 5 BigDecimal objects to the PriorityQueue
        // pq.add(bd1);
        // pq.add(bd2);
        // pq.add(bd3);
        // pq.add(bd4);
        // pq.add(bd5);

        // // print the PriorityQueue
        // System.out.println("PriorityQueue: " + pq);

        // java.util.Map<String, String> map = new java.util.HashMap<>();
        // Comparator<String> comp2 = (a, b) -> b.compareTo(a);
        // java.util.Set<String> set = new java.util.TreeSet<String>(comp2);
    }
}
