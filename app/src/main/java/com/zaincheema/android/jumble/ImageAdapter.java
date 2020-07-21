package com.zaincheema.android.jumble;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> mImages;
    private GestureDetector mDetector;
    LayoutInflater inflater;

    public ImageAdapter(Context c, ArrayList<Bitmap> i) {
        mContext = c;
        mImages = i;
        Log.e("ImageAdapter", String.valueOf(i.size()));
        inflater = (LayoutInflater.from(mContext));
        mDetector = new GestureDetector(mContext, new MyGestureListener());
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

        Log.e("ImageAdapter", "Image taken: " + String.valueOf(i));

        if (view == null) {
            Log.e("ImageAdapter", "ImageView is null");
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

        }
        return imageView;
    }

    private void setPuzzleLayout() {

    }

}

