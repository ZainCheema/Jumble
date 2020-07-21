package com.zaincheema.android.jumble;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class PuzzleFragment extends Fragment {

    private static Context mContext;
    GridView gridView;
    private ImageAdapter imageAdapter;

    public PuzzleFragment() {

    }

    public static PuzzleFragment newInstance(Context context) {
        PuzzleFragment fragment = new PuzzleFragment();
        mContext = context;
        return fragment;
    }


    View view;
    PuzzleViewModel mViewModel;
    Puzzle mPuzzle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(PuzzleViewModel.class);
        mPuzzle = mViewModel.getSelectedPuzzle().getValue();
        imageAdapter = new ImageAdapter(mContext, mPuzzle.getTiles().getValue());

        // This will be an observer that checks to see if all tiles have been loaded,
        // upon which the gridview would be shown
        final Observer<ArrayList<Bitmap>> tileObserver = new Observer<ArrayList<Bitmap>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Bitmap> bitmaps) {
                if(bitmaps.size() == 24) {
                    Log.e("Tiles download complete", "Now showing grid");
                    imageAdapter.notifyDataSetChanged();
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer
        try {
           mPuzzle.getTiles().observe(this, tileObserver);
        } catch(NullPointerException e) {
            Log.e("PuzzleFragment", e.getMessage());
        }

       // mViewModel.loadPuzzleTiles();
    }

    public View updateView() {
        Log.e("show grid", "createView() called");
        return gridView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.puzzle_grid, container, false);

        Log.e("PuzzleFragment", "View created");

        mPuzzle = mViewModel.getSelectedPuzzle().getValue();
        gridView = view.findViewById(R.id.grid);

        gridView.setAdapter(imageAdapter);

        return view;
    }
}
