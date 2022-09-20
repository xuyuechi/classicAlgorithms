package org.algorithms.search;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MagicSquare {
    @Test
    public void testForMagicSquare() {
        createMagicSquareByForce(3);
        createMagicSquareByMethod(6);
    }

    //罗伯法
    public int[][] RobertMethod(int n, int begin, int end) {
        int[][] work = new int[n][n];
        work[0][n / 2] = begin;
        int startRow = 0;
        int startCol = n / 2;
        for (int i = begin + 1; i <= end; i++) {
            if ((startRow - 1) < 0 && (startCol + 1) < n) {
                work[n - 1][startCol + 1] = i;
                startRow = n - 1;
                startCol = startCol + 1;
            } else if ((startRow - 1) >= 0 && (startCol + 1) >= n) {
                work[startRow - 1][0] = i;
                startRow = startRow - 1;
                startCol = 0;
            } else if ((startRow - 1) < 0 && (startCol + 1) >= n) {
                work[startRow + 1][startCol] = i;
                startRow = startRow + 1;
            } else if (work[startRow - 1][startCol + 1] != 0 /*&& (startRow+1)<n*/) {
                work[startRow + 1][startCol] = i;
                startRow = startRow + 1;
            } else {
                work[startRow - 1][startCol + 1] = i;
                startRow = startRow - 1;
                startCol = startCol + 1;
            }
        }
        return work;
    }

    public void createMagicSquareByMethod(int n) {
        if (n % 2 == 1) {
            //采用罗伯法
            int[][] result = RobertMethod(n, 1, n * n);
            System.out.println("更好的生成幻方的算法（奇数阶）---罗伯法：");
            for (int[] line : result) {
                System.out.println(Arrays.toString(line));
            }
        }
        if (n % 4 == 0) {
            //采用海尔法
            int[][] work = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    work[i][j] = i * n + j + 1;
                }
            }
            for (int i = 0; i < n / 2; i++) {
                work[i][i] = (n * n + 1) - work[i][i];
                work[i][n / 2 - i - 1] = (n * n + 1) - work[i][n / 2 - i - 1];
                work[i][i + 4] = (n * n + 1) - work[i][i + 4];
                work[i][7 - i] = (n * n + 1) - work[i][7 - i];
            }
            for (int i = n / 2; i < n; i++) {
                work[i][i] = (n * n + 1) - work[i][i];
                work[i][n - i - 1 + n / 2] = (n * n + 1) - work[i][n - i - 1 + n / 2];
                work[i][i - 4] = (n * n + 1) - work[i][i - 4];
                work[i][7 - i] = (n * n + 1) - work[i][7 - i];
            }
            System.out.println("更好的生成幻方的算法（双偶数阶）---海尔法：");
            for (int[] line : work) {
                System.out.println(Arrays.toString(line));
            }
        }
        if (n % 2 == 0 && n % 4 != 0) {
            //采用斯特拉兹法
            int[][] A = RobertMethod(n / 2, 1, (n * n) / 4);//左上
            int[][] B = RobertMethod(n / 2, (n * n) / 2 + 1, (3 * (n * n)) / 4);//右上
            int[][] C = RobertMethod(n / 2, (3 * (n * n)) / 4 + 1, n * n);//左下
            int[][] D = RobertMethod(n / 2, ((n * n) / 4) + 1, (n * n) / 2);//右下
            //开始AC交换
            int k = (n - 2) / 4;
            for (int i = 0; i < n / 2; i++) {
                if (i != n / 4) {
                    for (int j = 0; j < k; j++) {
                        int temp = A[i][j];
                        A[i][j] = C[i][j];
                        C[i][j] = temp;
                    }
                } else {
                    for (int j = n / 4; j < k + n / 4; j++) {
                        int temp = A[i][j];
                        A[i][j] = C[i][j];
                        C[i][j] = temp;
                    }
                }
            }
            //开始BD交换
            int m = k - 1;
            for (int i = 0; i < n / 2; i++) {
                for (int j = n / 4; j > n / 4 - m; j--) {
                    int temp = B[i][j];
                    B[i][j] = D[i][j];
                    D[i][j] = temp;
                }
            }
            System.out.println("更好的生成幻方的算法（单偶数阶）---斯特拉兹法：");
            for(int i=0;i<n/2;i++){
                System.out.print(Arrays.toString(A[i]));
                System.out.print(Arrays.toString(B[i]));
                System.out.println();
            }
            for(int i=0;i<n/2;i++){
                System.out.print(Arrays.toString(C[i]));
                System.out.print(Arrays.toString(D[i]));
                System.out.println();
            }
        }
    }

    public void createMagicSquareByForce(int n) {
        List<int[][]> finalResult = new LinkedList<>();
        int[][] work = null;
        List<int[]> permute = createPermute(n * n);
        for (int[] p : permute) {
            work = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    work[i][j] = p[i * n + j];
                }
            }
            int satisfied = 0;
            int sumForDiag = 0;
            for (int i = 0; i < n; i++) {
                int sumForRow = 0;
                int sumForCol = 0;
                for (int j = 0; j < n; j++) {
                    sumForRow += work[i][j];
                    sumForCol += work[j][i];
                    if (i == j) {
                        sumForDiag += work[i][j];
                    }
                }
                if (sumForRow == (n * (n * n + 1)) / 2 && sumForCol == (n * (n * n + 1)) / 2)
                    satisfied++;
            }
            if (sumForDiag == (n * (n * n + 1)) / 2)
                satisfied++;
            if (satisfied == (n + 1))
                finalResult.add(work);
        }
        System.out.println("蛮力求解：" + n + "阶幻方如下，共" + finalResult.size() + "个");
        System.out.println("注：根据3.4.10题意，未要求副对角线上元素和也等于n*(n^2+1)/2");
        for (int[][] r : finalResult) {
            for (int[] s : r) {
                System.out.println(Arrays.toString(s));
            }
            System.out.println();
        }
    }

    public List<int[]> createPermute(int n) {
        List<int[]> results = new ArrayList<>();
        int[] permute = new int[n];
        for (int i = 0; i < n; i++) {
            permute[i] = i + 1;
        }
        results.add(Arrays.copyOf(permute, permute.length));
        //true为向左，false为向右
        boolean[] direction = new boolean[n];
        Arrays.fill(direction, true);
        int index = -1;
        while ((index = findElement(permute, direction)) != -1) {
            int k = permute[index];
            boolean dir = direction[index];
            if (dir) {
                permute[index] = permute[index - 1];
                permute[index - 1] = k;
                direction[index] = direction[index - 1];
                direction[index - 1] = dir;
            } else {
                permute[index] = permute[index + 1];
                permute[index + 1] = k;
                direction[index] = direction[index + 1];
                direction[index + 1] = dir;
            }
            for (int i = 0; i < n; i++) {
                if (permute[i] > k)
                    direction[i] = !direction[i];
            }
            results.add(Arrays.copyOf(permute, permute.length));
        }

        return results;
    }

    public int findElement(int[] permute, boolean[] direction) {
        if (permute.length < 2)
            return -1;
        int biggest = -1;
        int biggestValue = -1;
        for (int j = 0; j < permute.length; j++) {
            if ((j - 1) >= 0 && direction[j] && permute[j - 1] < permute[j] && permute[j] >= biggestValue) {
                biggest = j;
                biggestValue = permute[j];
            }
            if ((j + 1) < permute.length && !direction[j] && permute[j] > permute[j + 1] && permute[j] >= biggestValue) {
                biggest = j;
                biggestValue = permute[j];
            }
        }
        return biggest;
    }
}
