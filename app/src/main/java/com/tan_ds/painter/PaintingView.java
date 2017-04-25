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
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.tan_ds.painter.R.string.line;

/**
 * Created by Tan-DS on 4/22/2017.
 */

public class PaintingView extends View {

    private float lastX, lastY, startX ,startY;
    public int flag;
    public int colInt, fig_1 = 0, fig_2 = 0;

    public enum Perechisl{
        lining,
        rect,
        fignia
    }
    public Perechisl figure;
    // для мультитача
    private SparseArray<PointF> lastPoints = new SparseArray<>(2);

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


        // TextView tex = (TextView) findViewById(R.id.col_vo);
        // tex.setText(((Integer)event.getPointerCount()).toString()) ;
        // загатовки под обработку события
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // multitouch
                // clear();
                int pointerId = event.getPointerId(event.getActionIndex());
                lastPoints.put(pointerId, new PointF(event.getX(event.getActionIndex()),
                                                    event.getY(event.getActionIndex())));

                startX = lastX = event.getX();
                startY = lastY = event.getY();

                if (colInt == 1){
                    flag =  event.getPointerId(event.getActionIndex());
                }

                colInt ++;


                return true;
            case MotionEvent.ACTION_MOVE:

                    if (figure == Perechisl.rect || figure == Perechisl.fignia) { // true -> rectangle || fignia

                        if(colInt == 2) { // для двух пальцев
                            for (int i = 0; i < event.getPointerCount(); i++) {

                                PointF last = lastPoints.get(event.getPointerId(i));

                                clear();
                                startY = lastY;
                                startX = lastX;

                                last.x = lastX;
                                last.y = lastY;

                                lastX = event.getX(i);
                                lastY = event.getY(i);

                                if (figure == Perechisl.rect){
                                    bitmapCanvas.drawLine(last.x, last.y, last.x, lastY, linePaint);
                                    bitmapCanvas.drawLine(last.x, last.y, lastX, last.y, linePaint);
                                    bitmapCanvas.drawLine(lastX, last.y, lastX, lastY, linePaint);
                                    bitmapCanvas.drawLine(last.x, lastY, lastX, lastY, linePaint);
                                }
                                if (figure == Perechisl.fignia){
                                    Drawable d = getResources().getDrawable(R.drawable.shapedrawable);
                                    d.setBounds((int)last.x, (int)last.y, (int)lastX, (int)lastY);
                                    d.draw(bitmapCanvas);
                                }

                                //bitmapCanvas.drawRect(last.x, last.y,lastX,lastY, linePaint); // старый-добрый прямоугольник влоб

                                /*bitmapCanvas.drawLine(startX, startY, startX, lastY, linePaint);
                                bitmapCanvas.drawLine(startX, startY, lastX, startY, linePaint);
                                bitmapCanvas.drawLine(lastX, startY, lastX, lastY, linePaint);
                                bitmapCanvas.drawLine(startX, lastY, lastX, lastY, linePaint);*/

                            }

                        } else if (colInt == 1){ // для одного пальца

                            lastX = event.getX();
                            lastY = event.getY();

                            clear();
                            if (figure == Perechisl.rect){
                                bitmapCanvas.drawLine(startX,startY,startX,lastY,linePaint);
                                bitmapCanvas.drawLine(startX,startY,lastX,startY,linePaint);
                                bitmapCanvas.drawLine(lastX,startY,lastX,lastY,linePaint);
                                bitmapCanvas.drawLine(startX,lastY,lastX,lastY,linePaint);
                            }
                            if (figure == Perechisl.fignia){
                                Drawable d = getResources().getDrawable(R.drawable.shapedrawable);
                                d.setBounds((int)startX, (int)startY, (int)lastX, (int)lastY);
                                d.draw(bitmapCanvas);
                            }


                        }
                    } else if (figure == Perechisl.lining) { // not true -> line

                        for (int i = 0; i < event.getPointerCount(); i++) {

                            PointF last = lastPoints.get(event.getPointerId(i));
                            lastX = event.getX(i);
                            lastY = event.getY(i);
                            bitmapCanvas.drawLine(last.x, last.y, lastX, lastY, linePaint);
                            last.x = lastX;
                            last.y = lastY;
                        }
                    }

                invalidate();
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                if ((figure == Perechisl.rect || figure == Perechisl.fignia) && (event.getPointerId(event.getActionIndex()) == flag)){
                    startX = lastX;
                    startY = lastY;
                }
                colInt--;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                colInt--;
                lastPoints.clear();
                return true;

        }
        //viewColVo.setText(((Integer)colInt).toString());

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
/*
    public void say(){
        TextView viewColVo = (TextView) findViewById(R.id.col_vo);
        viewColVo.setText("qwerty");
    }
*/
}
