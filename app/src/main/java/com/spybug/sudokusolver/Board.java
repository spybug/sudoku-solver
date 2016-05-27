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
    private boolean unsolvable;
    private ArrayList<Coordinate> entries;

    private class Coordinate {
        public int x;
        public int y;

        public Coordinate(int x_loc, int y_loc) {
            x = x_loc;
            y = y_loc;
        }
//
//        public int getX(){
//            return x;
//        }
//
//        public int getY() {
//            return y;
//        }
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
        unsolvable = false;
        entries = new ArrayList<Coordinate>();
    }

    public boolean solvePuzzle() {
        //implement sudoku solving algorithm
        for (Coordinate c : entries) {
            solvedData[c.x][c.y] = data[c.x][c.y]; //copy entries in data over to solvedData
        }

        if(backtrack()) { //recursive method that solves puzzle in solvedData
            unsolvable = false;
            return true;
        }
        else {
            unsolvable = true;
            return false;
        }
    }

    //checks that value entered at index has no repeats in its column, row, or group
    private boolean checkEntry(int x, int y) {
        Log.v("Board-checkEntry", "value " + data[x][y] + " entered at index [" + x + "," + y + "]"); // is not valid!");
        return true;
    }

    private boolean backtrack() {
        Coordinate c = new Coordinate(0, 0);

        if (findEmptyLocation(solvedData, c))
            return true;

        for (int num = 1; num <= 9; num++) {
            if (safePosition(c.x, c.y, num)) {
                solvedData[c.x][c.y] = num;

                if (backtrack())
                    return true;
                else
                    solvedData[c.x][c.y] = 0;
            }
        }
        return false;
    }

    private boolean safePosition(int x, int y, int num) {
        return !rowDuplicate(x, num) && !colDuplicate(y, num) && !boxDuplicate(x - x%3, y - y%3, num);
    }

    private boolean rowDuplicate(int x, int num) {
        for (int y = 0; y < size; y++) {
            if (solvedData[x][y] == num)
                return true;
        }
        return false;
    }

    private boolean colDuplicate(int y, int num) {
        for (int x = 0; x < size; x++) {
            if (solvedData[x][y] == num)
                return true;
        }
        return false;
    }

    private boolean boxDuplicate(int start_x, int start_y, int num) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (solvedData[x + start_x][y + start_y] == num)
                    return true;
            }
        }
        return false;
    }

    private boolean findEmptyLocation(int[][] board, Coordinate c) {
        for (c.x = 0; c.x < size; c.x++) {
            for (c.y = 0; c.y < size; c.y++)
                if(board[c.x][c.y] == 0)
                    return false;
        }
        return true;
    }

    public void deleteData(int index) {
        int x = index % size;
        int y = index / size;

        for (int i = entries.size()-1; i >= 0; i--) {
            if (entries.get(i).x == x && entries.get(i).y == y) {
                Log.v("Board-deleteData", "removed " + data[entries.get(i).x][entries.get(i).y] + " at [" + entries.get(i).x + "," + entries.get(i).y + "]");
                data[x][y] = 0;
                entries.remove(i);
                break;
            }
        }
    }

    public void setData(int index, int value) {
        int x = index % size;
        int y = index / size;
        data[x][y] = value;

        boolean duplicateFound = false;
        for (Coordinate c : entries) {
            if (c.x == x && c.y == y) {
                duplicateFound = true;
                break;
            }
        }
        if (!duplicateFound)
            entries.add(new Coordinate(x, y));

        checkEntry(x, y);
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
