package com.zaincheema.android.jumble;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PuzzleAdapter extends ArrayAdapter<Puzzle> {
    private Context mContext;
    private List<Puzzle> mPuzzleList;

    public PuzzleAdapter(@NonNull Context pContext, ArrayList<Puzzle> pList) {
        super(pContext, 0, pList);
        mContext = pContext;
        mPuzzleList = pList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View puzzleItem = convertView;

        if(puzzleItem == null) {
            puzzleItem = LayoutInflater.from(mContext).inflate(R.layout.puzzle_list_layout, parent, false);
        }

        Puzzle currentPuzzle = mPuzzleList.get(position);

        TextView puzzleName = (TextView) puzzleItem.findViewById(R.id.textView_puzzle_title);
        puzzleName.setText(currentPuzzle.getName());

        return puzzleItem;
    }
}
