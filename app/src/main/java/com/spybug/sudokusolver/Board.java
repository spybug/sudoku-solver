package com.spybug.sudokusolver;

import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class Board {

    private UUID mId;
    private int size;
    private int data[][];
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
        hasBeenSolved = false;
        unsolvable = false;
        entries = new ArrayList<Coordinate>();
    }

    public boolean solvePuzzle() {
        //implement sudoku solving algorithm
        return false;
    }

    //checks that value entered at index has no repeats in its column, row, or group
    private boolean checkEntry(int x, int y) {
        Log.v("Board-checkEntry", "value " + data[x][y] + " entered at index [" + x + "," + y + "]"); // is not valid!");
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
