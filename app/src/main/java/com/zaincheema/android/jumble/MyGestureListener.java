package com.zaincheema.android.jumble;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

// Adapted from: https://stackoverflow.com/questions/45054908/how-to-add-a-gesture-detector-to-a-view-in-android

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        return result;
    }
}