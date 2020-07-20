package com.zaincheema.android.jumble;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class PuzzleFragment extends Fragment {



    public PuzzleFragment() {

    }

    public static PuzzleFragment newInstance() {
        PuzzleFragment fragment = new PuzzleFragment();
        return fragment;
    }


    View view;
    PuzzleViewModel mViewModel;
    Puzzle mPuzzle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(PuzzleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.puzzle_grid, container, false);

      Log.e("PuzzleFragment", "View created");

        mPuzzle = mViewModel.getSelectedPuzzle().getValue();

     GridView gridView = (GridView) view.findViewById(R.id.grid);

     gridView.setAdapter(new ImageAdapter(getContext(), mPuzzle.getTiles()));

     return view;
    }
}
