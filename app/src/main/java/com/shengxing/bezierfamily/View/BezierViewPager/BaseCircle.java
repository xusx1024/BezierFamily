package com.shengxing.bezierfamily.View.BezierViewPager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.shengxing.bezierfamily.ScreenUtil;

/**
 * Fun:基本功一:做圆
 * Created by sxx.xu on 6/28/2018.
 */

public class BaseCircle extends View {
  protected final float mFactor = 0.551915024494f;
  protected PointF p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
  protected int mRadius = 90;//半径
  private Paint mPaint;
  protected Path mPath;

  public BaseCircle(Context context) {
    super(context);
    init();
  }

  public BaseCircle(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public BaseCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    initPointF();
    mPaint = new Paint();
    mPaint.setColor(Color.RED);
    mPaint.setStrokeWidth(8);
    mPaint.setStyle(Paint.Style.FILL);

    mPath = new Path();
  }

  private void initPointF() {
    p0 = new PointF(0, -mRadius);
    p6 = new PointF(0, mRadius);

    p3 = new PointF(mRadius, 0);
    p9 = new PointF(-mRadius, 0);

    p1 = new PointF(mRadius * mFactor, -mRadius);
    p5 = new PointF(mRadius * mFactor, mRadius);

    p11 = new PointF(-mRadius * mFactor, -mRadius);
    p7 = new PointF(-mRadius * mFactor, mRadius);

    p2 = new PointF(mRadius, -mRadius * mFactor);
    p4 = new PointF(mRadius, mRadius * mFactor);

    p10 = new PointF(-mRadius, -mRadius * mFactor);
    p8 = new PointF(-mRadius, mRadius * mFactor);
  }

  protected void resetPoint() {
    initPointF();
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.translate(ScreenUtil.getScreenWH(getContext()).x / 2,
        ScreenUtil.getScreenWH(getContext()).y / 2);
    mPath.reset();
    mPath.moveTo(p0.x, p0.y);
    mPath.cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);

    mPath.cubicTo(p4.x, p4.y, p5.x, p5.y, p6.x, p6.y);

    mPath.cubicTo(p7.x, p7.y, p8.x, p8.y, p9.x, p9.y);

    mPath.cubicTo(p10.x, p10.y, p11.x, p11.y, p0.x, p0.y);

    canvas.drawPath(mPath, mPaint);
    mPath.close();
  }
}
