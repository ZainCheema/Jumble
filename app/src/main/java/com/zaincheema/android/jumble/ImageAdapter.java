package com.zaincheema.android.jumble;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> mImages;
    LayoutInflater inflater;

    Puzzle mPuzzle;

    ArrayList<String> puzzleLayout = new ArrayList<String>();

   String completed1 = "123456789101112";
   String completed2 = "131415161718192021222324";

    int highscore = 0;

   boolean gameWon = false;

    Integer activeTilePosition;
    Bitmap activeTileImage;
    ImageView activeTileView;

    FragmentManager fragmentManager;

    HashMap<String, Boolean> rotatedToBool = new HashMap<String, Boolean>();
    int movesCounter;

    public ImageAdapter(Context c, ArrayList<Bitmap> i, Puzzle p, FragmentManager fm) {
        mContext = c;
        mImages = i;
        mPuzzle = p;
        Log.e("ImageAdapter", String.valueOf(i.size()));

        fragmentManager = fm;

        highscore = getHighScore();

        puzzleLayout = mPuzzle.getLayout();

        for(int x = 0; x < puzzleLayout.size(); x++) {
            if(puzzleLayout.get(x).contains("r")) {
                String rotated = puzzleLayout.get(x);
                rotated = rotated.substring(0, rotated.length() - 1);
                //     System.out.println(rotated);
                puzzleLayout.set(x, rotated);
                rotatedToBool.put(rotated, true);
            }
        }
        inflater = (LayoutInflater.from(mContext));
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            imageView = (ImageView) view;
        }

        if (mImages.size() == 24) {

            int index = Integer.parseInt(puzzleLayout.get(i));
            imageView.setImageBitmap(mImages.get(index-1));



            imageView.setOnTouchListener(new TileTouchListener(mContext) {
                @Override
                public void onClick() {
                    super.onClick();
                   // Log.e("ImageAdapter.java", String.valueOf(i) + " tapped");

                    // Case where you want to deselect active tile
                    if(activeTilePosition != null && i == activeTilePosition) {
                        removeSelectionBorder(imageView);
                        Log.e("ImageAdapter.java", String.valueOf(i) + " is non-active");

                        activeTilePosition = null;
                        activeTileView = null;
                        activeTileImage = null;
                    }
                    // Case where you want to swap tiles
                    else if (activeTilePosition != null && i != activeTilePosition) {
                      //  Bitmap swapTileImage = mImages.get(i);

                        int imageIndex = Integer.parseInt(puzzleLayout.get(i)) - 1;

                      Bitmap swapTileImage =   mImages.get(imageIndex);

                        // Change the tapped tile's bitmap to the active tiles bitmap
                        imageView.setImageBitmap(activeTileImage);

                        // and vice versa
                        removeSelectionBorder(activeTileView);
                        activeTileView.setImageBitmap(swapTileImage);

                        Collections.swap(puzzleLayout, i , activeTilePosition );

                        System.out.println("ArrayList after swap:");

                        String result = "";

                        for(String temp: puzzleLayout){
                            result = result + temp;
                        }

                        movesCounter++;

                        if(result.equals(completed1) || result.equals(completed2)) {
                            Log.e("GAME", "PUZZLE COMPLETED: " + movesCounter);
                            if(movesCounter < getHighScore()) {
                                setHighScore(movesCounter);
                            }
                            fragmentManager.popBackStackImmediate();
                        }

                        activeTilePosition = null;
                        activeTileView = null;
                        activeTileImage = null;
                    }
                    // Case where you want to select a tile, activeTile would be null
                    else {
                        addSelectionBorder(imageView);
                        activeTilePosition = i;
                        int imageIndex = Integer.parseInt(puzzleLayout.get(activeTilePosition)) - 1;
                        activeTileView = imageView;
                        activeTileImage =  mImages.get(imageIndex);
                        Log.e("ImageAdapter.java", String.valueOf(i) + " is active");
                    }
                }

                @Override
                public void onMediumClick() {
                    super.onMediumClick();
                    // When active and medium pressed, rotate by 180
                    if(activeTilePosition != null && i == activeTilePosition) {
                        Log.e("medium click","Medium press when active");
                        Matrix matrix = new Matrix();
                        matrix.postRotate(180);
                        Bitmap rotatedTile = Bitmap.createBitmap(activeTileImage,
                                0, 0,
                                activeTileImage.getWidth(),
                                activeTileImage.getHeight(),
                                matrix,
                                true);

                        activeTileView.setImageBitmap(rotatedTile);

                       String rotateCheck = puzzleLayout.get(i).toString();

                        if(rotatedToBool.containsKey(rotateCheck)) {
                            rotatedToBool.put(rotateCheck, false);
                        }
                    }
                }

                @Override
                public void onLongCLick() {
                    super.onLongCLick();

                    Log.e("long click", "Puzzle tile flip to other side");

                    // If there is no active tile, flip the tile
                    if(activeTilePosition == null) {
                    int imageNumber = i;
                    int puzzleLayoutnumber = Integer.parseInt(puzzleLayout.get(imageNumber));
                 //   int imageIndex = Integer.parseInt(puzzleLayout.get(imageNumber) ) - 1;
                    if(puzzleLayoutnumber <= 12) {
                        puzzleLayoutnumber= puzzleLayoutnumber + 12;
                    } else {
                        puzzleLayoutnumber = puzzleLayoutnumber - 12;
                    }
                    activeTileView = imageView;
                    activeTileView.setImageBitmap(mImages.get(puzzleLayoutnumber - 1));
                    puzzleLayout.set(imageNumber, String.valueOf((puzzleLayoutnumber)));

                        movesCounter++;

                        String result = "";

                        for(String temp: puzzleLayout){
                            result = result + temp;
                        }

                        if(result.equals(completed1) || result.equals(completed2)) {
                            Log.e("GAME", "PUZZLE COMPLETED: " + movesCounter);
                            if(movesCounter < getHighScore()) {
                                setHighScore(movesCounter);
                           }
                            fragmentManager.popBackStackImmediate();
                        }
                    }
                }
            });
        }
        return imageView;
    }

    public void addSelectionBorder(ImageView view)
    {
        int border = 8;
        view.setPadding(border,border,border,border);
        view.setBackgroundColor(Color.CYAN);
    }
    public void removeSelectionBorder(ImageView view)
    {
        int border = 0;
        view.setPadding(border,border,border,border);
        view.setBackgroundColor(Color.BLACK);
    }

    public int getHighScore() {
        SharedPreferences prefs = mContext.getSharedPreferences(mPuzzle.getName(), Context.MODE_PRIVATE);
        return prefs.getInt(mPuzzle.getName(),  100);

    }

    public void setHighScore(int highScore) {
        SharedPreferences prefs = mContext.getSharedPreferences(mPuzzle.getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(mPuzzle.getName(), highScore);
        editor.apply();
    }





}

