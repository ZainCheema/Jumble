package com.zaincheema.android.jumble;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.zaincheema.android.jumble.Lab.DummyData;

import java.util.ArrayList;

public class MyPuzzleListFragment extends ListFragment {
    PuzzleViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(PuzzleViewModel.class);

        // Create the observer which updates the UI.
        final Observer<ArrayList<Puzzle>> puzzleObserver = new Observer<ArrayList<Puzzle>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Puzzle> puzzles) {
                Log.e("OnChanged()", "OnChanged Called");
                PuzzleAdapter puzzleAdapter = new PuzzleAdapter(getActivity(), mViewModel.getPuzzles().getValue());
                setListAdapter(puzzleAdapter);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer
        try {
            mViewModel.getPuzzles().observe(this, puzzleObserver);
        } catch(NullPointerException e) {
            Log.e("MyPuzzleListFragment", e.getMessage());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      // PuzzleAdapter puzzleAdapter = new PuzzleAdapter(getActivity(), mViewModel.getPuzzles().getValue());
      //  setListAdapter(puzzleAdapter);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mViewModel.selectPuzzle(position);
        showContent(position);
    }

    void showContent(int index) {
        Intent intent = new Intent();

        intent.setClass(getActivity(), PuzzleActivity.class);

        // pass the current position
        intent.putExtra("index", index);

        startActivity(intent);
    }
}
