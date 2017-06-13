

package com.example.android.calendar;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    //RecyclerView recyclerView;
   // RecyclerView.Adapter recyclerAdapter;
    //RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        MonthFmAdapter adapter = new MonthFmAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

      //  recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerAdapter= new RecyclerAdapter();
        //recyclerView.setAdapter(recyclerAdapter);


    }
}
