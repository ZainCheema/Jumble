package com.zaincheema.android.jumble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class PuzzleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_grid);

        GridView gridView = (GridView) findViewById(R.id.grid);

        gridView.setAdapter(new ImageAdapter(this));
    }
}
