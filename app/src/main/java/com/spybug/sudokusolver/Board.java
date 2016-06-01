package com.spybug.sudokusolver;

import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class Board {

    private UUID mId;
    private int size;
    private int data[][];
    private int solvedData[][];
    private boolean hasBeenSolved;
    private boolean solvable;
    private ArrayList<Coordinate> entries;
    private ArrayList<Coordinate> invalidEntries;

    private class Coordinate {
        public int x;
        public int y;

        public Coordinate(int x_loc, int y_loc) {
            x = x_loc;
            y = y_loc;
        }
    }

    public Board(int size) {
        this(UUID.randomUUID(), size);
    }

    public Board(UUID id, int size) {
        mId = id;
        this.size = size;
        data = new int[size][size];
        solvedData = new int[size][size];
        hasBeenSolved = false;
        solvable = true;
        entries = new ArrayList<>();
        invalidEntries = new ArrayList<>();
    }

    public void setData(int index, int value) {
        int x = index % size;
        int y = index / size;

        data[x][y] = value;

        if (value != 0) {
            boolean duplicateFound = false;
            for (Coordinate c : entries) {
                if (c.x == x && c.y == y) {
                    duplicateFound = true;
                    break;
                }
            }

            if (!duplicateFound)
                entries.add(new Coordinate(x, y));

            checkData(x, y, value);
        }

        Log.v("Board-checkEntry", "value " + value + " entered at index [" + x + "," + y + "]");
    }

    public boolean solvePuzzle() {
        for (int i = 0; i < size; i++)
            System.arraycopy(data[i], 0, solvedData[i], 0, size);

        if(solvable && SolverAlgorithm.solve(solvedData)) { //recursive method that solves puzzle in solvedData
            hasBeenSolved = true;
            return true;
        }
        else {
            return false;
        }
    }

    //checks that value entered at index has no repeats in its column, row, or group
    private boolean checkData(int x, int y, int value) {

        if (colDuplicate(x, value, data) || rowDuplicate(y, value, data) || boxDuplicate(x, y, value, data)) {
            solvable = false;
            invalidEntries.add(new Coordinate(x, y));
            Log.v("board-checkData", "invalid entry added for " + x + "," + y + " and size is " + entries.size());
            return false;
        }
        else {
            solvable = true;
            return true;
        }
    }


    private boolean rowDuplicate(int y, int num, int[][] array) {
        boolean duplicateCounter = false;
        for (int x = 0; x < size; x++) {
            if (array[x][y] == num) {
                if (duplicateCounter) {
                    Log.e("DuplicateChecking", "row duplicate found!");
                    return true;
                }
                duplicateCounter = true;
            }
        }
        return false;
    }

    private boolean colDuplicate(int x, int num, int[][] array) {
        boolean duplicateCounter = false;
        for (int y = 0; y < size; y++) {
            if (array[x][y] == num) {
                if (duplicateCounter) {
                    Log.e("DuplicateChecking", "col duplicate found!");
                    return true;
                }
                duplicateCounter = true;
            }
        }
        return false;
    }

    private boolean boxDuplicate(int start_x, int start_y, int num, int[][] array) {
        start_x -= start_x % 3;
        start_y -= start_y % 3;
        boolean duplicateCounter = false;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (array[x + start_x][y + start_y] == num) {
                    if (duplicateCounter) {
                        int xx = start_x + x;
                        int yy = start_y + y;
                        Log.e("DuplicateChecking", "box duplicate found at " + xx + "," + yy);
                        return true;
                    }
                    duplicateCounter = true;
                }
            }
        }
        return false;
    }

    public void deleteSingleData(int index) {
        int x = index % size;
        int y = index / size;
        int valueToDelete = data[x][y];

        for (int i = entries.size()-1; i >= 0; i--) {
            if (entries.get(i).x == x && entries.get(i).y == y) {
                Log.v("Board-deleteData", "removed " + data[x][y] + " at [" + x + "," + y + "]   entry size: " + entries.size());
                entries.remove(i);
                break;
            }
        }

        for (int j = invalidEntries.size()-1; j >= 0; j--) {
            if (invalidEntries.get(j).x == x && invalidEntries.get(j).y == y) {

                invalidEntries.remove(j);
                break;
            }
        }

        data[x][y] = 0;

        if (checkData(x, y, valueToDelete))
            solvable = true;

        hasBeenSolved = false;


    }

    public void deleteData() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = 0;
            }
        }
        solvable = true;
    }

    public int getData(int index) {
        return data[index % size][index / size];
    }

    public int getSolvedData(int index) {
        return solvedData[index % size][index / size];
    }

    public int getSize() {
        return size;
    }

    public UUID getId() {
        return mId;
    }

    public boolean getSolvedStatus() {
        return hasBeenSolved;
    }
}

//references: http://programmers.stackexchange.com/questions/212808/treating-a-1d-data-structure-as-2d-grid
//              http://www.geeksforgeeks.org/backtracking-set-7-suduku/
