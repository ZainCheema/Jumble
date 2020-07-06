package com.zaincheema.android.jumble;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Puzzle {
    private String mName;
    private int mRows;
    private int mColumns;
    private ArrayList<ArrayList<String>> mLayout = new ArrayList<ArrayList<String>>();
    private ArrayList<String> mTilePaths = new ArrayList<String>();
    private ArrayList<Bitmap> mTiles = new ArrayList<Bitmap>();

    public Puzzle(String pName, int pRows, int pColumns, ArrayList<ArrayList<String>> pLayout, ArrayList<String> pTilePaths) {
        setName(pName);
        setRows(pRows);
        setColumns(pColumns);
        setLayout(pLayout);
        setTilePaths(pTilePaths);
    }

    public ArrayList<Bitmap> getTiles() { return mTiles; }
    public void addTile(Bitmap pTile) { mTiles.add(pTile); }

    public String getName() { return mName; }
    public void setName(String pName) { mName = pName; }

    public int getRows() { return mRows; }
    public void setRows(int pRows) { mRows = pRows; }

    public int getColumns() { return mColumns; }
    public void setColumns(int pColumns) { mColumns = pColumns; }

    public ArrayList<ArrayList<String>> getLayout() { return mLayout; }
    public void setLayout(ArrayList<ArrayList<String>> pLayout) { mLayout = pLayout; }

    public ArrayList<String> getTilePaths() { return mTilePaths; }
    public void setTilePaths(ArrayList<String> pTilePaths) { mTilePaths = pTilePaths; }


}
