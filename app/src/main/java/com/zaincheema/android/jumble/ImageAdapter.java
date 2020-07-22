package com.zaincheema.android.jumble;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> mImages;
    LayoutInflater inflater;

    Integer activeTilePosition;
    Bitmap activeTileImage;
    ImageView activeTileView;

    public ImageAdapter(Context c, ArrayList<Bitmap> i) {
        mContext = c;
        mImages = i;
        Log.e("ImageAdapter", String.valueOf(i.size()));
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
            imageView.setImageBitmap(mImages.get(i));
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
                        Bitmap swapTileImage = mImages.get(i);

                        // Change the tapped tile's bitmap to the active tiles bitmap
                        imageView.setImageBitmap(activeTileImage);

                        // and vice versa
                        removeSelectionBorder(activeTileView);
                        activeTileView.setImageBitmap(swapTileImage);


                        mImages.set(i, activeTileImage);
                        mImages.set(activeTilePosition, swapTileImage);

                        activeTilePosition = null;
                        activeTileView = null;
                        activeTileImage = null;
                    }
                    // Case where you want to select a tile, activeTile would be null
                    else {
                        addSelectionBorder(imageView);
                        activeTilePosition = i;
                        activeTileView = imageView;
                        activeTileImage = mImages.get(i);
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

                        mImages.set(i, rotatedTile);
                    }
                }
            });
        }
        return imageView;
    }

    private void setPuzzleLayout() {

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


}

