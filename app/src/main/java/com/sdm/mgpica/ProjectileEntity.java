package com.sdm.mgpica;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class ProjectileEntity implements EntityBase, Collidable {
    // Comment for now and use if code from Slide no 7 is type in
    int ScreenWidth, ScreenHeight;

    private Bitmap bmp = null;
    private Bitmap sbmp = null;
    private Sprite spritesheet = null; // using Sprite class

    private boolean isDone = false;
    private int xPos = 0, yPos = 0;

    private boolean isInit = false;
    private boolean hasTouched = false;

    private boolean isMidair = true;
    private boolean isJumping = false;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        xPos = (int) PlayerEntity.Create().GetPosX();
        yPos = (int) PlayerEntity.Create().GetPosY();

        bmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.tile_0300);
        sbmp = Bitmap.createScaledBitmap(bmp, (int)150,
                (int)150,true);
        //spritesheet = new Sprite(bmp, 4,4, 16);

        //spritesheet = new Sprite(BitmapFactory.decodeResource(_view.getResources(),
        //                R.drawable.smurf_sprite), 4,4, 16);

        spritesheet = new Sprite(sbmp,
                1,1, 16);

        isInit = true;
    }

    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        yPos += _dt * 750;
        spritesheet.Update(_dt);
        //if (TouchManager.Instance.HasTouch())
        //{
        //    float imgRadius = spritesheet.GetWidth() * 0.5f;
        //    if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius)|| hasTouched)
        //    {
        //        hasTouched = true;
        //        Instance.xPos = TouchManager.Instance.GetPosX();
        //        Instance.yPos = TouchManager.Instance.GetPosY();
        //    }
        //}
        // addon codes provide on slides from Week 6 -- Slide no.7
    }

    public void Render(Canvas _canvas) {
        spritesheet.Render(_canvas, xPos, (int) (400+yPos-PlayerEntity.Create().GetPosY()));
    }

    public boolean IsInit(){

        return bmp != null;
    }

    public void SetRenderLayer(int _newLayer){
        return;
    }

    public int GetRenderLayer(){
        return LayerConstants.RENDERPROJECTILE_LAYER;
    }

    public ENTITY_TYPE GetEntityType(){

        return ENTITY_TYPE.ENT_PROJECTILE;
    }

    public static ProjectileEntity Create(){
        ProjectileEntity result = new ProjectileEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_PROJECTILE);
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

    public void SetPosX(int xPos) {
        this.xPos = xPos;
    }

    @Override
    public float GetRadius() {
        return 0;
    }

    @Override
    public void OnHit(Collidable _other) {

    }
}
