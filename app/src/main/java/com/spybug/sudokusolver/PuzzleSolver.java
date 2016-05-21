package com.spybug.sudokusolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class PuzzleSolver extends AppCompatActivity {

    GridView puzzleGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_solver);

        puzzleGrid = (GridView) findViewById(R.id.board_gridview);
        puzzleGrid.setAdapter(new BoardAdapter(this));
    }

    private void populateGridView() {

    }
}