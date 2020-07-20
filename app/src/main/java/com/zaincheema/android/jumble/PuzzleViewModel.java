package com.zaincheema.android.jumble;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

public class PuzzleViewModel extends AndroidViewModel {
    private MutableLiveData<Puzzle> mSelectedPuzzle = new MutableLiveData<>();
    private PuzzleRepository mPuzzleRepository;
    private int mSelectedIndex;

    public PuzzleViewModel(@NonNull Application pApplication) {
        super(pApplication);
        mPuzzleRepository = PuzzleRepository.getInstance(getApplication());
        Log.e("PuzzleViewModel", "loadPuzzleFromJSON() reached");
        mPuzzleRepository.loadPuzzlesFromJSON();
    }

    public LiveData<ArrayList<Puzzle>> getPuzzles() {
        return mPuzzleRepository.getPuzzles();
    }

    public LiveData<Puzzle> getSelectedPuzzle() {
        return mSelectedPuzzle;
    }

    public void selectPuzzle(int pIndex) {
        if(pIndex != mSelectedIndex || mSelectedPuzzle == null) {
            mSelectedIndex = pIndex;
        }
       mSelectedPuzzle.setValue(mPuzzleRepository.getPuzzle(mSelectedIndex));
    }
}
