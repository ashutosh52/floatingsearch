package com.android.floatingsearch;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.floatingsearch.data.SearchData;

import java.util.List;

public class FloatingSearchView extends LinearLayout {

    private EditText mSearchEditText;
    private ListView mSearchResultListView;

    private FloatingSearchAdapter searchAdapter;

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
        mSearchEditText = findViewById(R.id.auto_complete_search);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchAdapter != null) {
                    searchAdapter.updateSearchText(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mSearchResultListView = findViewById(R.id.list_search_result);
    }

    public void setDataSource(List<SearchData> searchDataList) {
        searchAdapter = new FloatingSearchAdapter(getContext(), searchDataList);
        mSearchResultListView.setAdapter(searchAdapter);
        requestLayout();
    }
}
