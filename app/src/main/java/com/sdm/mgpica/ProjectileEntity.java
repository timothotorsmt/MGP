package com.sdm.mgpica;

import android.app.Notification;
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
    public float width = 150;

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
                R.drawable.projectile);
        sbmp = Bitmap.createScaledBitmap(bmp, (int)width,
                (int)width,true);
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

        if (PlayerEntity.Create().yPosOnScreen+yPos-PlayerEntity.Create().GetPosY() > (ScreenHeight - 300)) {
            ActionButtonEntity.Create().projectileEntities.remove(this);
            System.out.print(ActionButtonEntity.Create().projectileEntities.size());
            SetIsDone(true);
        }
    }

    public void Render(Canvas _canvas) {
        spritesheet.Render(_canvas, xPos, (int) (PlayerEntity.Create().yPosOnScreen+yPos-PlayerEntity.Create().GetPosY()));
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
        return (width / 2);
    }

    @Override
    public void OnHit(Collidable _other) {

    }
}