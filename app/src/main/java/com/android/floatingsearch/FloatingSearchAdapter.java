package com.android.floatingsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.floatingsearch.data.SearchData;

import java.util.ArrayList;
import java.util.List;

public class FloatingSearchAdapter extends BaseAdapter implements Filterable {

    private List<SearchData> mSearchDataList;
    private List<SearchData> mSearchResultList;

    private LayoutInflater mLayoutInflater;

    public FloatingSearchAdapter(Context context, List<SearchData> searchDataList) {
        mSearchDataList = searchDataList;
        mLayoutInflater = LayoutInflater.from(context);
        mSearchResultList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mSearchResultList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.layout_search_adapter, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SearchData searchData = mSearchResultList.get(position);
        viewHolder.mTitleTextView.setText(searchData.getTitle());
        viewHolder.mSubtitleTextView.setText(searchData.getSubTitle());
        viewHolder.mDescriptionTextView.setText(searchData.getDescription());
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                ArrayList<SearchData> searchDataArrayList = new ArrayList<>();
                String searchString = charSequence.toString().toLowerCase();
                for (SearchData searchData : mSearchDataList) {
                    String title = searchData.getTitle().toLowerCase();
                    String subTitle = searchData.getSubTitle().toLowerCase();
                    String description = searchData.getDescription().toLowerCase();
                    if (title.contains(searchString) || subTitle.contains(searchString) || description.contains(searchString)) {
                        searchDataArrayList.add(searchData);
                    }
                }
                filterResults.values = searchDataArrayList;
                mSearchResultList = searchDataArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }

    private class ViewHolder {
        private TextView mTitleTextView, mSubtitleTextView, mDescriptionTextView;
        private ImageView mCellImageView;
        public ViewHolder(View view) {
            mTitleTextView = view.findViewById(R.id.text_title);
            mSubtitleTextView = view.findViewById(R.id.text_subtitle);
            mDescriptionTextView = view.findViewById(R.id.text_description);
            mCellImageView = view.findViewById(R.id.image_cell_icon);
        }
    }
}
