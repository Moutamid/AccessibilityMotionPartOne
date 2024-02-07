package com.dr.accessibilitymotion;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.accessibilityservice.GestureDescription.StrokeDescription;
import android.graphics.Path;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fxn.stash.Stash;

public class MotionService extends AccessibilityService {
    private static final String TAG = "MotionService";

//    private boolean canScroll = true;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(getApplicationContext(), "Connected!", Toast.LENGTH_SHORT).show();
        });
    }

    boolean continueBool = true;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null || event.getSource() == null) return;

        if (Stash.getBoolean("key", false))
            return;

//        Log.d(TAG, "onAccessibilityEvent/32: getViewIdResourceName : " + event.getSource().getViewIdResourceName());
//        Log.d(TAG, "onAccessibilityEvent/32: getUniqueId : " + event.getSource().getUniqueId());
//        Log.d(TAG, "onAccessibilityEvent/32: getClassName : " + event.getSource().getClassName());
//        Log.d(TAG, "onAccessibilityEvent/32: getHintText : " + event.getSource().getHintText());
//        Log.d(TAG, "onAccessibilityEvent/32: getText : " + event.getSource().getText());
//        Log.d(TAG, "onAccessibilityEvent/32:\n ");

        if (event.getPackageName().equals("com.zhiliaoapp.musically") &&
                event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED
                && continueBool) {

            continueBool = false;

            new Handler().postDelayed(() -> {
                clickWindow(event);
                continueBool = true;
            }, 1000);

            Log.d(TAG, "onAccessibilityEvent/34: scrollY : " + event.getScrollY());
//            Log.d(TAG, "onAccessibilityEvent/34: scrollDeltaY : " + event.getScrollDeltaY());
//            canScroll = true;
        }

//        Log.d(TAG, "onAccessibilityEvent/30:  : " + );
//        Log.d(TAG, "onAccessibilityEvent/29: eventName : " + event.getSource().getViewIdResourceName());
//        scrollWindow(event);
    }

    private void clickWindow(AccessibilityEvent event) {
        if (event.getPackageName().equals("com.zhiliaoapp.musically")) {
//            if (canScroll) {
//                for (int i = 0; i < event.getSource().getChildCount(); i++) {
//                    AccessibilityNodeInfo child = event.getSource().getChild(i);
//                    if (child != null && canChildScroll(child)) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
            Point position = new Point();
            position.x = getResources().getDisplayMetrics().widthPixels / 2;
//                        position.x = 5;
            position.y = getResources().getDisplayMetrics().heightPixels / 2;
            GestureDescription.Builder builder = new GestureDescription.Builder();
            Path path = new Path();
            path.moveTo(position.x, position.y);
            builder.addStroke(new StrokeDescription(path, 50L, 1L));
            GestureDescription gesture = builder.build();
            boolean dispatched = dispatchGesture(gesture, new GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    Log.d("Gesture", "Completed");
                }
            }, null);
//            if (dispatched) {
//                Log.d("Gesture", "Dispatched");
//            }
//                    }
//                }, 3000);
        }
//            canScroll = false;

    }

//}

    private void scrollWindow(AccessibilityEvent event) {
        if (event.getPackageName().equals("com.zhiliaoapp.musically")) {
//            if (canScroll) {
//                for (int i = 0; i < event.getSource().getChildCount(); i++) {
//                    AccessibilityNodeInfo child = event.getSource().getChild(i);
//                    if (child != null && canChildScroll(child)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Point position = new Point();
//                        position.x = getResources().getDisplayMetrics().widthPixels / 2;
//                        position.y = getResources().getDisplayMetrics().heightPixels / 2;
                    position.x = 1;
                    position.y = getResources().getDisplayMetrics().heightPixels / 2;

                    GestureDescription.Builder builder = new GestureDescription.Builder();
                    Path path = new Path();
                    path.moveTo(position.x, position.y);
                    path.lineTo(position.x, position.y - 200);
                    builder.addStroke(new StrokeDescription(path, 0L, 50L));
                    GestureDescription gesture = builder.build();
                    boolean dispatched = dispatchGesture(gesture, new GestureResultCallback() {
                        @Override
                        public void onCompleted(GestureDescription gestureDescription) {
                            super.onCompleted(gestureDescription);
                            Log.d("Gesture", "Completed");
                            new Handler(Looper.getMainLooper()).post(() -> {
                                Toast.makeText(getApplicationContext(), "Swiped!", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }, null);
                }
            }, 3000);

//                        canScroll = false;
//                    }
//                }
//            }
//            canScroll = false;

        }
    }

    private boolean canChildScroll(AccessibilityNodeInfo view) {
        return view.isScrollable();
    }

    private void startTimer() {
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // do nothing
            }

            @Override
            public void onFinish() {
//                canScroll = true;
            }
        }.start();
    }

    @Override
    public void onInterrupt() {
        // Not yet implemented
    }
}
