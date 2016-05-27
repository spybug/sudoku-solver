package com.spybug.sudokusolver;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

public class TableEntryEditText extends EditText {

    public TableEntryEditText(Context context) {
        super(context);
    }

    @Override
    public void onSelectionChanged(int start, int end) { //always puts cursor at end
        CharSequence text = getText();

        if (text != null) {
            if (start != text.length() || end != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }
        super.onSelectionChanged(start, end);
    }

}
