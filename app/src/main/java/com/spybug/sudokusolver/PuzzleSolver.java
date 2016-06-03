package com.spybug.sudokusolver;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class PuzzleSolver extends AppCompatActivity {

    private TableLayout mTableLayout;
    private Board mBoard;
    private Button mClearBoardButton;
    private Button mFullySolveButton;
    private boolean ignoreNextText; //decides whether to skip textWatcher

    final static int DEFAULT_BOARD_SIZE = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBoard = new Board(DEFAULT_BOARD_SIZE);

        setContentView(R.layout.activity_puzzle_solver);
        mTableLayout = (TableLayout) findViewById(R.id.board_table);
        createTable(DEFAULT_BOARD_SIZE);

        ignoreNextText = false;

        mClearBoardButton = (Button) findViewById(R.id.clear_puzzle_button);
        mClearBoardButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEditTexts();
                mBoard.deleteData();
                if (!mFullySolveButton.isEnabled())
                    mFullySolveButton.setEnabled(true);
            }
        });

        mFullySolveButton = (Button) findViewById(R.id.fully_solve_button);
        mFullySolveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBoard.solvePuzzle()) {
                    TableEntryEditText tempET;
                    int size = mBoard.getSize();
                    ignoreNextText = true;
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            tempET = (TableEntryEditText) findViewById(i * size + j);
                            if (tempET != null) {
                                tempET.setText(String.format("%s", mBoard.getSolvedData(i * size + j)));
                                tempET.disableEditability();
                            }
                        }
                    }
                    ignoreNextText = false;
                    mFullySolveButton.setEnabled(false);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Puzzle could not be solved", Toast.LENGTH_SHORT).show();
                }

                //InputMethodManager imm = (InputMethodManager) PuzzleSolver.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                //if (imm.isAcceptingText())
                    //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); //hide keyboard if its open - VERY BUGGY
            }
        });

    }

    private void clearEditTexts() {
        ignoreNextText = true;
        for (int i = 0; i < mTableLayout.getChildCount(); i++) { //loops through all rows in tableLayout
            TableRow tempTR = (TableRow) mTableLayout.getChildAt(i); //sets a temp TableRow
            for (int j = 0; j < tempTR.getChildCount(); j++) { //loops through all editTexts in current TableRow
                TableEntryEditText et = (TableEntryEditText) tempTR.getChildAt(j);
                et.enableEditability();
                et.setText(""); //clears the editText
                et.setInvalidEntry(false);
            }
        }
        ignoreNextText = false;
    }


    @Override
    protected void onSaveInstanceState(Bundle SavedInstanceState) {
        super.onSaveInstanceState(SavedInstanceState);

        String boardData;
    }

    private void checkEntry(int editTextId) {
        TableEntryEditText tempET;
        int startPos;

        switch (mBoard.checkData(editTextId)) {
            case 1:
                //set col background for elements to red
                for (int i = mBoard.computeX(editTextId); i < mBoard.getSize() * mBoard.getSize(); i+=9) {
                    tempET = (TableEntryEditText) findViewById(i);
                    if (tempET != null)
                        tempET.setInvalidEntry(true);
                        //tempET.setBackgroundResource(R.drawable.invalid_cell);
                }
                break;
            case 2:
                //set row background for elements to red
                startPos = mBoard.computeY(editTextId) * mBoard.getSize();
                for (int i = startPos; i < startPos + 9; i++) {
                    tempET = (TableEntryEditText) findViewById(i);
                    if (tempET != null)
                        tempET.setInvalidEntry(true);
                        //tempET.setBackgroundResource(R.drawable.invalid_cell);
                }
                break;
            case 3:
                //set box background for elements to red
                startPos = (editTextId - (editTextId % 3)) - ((editTextId / 9) % 3 * 9);
                for (int y = startPos; y < 27 + startPos; y += 9) {
                    for (int loc = y; loc < y + 3; loc++) {
                        tempET = (TableEntryEditText) findViewById(loc);
                        if (tempET != null)
                            tempET.setInvalidEntry(true);
                            //tempET.setBackgroundResource(R.drawable.invalid_cell);
                    }
                }

                break;
            case 0:
                //remove element from list and set background to normal
                break;
        }
    }

    private void createTable(final int size) {

        for (int i = 0; i < size; i++) {
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams( new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)); //sets width and height
            tableRow.setId(i + 100); //sets id between 100 and 100 + boardSize

            for (int j = 0; j < size; j++) {
                final TableEntryEditText editText = new TableEntryEditText(this);
                editText.setId(i * size + j); //sets id between 0 and 80

                mTableLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        editText.setHeight(mTableLayout.getWidth() / 9);
                    }
                });
                //editText.setHeight(tableRow.getWidth());
                //http://stackoverflow.com/questions/3591784/getwidth-and-getheight-of-view-returns-0

                editText.addTextChangedListener(new TextWatcher() { //add custom textwatcher class that accesses edittext parent and such and changes background id's of all edititexts
                                                                    //will need a way to check if certain set of edittexts background has been set and change it if changes

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(ignoreNextText)
                            return;

                        if (editText.length() > 1) {
                            String newInput = s.toString().substring(s.length() - 1); //grabs the last value entered
                            editText.setText(newInput); //sets the text to the newInput
                            editText.setSelection(editText.length()); //sets cursor to end of editText
                            checkEntry(editText.getId());
                        }
                        else if (editText.length() == 1 && s.length() == 1) {
                            mBoard.setData(editText.getId(), Integer.parseInt(s.toString())); //sets board data when something entered
                            checkEntry(editText.getId());
                        }
                        else if (count == 0 && editText.length() == 0) {
                            mBoard.deleteSingleData(editText.getId());
                            checkEntry(editText.getId());
                            Log.v("onTextChanged", "deleting data at index " + editText.getId());
                        }
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

                tableRow.addView(editText);
            }

            mTableLayout.addView(tableRow); //add the row to the table
        }
    }
}