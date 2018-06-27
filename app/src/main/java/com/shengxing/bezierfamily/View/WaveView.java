package com.shengxing.bezierfamily.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

/**
 * Fun: 波浪
 * Created by sxx.xu on 6/27/2018.
 */

public class WaveView extends View {
  private Paint mPaint;
  private Path mPath;
  private PointF start, end;
  private int controlx, controly;
  private int mScreenW, mScreenH;
  private int mOffset;

  public WaveView(Context context) {
    super(context);
    init();
  }

  public WaveView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  void init() {

    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    Point outSize = new Point();
    wm.getDefaultDisplay().getSize(outSize);
    mScreenH = outSize.y;
    mScreenW = outSize.x;

    mPath = new Path();
    mPaint = new Paint();
    mPaint.setColor(Color.BLUE);
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setStrokeWidth(8);

    setViewAnimator();
  }

  private void setViewAnimator() {
    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mScreenW);
    valueAnimator.setDuration(1200);
    valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        mOffset = (int) animation.getAnimatedValue();
        invalidate();
      }
    });
    valueAnimator.start();
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPath.reset();
    mPath.moveTo(-mScreenW + mOffset, mScreenH / 2);
    for (int i = 0; i < 2; i++) {//如果我们连续调用quadTo()，前一个quadTo()的终点，就是下一个quadTo()函数的起点
      mPath.quadTo(-mScreenW * 3 / 4 + (mScreenW * i) + mOffset, mScreenH / 2 - 100,
          -mScreenW / 2 + (mScreenW * i) + mOffset, mScreenH / 2);
      mPath.quadTo(-mScreenW / 4 + (mScreenW * i) + mOffset, mScreenH / 2 + 100,
          mScreenW * i + mOffset, mScreenH / 2);
    }
    mPath.lineTo(mScreenW, mScreenH);
    mPath.lineTo(0, mScreenH);
    canvas.drawPath(mPath, mPaint);
  }
}
