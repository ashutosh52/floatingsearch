package com.android.floatingsearch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class FloatingSearchView extends LinearLayout {

    public FloatingSearchView(Context context) {
        this(context, null);
    }

    public FloatingSearchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        loadLayout();
    }

    private void loadLayout() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layoutInflater.inflate(R.layout.layout_auto_complete_view, this, true);
    }

}
