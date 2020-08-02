package com.zaincheema.android.jumble;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class Puzzle {
    private String mName;
    private int mRows;
    private int mColumns;
    private ArrayList<String> mLayout = new ArrayList<String>();
    private ArrayList<String> mTilePaths = new ArrayList<String>();
    ArrayList<Bitmap> tiles = new ArrayList<Bitmap>();
    private MutableLiveData<ArrayList<Bitmap>> mTiles = new MutableLiveData<ArrayList<Bitmap>>();

    public Puzzle(String pName, int pRows, int pColumns, ArrayList<String> pLayout, ArrayList<String> pTilePaths) {
        setName(pName);
        setRows(pRows);
        setColumns(pColumns);
        setLayout(pLayout);
        setTilePaths(pTilePaths);

        mTiles.setValue(tiles);
    }

    public MutableLiveData<ArrayList<Bitmap>> getTiles() { return mTiles; }

    public void addTile(Bitmap pTile) {
       tiles = mTiles.getValue();
        tiles.add(pTile);
        mTiles.postValue(tiles);
    }

    public String getName() { return mName; }
    public void setName(String pName) { mName = pName; }

    public int getRows() { return mRows; }
    public void setRows(int pRows) { mRows = pRows; }

    public int getColumns() { return mColumns; }
    public void setColumns(int pColumns) { mColumns = pColumns; }

    public ArrayList<String> getLayout() { return mLayout; }
    public void setLayout(ArrayList<String> pLayout) { mLayout = pLayout; }

    public ArrayList<String> getTilePaths() { return mTilePaths; }
    public void setTilePaths(ArrayList<String> pTilePaths) { mTilePaths = pTilePaths; }


}
