package com.sdm.mgpica;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class BlockEntity implements EntityBase, Collidable {

    private Bitmap bmp = null;
    private Bitmap scaledbmp1 = null, scaledbmp2 = null, scaledbmp3 = null;

    private boolean isDone = false;
    private int xPos = 0, yPos = 0;
    private int xOffset = 0;
    private int blockScale = 1;

    private boolean isInit = false;
    private boolean hasTouched = false;

    private int ScreenWidth, ScreenHeight;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.groundstone);
        // get the screen size
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledbmp1 = Bitmap.createScaledBitmap(bmp, (int) (ScreenWidth/5), 50, true);
        scaledbmp2 = Bitmap.createScaledBitmap(bmp, (int) (ScreenWidth/4), 50, true);
        scaledbmp3 = Bitmap.createScaledBitmap(bmp, (int) (ScreenWidth/3), 50, true);

        isInit = true;
    }

    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        yPos -= _dt * 80;
    }

    public void Setoffset (int NewxOffset) {
        xOffset = NewxOffset;
    }
    public void SetScale (int newScale) {
        blockScale = newScale;
    }

    public void Render(Canvas _canvas) {
        if (blockScale == 0) {
            _canvas.drawBitmap(scaledbmp1, xPos + xOffset, ScreenHeight - 50 + yPos, null);
        }
        else if (blockScale == 1) {
            _canvas.drawBitmap(scaledbmp2, xPos + xOffset, ScreenHeight - 50 + yPos, null);
        } else {
            _canvas.drawBitmap(scaledbmp3, xPos + xOffset, ScreenHeight - 50 + yPos, null);
        }
    }

    public boolean IsInit(){
        return bmp != null;
    }

    public void SetRenderLayer(int _newLayer){
        return;
    }

    public int GetRenderLayer(){
        return LayerConstants.TERRAIN_LAYER;
    }

    public ENTITY_TYPE GetEntityType(){

        return ENTITY_TYPE.ENT_SMURF;
    }

    public static BlockEntity Create(){
        BlockEntity result = new BlockEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_BLOCKS);
        return result;
    }

    @Override
    public String GetType() {
        return null;
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return 0;
    }

    @Override
    public void OnHit(Collidable _other) {

    }
}
