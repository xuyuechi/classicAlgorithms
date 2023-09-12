package org.algorithms.labuladong;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int A = scanner.nextInt();
        int B = scanner.nextInt();
        int C = scanner.nextInt();
        int tutu = 1;
        int money = 0;
        boolean flag = false;
        if (A == 1) {
            System.out.println(Math.ceil(C / (double) A));
            return;
        }
        while (money < C) {
            if (money + A - 1 >= C) {
                money++;
                tutu++;
                continue;
            }
            money += A - 1;
            tutu += A - 1;


            money += B;
            tutu++;

        }
        System.out.println(tutu - 1);
    }
}
