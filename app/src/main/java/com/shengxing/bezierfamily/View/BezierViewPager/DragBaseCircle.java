package com.shengxing.bezierfamily.View.BezierViewPager;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.shengxing.bezierfamily.ScreenUtil;

/**
 * Fun:
 * Created by sxx.xu on 6/28/2018.
 */

public class DragBaseCircle extends BaseCircle {
  float startX;
  Matrix mMatrix = new Matrix();

  public DragBaseCircle(Context context) {
    super(context);
  }

  public DragBaseCircle(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public DragBaseCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        startX = event.getX();
        break;
      case MotionEvent.ACTION_MOVE:
        if (event.getX() > startX) {
          //resetPoint();
          //mMatrix.preScale(1, -1);
          //mPath.transform(mMatrix);
          p2 = new PointF(event.getX() - ScreenUtil.getScreenWH(getContext()).x / 2,
              -mRadius * mFactor);
          p3 = new PointF(event.getX() - ScreenUtil.getScreenWH(getContext()).x / 2, 0);
          p4 = new PointF(event.getX() - ScreenUtil.getScreenWH(getContext()).x / 2,
              mRadius * mFactor);
        }
        if (event.getX() < startX) {
          //resetPoint();
          //mMatrix.preScale(-1, 1);
          //mPath.transform(mMatrix);

          p8 = new PointF(ScreenUtil.getScreenWH(getContext()).x / 2 - event.getX(),
              mRadius * mFactor);
          p9 = new PointF(ScreenUtil.getScreenWH(getContext()).x / 2 - event.getX(), 0);
          p10 = new PointF(ScreenUtil.getScreenWH(getContext()).x / 2 - event.getX(),
              -mRadius * mFactor);
        }
        invalidate();
        break;
    }
    return true;
  }
}
