package com.zaincheema.android.jumble;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.e("ImageAdapter", String.valueOf(mImages.size()));
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int width = metrics.widthPixels / 3;
            int height = metrics.heightPixels / 3;
            imageView.setLayoutParams(new GridView.LayoutParams(width, height));
        } else {
            imageView = (ImageView) view;
        }

        if (mImages.size() == 24) {
            imageView.setImageBitmap(mImages.get(i));
        } else {

            imageView.setImageResource(R.drawable.iggy);
        }


        return imageView;
    }
}

