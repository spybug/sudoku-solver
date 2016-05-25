package com.spybug.sudokusolver;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PuzzleSolver extends AppCompatActivity {

    TableLayout mTableLayout;

    int boardSize = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_solver);

        mTableLayout = (TableLayout) findViewById(R.id.board_table);
        createTable();
    }

    private void createTable() {

        for (int i = 0; i < boardSize; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams( new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)); //sets width and height
            tableRow.setId(i + 100); //sets id between 100 and 100 + boardSize

            for (int j = 0; j < boardSize; j++) {
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

                editText.setId(i * 9 + j); //sets id between 0 and 80

                tableRow.addView(editText);

                editText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (editText.length() > 1) {
                            String newInput = s.toString().substring(s.length() - 1); //grabs the last value entered
                            editText.setText(newInput); //sets the text to the newInput
                            editText.setSelection(editText.length()); //sets cursor to end of editText
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