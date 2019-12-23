package com.android.floatingsearch;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.floatingsearch.data.SearchData;

import java.util.List;

public class FloatingSearchView extends LinearLayout {

    private EditText mSearchEditText;
    private ListView mSearchResultListView;
    private ImageButton mLeftIconButton;
    private ImageButton mRightIconButton;

    private FloatingSearchAdapter searchAdapter;

    public FloatingSearchView(Context context) {
        this(context, null);
    }

    public FloatingSearchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        loadLayout();
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attributeSet,
                R.styleable.FloatingSearchView,
                0,
                0);
        boolean displayLeftIcon = attributes.getBoolean(R.styleable.FloatingSearchView_displayLeftIcon,
                false);
        boolean displayRightIcon = attributes.getBoolean(R.styleable.FloatingSearchView_displayRightIcon,
                false);
        setDisplayLeftIcon(displayLeftIcon);
        setDisplayRightIcon(displayRightIcon);

        int leftIconSrc = attributes.getResourceId(R.styleable.FloatingSearchView_leftIconSrc, 0);
        setLeftIconDrawable(leftIconSrc);

        int rightIconSrc = attributes.getResourceId(R.styleable.FloatingSearchView_rightIconSrc, 0);
        setRightIconDrawable(rightIconSrc);

        String searchHint = attributes.getString(R.styleable.FloatingSearchView_searchHint);
        if (searchHint != null) {
            setSearchHint(searchHint);
        }
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
        mLeftIconButton = findViewById(R.id.image_button_left);
        mRightIconButton = findViewById(R.id.image_button_right);
    }

    public void setDataSource(List<SearchData> searchDataList) {
        searchAdapter = new FloatingSearchAdapter(getContext(), searchDataList);
        mSearchResultListView.setAdapter(searchAdapter);
        requestLayout();
    }

    public void setLeftIconDrawable(int rscId) {
        mLeftIconButton.setImageResource(rscId);
        if (rscId != 0) {
            setDisplayLeftIcon(true);
        }
        else {
            setDisplayLeftIcon(false);
        }
    }

    public void setRightIconDrawable(int rscId) {
        mRightIconButton.setImageResource(rscId);
        if (rscId != 0) {
            setDisplayRightIcon(true);
        }
        else {
            setDisplayRightIcon(false);
        }
    }

    public void setDisplayLeftIcon(boolean shouldDisplayLeftIcon) {
        if (shouldDisplayLeftIcon) {
            mLeftIconButton.setVisibility(View.VISIBLE);
        }
        else {
            mLeftIconButton.setVisibility(View.GONE);
        }
        invalidate();
        requestLayout();
    }

    public void setDisplayRightIcon(boolean shouldDisplayRightIcon) {
        if (shouldDisplayRightIcon) {
            mRightIconButton.setVisibility(View.VISIBLE);
        }
        else {
            mRightIconButton.setVisibility(View.GONE);
        }
        invalidate();
        requestLayout();
    }

    public void setSearchHint(String hint) {
        mSearchEditText.setHint(hint);
        invalidate();
    }

    public void setOnLeftIconClickListener(View.OnClickListener listener) {
        mLeftIconButton.setOnClickListener(listener);
    }

    public void setOnRightIconClickListener(View.OnClickListener listener) {
        mRightIconButton.setOnClickListener(listener);
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        mSearchResultListView.setOnItemSelectedListener(listener);
    }
}
