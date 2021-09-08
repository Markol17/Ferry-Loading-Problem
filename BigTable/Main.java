// package BigTable;

import java.util.ArrayList;
import java.util.Scanner;

class Main {

    int n;
    int L;
    int bestK;
    int[] currX;
    int[] bestX;
    int[] length;
    boolean[][] visited;

    Main(ArrayList<Integer> length, int L) {
        this.n = length.size();
        this.L = L;
        this.bestK = -1;
        this.currX = new int[this.n];
        this.bestX = new int[this.n];
        this.length = new int[this.n];
        for (int i = 0; i < this.n; i++) {
            this.length[i] = length.get(i).intValue();
        }
        this.visited = new boolean[this.n + 1][L + 1];
    }

    public static void main(String[] args) {
        ArrayList<Integer> length = new ArrayList<Integer>(); // Cars in data set
        int L = 0; // Length of boat in cm

        /*------------- file parsing -------------*/
        try {
            Scanner scanner = new Scanner(System.in);
            int numOfTestDataSet = scanner.nextInt();
            boolean isFirstOfSet = true;
            int dataSetCount = 1;

            while (scanner.hasNext()) {
                int nextInt = scanner.nextInt();
                if (isFirstOfSet) {
                    L = nextInt * 100;
                    isFirstOfSet = false;
                } else {
                    if (nextInt != 0) {
                        length.add(nextInt);
                    } else {
                        Main bigTable = new Main(length, L);
                        bigTable.backtrackSolve(0, L);
                        bigTable.print(dataSetCount, numOfTestDataSet);
                        dataSetCount++;
                        length = new ArrayList<Integer>();
                        isFirstOfSet = true;
                    }
                }
            }
            /*------------- end of file parsing -------------*/
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void backtrackSolve(int currK, int currS) {
        // currK cars have been added
        // currS space remains on the left side (port)
        if (currK > bestK) {
            this.bestK = currK;
            for (int i = 0; i < currX.length; i++) {
                bestX[i] = currX[i];
            }
        }
        if (currK < n) {
            int total = 0;
            for (int i = 0; i < currK; i++) {
                total += length[i];
            }
            if ((length[currK] <= currS) && !isVisited(currK + 1, currS - length[currK])) {
                currX[currK] = 1;
                int newS = currS - length[currK];
                backtrackSolve(currK + 1, newS);
                markAsVisited(currK + 1, newS);
            }
            if ((length[currK] <= (L - (total - (L - currS)))) && !isVisited(currK + 1, currS)) {
                currX[currK] = 0;
                backtrackSolve(currK + 1, currS);
                markAsVisited(currK + 1, currS);
            }
        }
    }

    private void markAsVisited(int row, int col) {
        this.visited[row][col] = true;
    }

    private boolean isVisited(int row, int col) {
        return this.visited[row][col];
    }

    public void print(int dataSetCount, int numOfTestDataSet) {
        System.out.println(bestK);
        for (int i = 0; i < bestK; i++) {
            if (bestX[i] == 1) {
                System.out.println("starboard");
            } else {
                System.out.println("port");
            }
        }
        if (dataSetCount != numOfTestDataSet) {
            System.out.print("\n");
        }
    }
}