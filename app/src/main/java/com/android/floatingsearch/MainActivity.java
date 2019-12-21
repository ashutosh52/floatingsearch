package com.android.floatingsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.floatingsearch.data.SearchData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<SearchData> searchDataList = new ArrayList<>();
        SearchData searchData = new SearchData.SearchDataBuilder()
                .setTitle("Meeting")
                .setSubTitle("")
                .setDescription("").build();
        searchDataList.add(searchData);

        SearchData searchData1 = new SearchData.SearchDataBuilder()
                .setTitle("Break Room")
                .setSubTitle("")
                .setDescription("").build();
        searchDataList.add(searchData1);
        FloatingSearchView floatingSearchView = findViewById(R.id.floating_search_view);
        floatingSearchView.setDataSource(searchDataList);
    }
}
