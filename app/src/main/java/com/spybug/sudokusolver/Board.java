package com.spybug.sudokusolver;

import android.util.Log;

import java.util.UUID;

public class Board {

    private UUID mId;
    private int size;
    private int data[][];
    private boolean hasBeenSolved;
    private boolean unsolvable;

    public Board(int size) {
        this(UUID.randomUUID(), size);
    }

    public Board(UUID id, int size) {
        mId = id;
        this.size = size;
        data = new int[size][size];
        hasBeenSolved = false;
        unsolvable = false;
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

    public void setData(int index, int value) {
        int x = index % size;
        int y = index / size;
        data[x][y] = value;

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
