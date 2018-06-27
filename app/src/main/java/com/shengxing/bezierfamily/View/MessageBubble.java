package com.shengxing.bezierfamily.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.shengxing.bezierfamily.R;

/**
 * Fun:消息气泡拖曳消失
 * Created by sxx.xu on 6/27/2018.
 */

public class MessageBubble extends FrameLayout {

  public static final float DEFAULT_RADIUS = 20;//默认定点圆半径

  Paint paint;
  Path path;

  //手势座标
  float x = 300;
  float y = 300;

  //锚点座标
  float anchorX = 200;
  float anchorY = 200;

  //起点座标
  float startX = 100;
  float startY = 100;

  //定点圆半径
  float radius = DEFAULT_RADIUS;

  boolean isAnimStart;//动画是否已经开始
  boolean isTouch;// 是否开始拖动

  ImageView explorImageView;
  ImageView tipImageView;

  public MessageBubble(Context context) {
    super(context);
    init();
  }

  public MessageBubble(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public MessageBubble(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  void init() {
    path = new Path();
    paint = new Paint();

    paint.setAntiAlias(true);//抗锯齿(平滑绘制)
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    paint.setStrokeWidth(2);//笔画宽度
    paint.setColor(Color.RED);

    LayoutParams params =
        new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    explorImageView = new ImageView(getContext());
    explorImageView.setLayoutParams(params);
    explorImageView.setImageResource(R.drawable.tip_anim);
    explorImageView.setVisibility(View.INVISIBLE);

    tipImageView = new ImageView(getContext());
    tipImageView.setLayoutParams(params);
    tipImageView.setImageResource(R.mipmap.skin_tips_newmessage_ninetynine);

    addView(explorImageView);
    addView(tipImageView);
  }

  @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    explorImageView.setX(startX - explorImageView.getWidth() / 2);
    explorImageView.setY(startY - explorImageView.getHeight() / 2);

    tipImageView.setX(startX - tipImageView.getWidth() / 2);
    tipImageView.setY(startY - tipImageView.getHeight() / 2);

    super.onLayout(changed, left, top, right, bottom);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      //判断触摸点是否在tipImageView中
      Rect rect = new Rect();
      int[] location = new int[2];
      tipImageView.getDrawingRect(rect);
      tipImageView.getLocationOnScreen(location);
      rect.left = location[0];
      rect.top = location[1];
      rect.right = rect.right + location[0];
      rect.bottom = rect.bottom + location[1];
      if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
        isTouch = true;
      }
    } else if (event.getAction() == MotionEvent.ACTION_UP
        || event.getAction() == MotionEvent.ACTION_CANCEL) {
      isTouch = false;
      tipImageView.setX(startX - tipImageView.getWidth() / 2);
      tipImageView.setY(startY - tipImageView.getHeight() / 2);
    }
    invalidate();
    if (isAnimStart) {
      return super.onTouchEvent(event);
    }
    anchorX = (event.getX() + startX) / 2;
    anchorY = (event.getY() + startY) / 2;
    x = event.getX();
    y = event.getY();
    return true;
  }

  @Override protected void onDraw(Canvas canvas) {
    if (isAnimStart || !isTouch) {
      canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
    } else {
      calulate();
      canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
      canvas.drawPath(path, paint);
      canvas.drawCircle(startX, startY, radius, paint);
      canvas.drawCircle(x, y, radius, paint);
    }
    super.onDraw(canvas);
  }

  private void calulate() {
    float distance = (float) Math.sqrt(Math.pow(y - startY, 2) + Math.pow(x - startX, 2));
    radius = -distance / 15 + DEFAULT_RADIUS;

    if (radius < 0) {
      isAnimStart = true;
      explorImageView.setVisibility(VISIBLE);
      explorImageView.setImageResource(R.drawable.tip_anim);
      ((AnimationDrawable) explorImageView.getDrawable()).stop();
      ((AnimationDrawable) explorImageView.getDrawable()).start();

      tipImageView.setVisibility(GONE);
    }

    // 根据角度算出四边形的四个点
    float offsetX = (float) (radius * Math.sin(Math.atan((y - startY) / (x - startX))));
    float offsetY = (float) (radius * Math.cos(Math.atan((y - startY) / (x - startX))));

    float x1 = startX - offsetX;
    float y1 = startY + offsetY;

    float x2 = x - offsetX;
    float y2 = y + offsetY;

    float x3 = x + offsetX;
    float y3 = y - offsetY;

    float x4 = startX + offsetX;
    float y4 = startY - offsetY;

    path.reset();
    path.moveTo(x1, y1);
    path.quadTo(anchorX, anchorY, x2, y2);
    path.lineTo(x3, y3);
    path.quadTo(anchorX, anchorY, x4, y4);
    path.lineTo(x1, y1);

    tipImageView.setX(x - tipImageView.getWidth() / 2);
    tipImageView.setY(y - tipImageView.getHeight() / 2);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
  }
}
