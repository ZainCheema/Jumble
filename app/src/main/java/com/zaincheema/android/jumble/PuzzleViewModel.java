package com.zaincheema.android.jumble;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class PuzzleViewModel extends AndroidViewModel {
    private LiveData<ArrayList<Puzzle>> mPuzzles;
    private LiveData<Puzzle> mSelectedPuzzle;
    private PuzzleRepository mPuzzleRepository;
    private int mSelectedIndex;

    public PuzzleViewModel(@NonNull Application pApplication) {
        super(pApplication);
        mPuzzleRepository = PuzzleRepository.getInstance(getApplication());
    }

    public LiveData<ArrayList<Puzzle>> getPuzzles() {
        if(mPuzzles == null) {
            mPuzzles = mPuzzleRepository.getPuzzles();
        }
        return mPuzzles;
    }

    public LiveData<Puzzle> getPuzzle(int pPuzzleIndex) {
        return mPuzzleRepository.getPuzzle(pPuzzleIndex);
    }

    public void selectPuzzle(int pIndex) {
        if(pIndex != mSelectedIndex || mSelectedPuzzle == null) {
            mSelectedIndex = pIndex;
            mSelectedPuzzle = getPuzzle(mSelectedIndex);
        }
    }

    public LiveData<Puzzle> getSelectedPuzzle() { return mSelectedPuzzle; }

}
