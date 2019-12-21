package com.android.floatingsearch.data;

public class SearchData {

    private String mTitle;
    private String mSubTitle;
    private String mDescription;

    private SearchData(SearchDataBuilder builder) {
        this.mTitle = builder.mTitle;
        this.mSubTitle = builder.mSubTitle;
        this.mDescription = builder.mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public String getDescription() {
        return mDescription;
    }


    public static class SearchDataBuilder {
        private String mTitle;
        private String mSubTitle;
        private String mDescription;

        public SearchDataBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public SearchDataBuilder setSubTitle(String subTitle) {
            mSubTitle = subTitle;
            return this;
        }

        public SearchDataBuilder setDescription(String description) {
            mDescription = description;
            return this;
        }

        public SearchData build() {
            return new SearchData(this);
        }
    }
}
