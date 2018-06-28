package com.shengxing.bezierfamily;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Fun:
 * Created by sxx.xu on 6/28/2018.
 */

public final class ScreenUtil {
  private ScreenUtil() {
    throw new RuntimeException("don't do this");
  }

  public static Point getScreenWH(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Point outSize = new Point();
    wm.getDefaultDisplay().getSize(outSize);
    return outSize;
  }
}
