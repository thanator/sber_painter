package com.tan_ds.painter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Tan-DS on 4/22/2017.
 */

public class PaintingView extends View {

    private float lastX, lastY, startX ,startY;
    public boolean flag;
    Rect myRect = new Rect();

    // для мультитача
    private SparseArray<PointF> lastPoints = new SparseArray<>(10);

    private Bitmap cacheBitmap;
    private Paint linePaint;
    private Canvas bitmapCanvas;


    public PaintingView(Context context) {
        super(context);
        init();
    }

    public PaintingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaintingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    // для размера и создания битмапа


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w != 0 && h != 0){
            // chto delat so starim - delete
            if (cacheBitmap != null){
                cacheBitmap.recycle();
            }
            // sozdai noviy
            cacheBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(cacheBitmap); // для каждого изменения битмапа

        }
        init();
    }



    @Override
    // event нам гарантирует, что будем работать только от начаал нажатия пальцем и до отпуска
    public boolean onTouchEvent(MotionEvent event) {


        // загатовки под обработку события
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                // multitouch
                clear();
                startX = lastX = event.getX();
                startY = lastY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:

                if (flag) { // true -> rectangle
                    lastX = event.getX();
                    lastY = event.getY();
                    clear();
                    bitmapCanvas.drawLine(startX,startY,startX,lastY,linePaint);
                    bitmapCanvas.drawLine(startX,startY,lastX,startY,linePaint);
                    bitmapCanvas.drawLine(lastX,startY,lastX,lastY,linePaint);
                    bitmapCanvas.drawLine(startX,lastY,lastX,lastY,linePaint);
                    //bitmapCanvas.drawRect(startX,startY,lastX,lastY, linePaint); // старый-добрый прямоугольник в лоб
                } else { // not true -> line
                    bitmapCanvas.drawLine(lastX,lastY, event.getX(), event.getY(), linePaint);
                    lastX = event.getX();
                    lastY = event.getY();
                }



                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return true;

        }
        init();
        return super.onTouchEvent(event);
    }

    // переопределим рисование
    @Override
    protected void onDraw(Canvas canvas) {

        if (isInEditMode()){
            canvas.drawRect(0.1f*getWidth(), 0.1f*getHeight(), 0.9f*getWidth(), 0.9f*getWidth(), linePaint);
        }


        canvas.drawBitmap(cacheBitmap, 0,0, null);
        init();

    }

    private void init(){
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setColor(getResources().getColor(R.color.colorAccent));
        linePaint.setStrokeWidth(getResources().getDimension(R.dimen.default_line_width));

    }

    public void clear(){
        bitmapCanvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        invalidate();
    }


}
