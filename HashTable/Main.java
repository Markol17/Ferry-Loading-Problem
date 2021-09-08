// package HashTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Main {

    int n;
    int L;
    int bestK;
    int[] currX;
    int[] bestX;
    int[] length;
    HashMap<Integer, Boolean> visited;

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
        this.visited = new HashMap<Integer, Boolean>();
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

    private void markAsVisited(int k, int s) {
        // add k + s to create key (could also be s - k or so other thing, but k + s
        // creates non-negative keys and are not too big)
        this.visited.put(k + s, true);
    }

    private boolean isVisited(int k, int s) {
        Boolean value = this.visited.get(k + s);
        return value == null ? false : value;
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

/* ------------------------------------------------------------------ */
// This version was made to degug in vs code with the Java extension
// package HashTable;

// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Scanner;
// import java.util.*;

// class Main {

// private static final String INPUT_MESSAGE = "Enter file name by path (e.g.,
// ./input1.txt):";
// private static final String FILE_EXTENSION = ".txt";
// private static final String INPUT_ERROR = "File path is incorrect!";
// private static final String OUTPUT_FILE_PREFIX = "output";
// private static final String ERROR_MESSAGE = "An error occurred";
// private static final String FILE_EXISTS_MESSAGE = " already exists";
// private static final String FILE_CREATED_MESSAGE = "File created: ";
// private static final String FILE_WRITE_MESSAGE = "Writing to file: ";

// int n;
// int L;
// int bestK;
// int[] currX;
// int[] bestX;
// int[] length;
// HashMap<Integer, Boolean> visited;

// Main(ArrayList<Integer> length, int L) {
// this.n = length.size(); // the number of cars
// this.L = L; // length of the ferry
// this.bestK = -1;
// this.currX = new int[this.n]; // temp result
// this.bestX = new int[this.n]; // result
// this.length = new int[this.n]; // containing all the length of the cars
// for (int i = 0; i < this.n; i++) {
// this.length[i] = length.get(i).intValue();
// }
// this.visited = new HashMap<Integer, Boolean>();
// }

// public static void main(String[] args) {
// ArrayList<Integer> length = new ArrayList<Integer>(); // Cars in data set
// int L = 0; // Length of boat in cm

// // Those variables are used for parsing.
// // They can be ignored for the actual algorithm.
// int numOfTestDataSet = 0; // Number of test data set
// int sizeToSkip = 0; // Number of rows to skip when parsing

// // Get the user input
// Scanner scanner = new Scanner(System.in);
// System.out.println(INPUT_MESSAGE);
// String filePath;
// filePath = scanner.nextLine();

// // Iterate until a valid input is given
// while (!filePath.endsWith(FILE_EXTENSION)) {
// if (!filePath.endsWith(FILE_EXTENSION)) {
// System.out.println(INPUT_ERROR);
// System.out.println(INPUT_MESSAGE);
// filePath = scanner.nextLine();
// }
// }
// scanner.close();

// /* Start of parsing the file */
// File file = new File(filePath);
// try {
// Scanner reader = new Scanner(file);
// numOfTestDataSet = Integer.parseInt(reader.nextLine());
// reader.close();
// } catch (FileNotFoundException e) {
// System.out.println(e);
// }

// for (int i = 0; i < numOfTestDataSet; i++) {
// boolean isFirstLine = true;
// boolean isFirstOfSet = true;
// boolean skipIsExecuted = false;

// try {
// Scanner reader = new Scanner(file);
// while (reader.hasNextLine()) {

// int lineData = Integer.parseInt(reader.nextLine());

// if (isFirstLine) {
// isFirstLine = false;
// reader.nextLine();
// lineData = Integer.parseInt(reader.nextLine());
// }

// if (i != numOfTestDataSet && !skipIsExecuted) {
// sizeToSkip = sizeToSkip == 0 ? sizeToSkip : sizeToSkip + 2;
// for (int j = 0; j < sizeToSkip; j++) {
// String line = reader.nextLine();
// if (line.equals("")) {
// lineData = Integer.parseInt(reader.nextLine());
// } else {
// lineData = Integer.parseInt(line);
// }
// isFirstOfSet = true;
// skipIsExecuted = true;
// }
// }

// if (isFirstOfSet) {
// L = lineData * 100;
// isFirstOfSet = false;
// } else {
// if (lineData != 0) {
// length.add(lineData);
// } else {
// break;
// }
// }

// }
// sizeToSkip += length.size();
// reader.close();
// } catch (FileNotFoundException e) {
// System.out.println(e);
// }
// /* End of file parsing */

// Main hashTable = new Main(length, L);
// hashTable.BacktrackSolve(0, L);

// // Validate answer and get number of valid positions
// // int numOfValidPositions = hashTable.validate();

// // Parse the current filePath
// String fileName = OUTPUT_FILE_PREFIX +
// Integer.parseInt(filePath.replaceAll("[^\\d]", " ").trim())
// + FILE_EXTENSION;

// // Save to file
// hashTable.save(fileName);
// // Reset array for next data set
// length = new ArrayList<Integer>();
// }
// }

// public void BacktrackSolve(int currK, int currS) {
// if (currK > bestK) {
// this.bestK = currK;
// for (int i = 0; i < currX.length; i++) {
// bestX[i] = currX[i];
// }
// }
// if (currK < n) {
// int total = 0;
// for (int i = 0; i < currK; i++) {
// total += length[i];
// }
// if ((length[currK] <= currS) && !isVisited(currK + 1, currS - length[currK]))
// {
// currX[currK] = 1;
// int newS = currS - length[currK];
// BacktrackSolve(currK + 1, newS);
// markAsVisited(currK + 1, newS);
// }
// if ((length[currK] <= (L - (total - (L - currS)))) && !isVisited(currK + 1,
// currS)) {
// currX[currK] = 0;
// BacktrackSolve(currK + 1, currS);
// markAsVisited(currK + 1, currS);
// }
// }
// }

// private void markAsVisited(int k, int s) {
// this.visited.put(k + s, true);
// }

// private boolean isVisited(int k, int s) {
// Boolean value = this.visited.get(k + s);
// return value == null ? false : value;
// }

// // validation method
// private int validate() {
// int counter = 0;
// int starboard = 0;
// int port = 0;
// int max = this.L;
// boolean firstIsAdded = false;

// for (int i = 0; i < bestX.length; i++) {
// if (bestX[i] == 1 && (starboard + this.length[i]) <= max && (i == 0 ||
// firstIsAdded)) {
// starboard += this.length[i];
// counter++;
// if (i == 0) {
// firstIsAdded = true;
// }
// } else if (port + this.length[i] <= max && (i == 0 || firstIsAdded)) {
// port += this.length[i];
// counter++;
// if (i == 0) {
// firstIsAdded = true;
// }
// }
// }
// return counter;
// }

// public void save(String fileName) {
// try {
// // creates new file
// File file = new File(fileName);

// if (file.createNewFile()) {
// // confirmation message
// System.out.println(FILE_CREATED_MESSAGE + file.getName());
// System.out.println(FILE_WRITE_MESSAGE + fileName);
// FileWriter writer = new FileWriter(file);

// writer.write(bestK + "\n");
// for (int i = 0; i < bestK; i++) {
// if (bestX[i] == 1) {
// writer.write("starboard \n");
// } else {
// writer.write("port \n");
// }
// }
// writer.close();
// } else {
// // file already exists
// System.out.println(fileName + FILE_EXISTS_MESSAGE);
// System.out.println(FILE_WRITE_MESSAGE + fileName);
// FileWriter writer = new FileWriter(file, true);

// writer.write("\n");
// writer.write(bestK + "\n");
// for (int i = 0; i < bestK; i++) {
// if (bestX[i] == 1) {
// writer.write("starboard \n");
// } else {
// writer.write("port \n");
// }
// }
// writer.close();
// }
// } catch (IOException e) {
// // other error
// System.out.println(ERROR_MESSAGE);
// e.printStackTrace();
// }
// }
// }

/* ------------------------------------------------------------------ */
// Custom hashmap implementation
// Replacing the Java hashmap by this implementation, it still gives you the
// right answer but slower and exceeding the 3 sec time limit

// private class HashMap {
// class Node {
// int key;
// int val;
// Node next;

// Node(int key, int val) {
// this.key = key;
// this.val = val;
// }
// }

// Node[] map;

// /** Initialize your data structure here. */
// public HashMap() {
// map = new Node[1000];
// }

// /** value will always be non-negative. */
// public void put(int key, int value) {
// int hash = hash(key);
// if (map[hash] == null) {
// Node node = new Node(key, value);
// map[hash] = node;
// return;
// }
// Node head = map[hash];
// while (head != null && head.key != key) {
// head = head.next;
// }
// if (head == null) {
// Node newHead = new Node(key, value);
// newHead.next = map[hash];
// map[hash] = newHead;
// return;
// } else {
// head.val = value;
// }
// }

// /**
// * Returns the value to which the specified key is mapped, or -1 if this map
// * contains no mapping for the key
// */
// public boolean containsPair(int key, int value) {
// int hash = hash(key);
// if (map[hash] == null)
// return false;
// Node head = map[hash];
// while (head != null && head.key != key) {
// if (head.val == value) {
// return true;
// }
// head = head.next;
// }
// if (head.next == null || head == null) {
// return false;
// } else {
// return true;
// }
// }

// private int hash(int key) {
// return key % 1000;
// }
// }