package com.spybug.sudokusolver;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PuzzleSolver extends AppCompatActivity {

    private TableLayout mTableLayout;
    private Board mBoard;
    private Button mClearBoardButton;

    final static int DEFAULT_BOARD_SIZE = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBoard = new Board(DEFAULT_BOARD_SIZE);


            setContentView(R.layout.activity_puzzle_solver);
            mTableLayout = (TableLayout) findViewById(R.id.board_table);
            createTable(DEFAULT_BOARD_SIZE);

        mClearBoardButton = (Button) findViewById(R.id.clear_puzzle_button);
        mClearBoardButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEditTexts();
            }
        });

    }

    private void clearEditTexts() {
        for (int i = 0; i < mTableLayout.getChildCount(); i++) { //loops through all rows in tableLayout
            TableRow tempTR = (TableRow) mTableLayout.getChildAt(i); //sets a temp TableRow
            for (int j = 0; j < tempTR.getChildCount(); j++) { //loops through all editTexts in current TableRow
                EditText et = (EditText) tempTR.getChildAt(j);
                et.setText(""); //clears the editText
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle SavedInstanceState) {
        super.onSaveInstanceState(SavedInstanceState);

        String boardData;
    }

    private void createTable(int size) {

        for (int i = 0; i < size; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams( new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)); //sets width and height
            tableRow.setId(i + 100); //sets id between 100 and 100 + boardSize

            for (int j = 0; j < size; j++) {
                final TableEntryEditText editText = new TableEntryEditText(this);

                editText.setLayoutParams( new TableRow.LayoutParams(
                        0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f)); //0 width and wrap_content height
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setEms(10);
                editText.setGravity(Gravity.CENTER_HORIZONTAL);
                editText.setFilters(new InputFilter[] {
                        new InputFilter.LengthFilter(5) //sets max length to 1
                });
                editText.setCursorVisible(false);
                editText.setSelection(editText.length());

                editText.setId(i * size + j); //sets id between 0 and 80

                tableRow.addView(editText);

                editText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (editText.length() > 1) {
                            String newInput = s.toString().substring(s.length() - 1); //grabs the last value entered
                            editText.setText(newInput); //sets the text to the newInput
                            editText.setSelection(editText.length()); //sets cursor to end of editText
                        }
                        else if (editText.length() == 1 && s.length() == 1) {
                            mBoard.setData(editText.getId(), Integer.parseInt(s.toString())); //sets board data when something entered
                        }
                        else if (count == 0 && editText.length() == 0) {
                            mBoard.deleteData(editText.getId());
                        }
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void afterTextChanged(Editable s) {}
                });
            }

            mTableLayout.addView(tableRow); //add the row to the table
        }
    }
}