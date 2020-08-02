package com.zaincheema.android.jumble;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


// Adapted from: https://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
// Specifically Jaydipsinhs Zala's answer

public class TileTouchListener implements View.OnTouchListener {

    private GestureDetectorCompat gestureDetector;

    public TileTouchListener(Context c) {
        GestureListener gl = new GestureListener();
        gestureDetector = new GestureDetectorCompat(c, gl);
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            onMediumClick();
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onClick();
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            onLongCLick();
            super.onLongPress(e);
        }
    }

    public void onClick() {

    }

    public void onMediumClick() {

    }

    public void onLongCLick() {

    }
}