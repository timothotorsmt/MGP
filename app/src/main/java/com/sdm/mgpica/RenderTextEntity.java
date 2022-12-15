package com.sdm.mgpica;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

public class RenderTextEntity implements EntityBase {

    // Paint object
    Paint paint = new Paint();
    private int red = 0, green = 0, blue = 0;

    //Our own font type to print text on screen
    Typeface myfont;

    private boolean isDone = false;
    private int xPos = 0, yPos = 0;

    private boolean isInit = false;

    int frameCount;
    long lastTime = 0;
    long lastFPSTime = 0;
    float fps;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {

        // default font type in android
        myfont = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL);

        // own font type
        //myfont = Typeface.createFromFile("fonts/gemcutbold.otf");

        isInit = true;
    }

    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        frameCount++;
        long currentTime = System.currentTimeMillis();

        lastTime = currentTime;
        if (currentTime - lastFPSTime > 1000){
            fps = (frameCount * 1000.f) / (currentTime - lastFPSTime);
            lastFPSTime =currentTime;
            frameCount = 0;
        }
    }

    public void Render(Canvas _canvas) {
        Paint paint = new Paint();
        paint.setARGB(255, 255,255,255); // number range from 0-255
        paint.setStrokeWidth(200);
        paint.setTextSize(100);
        paint.setTypeface(myfont);
        _canvas.drawText("Fuck this module", 30, 80 , paint);
    }

    public boolean IsInit(){
        return true; // Not loading any images hence not the same as Smurf entity
    }

    public void SetRenderLayer(int _newLayer){
        return;
    }

    public int GetRenderLayer(){
        return LayerConstants.RENDERTEXT_LAYER;
    }

    public ENTITY_TYPE GetEntityType(){

        return ENTITY_TYPE.ENT_TEXT;
    }

    public static RenderTextEntity Create(){
        RenderTextEntity result = new RenderTextEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_TEXT);
        return result;
    }
}
