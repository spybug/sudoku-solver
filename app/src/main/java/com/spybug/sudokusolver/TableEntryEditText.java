package com.spybug.sudokusolver;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableRow;

public class TableEntryEditText extends EditText {

    private boolean invalidEntry;
    private static final int[] STATE_INVALID_ENTRY = {R.attr.state_invalid_entry};

    public TableEntryEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultValues();
    }

    public TableEntryEditText(Context context) {
        super(context);
        setDefaultValues();
    }

    public TableEntryEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setDefaultValues();
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

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (invalidEntry) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, STATE_INVALID_ENTRY);
            return drawableState;
        }
        else {
            return super.onCreateDrawableState(extraSpace);
        }
    }

    public void disableEditability() {
        setLongClickable(false);
        setFocusableInTouchMode(false);
        setFocusable(false);
        setClickable(false);
        setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    public void enableEditability() {
        setLongClickable(true);
        setClickable(true);
        setFocusableInTouchMode(true);
        setFocusable(true);
    }


    public void setInvalidEntry(boolean value) {
        invalidEntry = value;

        refreshDrawableState();
    }

    private void setDefaultValues() {
        setBackgroundResource(R.drawable.cell_selector);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setLayoutParams(new TableRow.LayoutParams(
                0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        setEms(1);
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setCursorVisible(false);
        setSelection(0);
        setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(2) //sets max length to 2
        });
        setKeyListener(DigitsKeyListener.getInstance("123456789"));
        setTextSize(26);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setPadding(0, 20, 0, 0);
    }

}
