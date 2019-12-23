package com.android.floatingsearch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.floatingsearch.data.SearchData;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class FloatingSearchView extends LinearLayout {

    private SearchEditText mSearchEditText;
    private ListView mSearchResultListView;
    private ImageButton mLeftIconButton;
    private View mSearchDividerView;

    private FloatingSearchAdapter mSearchAdapter;

    private FloatingSearchAdapter.OnSearchResultListener mSearchListener = new FloatingSearchAdapter.OnSearchResultListener() {
        @Override
        public void onSuccess(int resultCount) {
            if (resultCount != 0) {
                mSearchDividerView.setVisibility(View.VISIBLE);
            }
            else {
                mSearchDividerView.setVisibility(View.GONE);
            }
        }
    };

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
        try {
            boolean displayLeftIcon = attributes.getBoolean(R.styleable.FloatingSearchView_displayLeftIcon,
                    false);
            setDisplayLeftIcon(displayLeftIcon);

            int leftIconSrc = attributes.getResourceId(R.styleable.FloatingSearchView_leftIconSrc, 0);
            setLeftIconDrawable(leftIconSrc);

            String searchHint = attributes.getString(R.styleable.FloatingSearchView_searchHint);
            if (searchHint != null) {
                setSearchHint(searchHint);
            }
        }
        finally {
            attributes.recycle();
        }
    }

    private void loadLayout() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layoutInflater.inflate(R.layout.layout_auto_complete_view, this, true);
        mSearchDividerView = findViewById(R.id.view_search_divider);
        mSearchEditText = findViewById(R.id.auto_complete_search);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mSearchAdapter != null) {
                    mSearchAdapter.updateSearchText(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Drawable drawableRight = getContext().getResources().getDrawable(R.drawable.ic_close);
        drawableRight.setBounds(0, 0, drawableRight.getIntrinsicWidth(), drawableRight.getIntrinsicHeight());
        mSearchEditText.setCompoundDrawables(null, null, drawableRight, null);
        mSearchEditText.setDrawableClickListener(new SearchEditText.DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                if (target == DrawablePosition.RIGHT) {
                    clearSearch();
                }
            }
        });
        mSearchResultListView = findViewById(R.id.list_search_result);
        mLeftIconButton = findViewById(R.id.image_button_left);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    public void setDataSource(List<SearchData> searchDataList) {
        mSearchAdapter = new FloatingSearchAdapter(getContext(), searchDataList);
        mSearchAdapter.setSearchResultListener(mSearchListener);
        mSearchResultListView.setAdapter(mSearchAdapter);
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

    public void setSearchHint(String hint) {
        mSearchEditText.setHint(hint);
        invalidate();
    }

    /**
     * This method should be called to gain focus to search bar.
     * Calling this method will result in opening the soft input keyboard.
     * By default search bar is not focused initially.
     * Call this method inside onCreate() method of the activity to open keyboard.
     */
    public void requestSearchFocus() {
        mSearchEditText.requestFocus();
    }

    /**
     * This method is used to clear search text from the search bar.
     * Call this method, if you want to clear the search text and don't want to show any search result.
     */
    public void clearSearch() {
        mSearchEditText.setText(Consts.EMPTY_STRING);
        invalidate();
    }

    /**
     * This method will remove the focus and hide the keyboard.
     */
    public void clearSearchFocus() {
        mSearchEditText.clearFocus();
        hideKeyboard();
        invalidate();
        requestLayout();
    }


    public void setOnLeftIconClickListener(View.OnClickListener listener) {
        mLeftIconButton.setOnClickListener(listener);
    }

    public void setOnItemSelectedListener(final OnItemSelectedListener listener) {
        mSearchResultListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchData searchData = (SearchData) mSearchAdapter.getItem(i);
                listener.onItemSelected(i, searchData);
            }
        });
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position, SearchData searchData);
    }
}
