package com.zaincheema.android.jumble;

import android.content.Context;
import android.graphics.Bitmap;
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

    public Integer[] thumbImages = {
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy,
            R.drawable.iggy
    };

    public ImageAdapter(Context c, ArrayList<Bitmap> i) {
        mContext = c;
        mImages = i;
        inflater = (LayoutInflater.from(mContext));
    }

    @Override
    public int getCount() {
        return thumbImages.length;
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
        view = inflater.inflate(R.layout.grid_image, null);

        Log.e("ImageAdapter", "Show Grid");

        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        imageView.setImageResource( R.drawable.iggy);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    //    imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return view;
    }
}
