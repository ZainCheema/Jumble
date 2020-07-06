package com.zaincheema.android.jumble.Lab;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zaincheema.android.jumble.R;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        // create fragment
        ListItemFragment content = new ListItemFragment();

        // we can just pass the intent extras straight to the fragment
        content.setArguments(getIntent().getExtras());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, content).commit();

    }
}
