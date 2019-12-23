package com.android.floatingsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.floatingsearch.data.SearchData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingSearchView floatingSearchView;

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
        floatingSearchView = findViewById(R.id.floating_search_view);
        floatingSearchView.setDataSource(searchDataList);
        floatingSearchView.requestSearchFocus();

        floatingSearchView.setOnItemSelectedListener(new FloatingSearchView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position, SearchData searchData) {
                Toast.makeText(MainActivity.this, searchData.getTitle(), Toast.LENGTH_SHORT).show();
                floatingSearchView.clearSearch();
                floatingSearchView.clearSearchFocus();
            }
        });
    }
}
