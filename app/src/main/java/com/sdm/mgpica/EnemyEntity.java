package com.sdm.mgpica;

// Written by Timothy Luk
// Edited by TanSzeTing2022

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class EnemyEntity implements EntityBase, Collidable {
    // Comment for now and use if code from Slide no 7 is type in
    int ScreenWidth, ScreenHeight;

    private Bitmap bmp = null;
    private Bitmap sbmp = null;
    private Sprite spritesheet = null; // using Sprite class

    private boolean isDone = false;
    private int xPos = 0, yPos = -50;

    private Vibrator _vibrator;

    private boolean isInit = false;
    private boolean hasTouched = false;

    public float speed = 100;

    // Temporary Jump Timer
    private float JumpTimer = 0;
    public boolean isMoving = true;

    private int iHealth = 100;
    public int width = 150;

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

        int x = (int)Math.random() * ScreenWidth;
        xPos = x;
        yPos = ScreenHeight + 50;
        bmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.enemy);
        sbmp = Bitmap.createScaledBitmap(bmp, (int)width,
                (int)width,true);
        //spritesheet = new Sprite(bmp, 4,4, 16);

        //spritesheet = new Sprite(BitmapFactory.decodeResource(_view.getResources(),
        //                R.drawable.smurf_sprite), 4,4, 16);

        spritesheet = new Sprite(sbmp,
                1,1, 16);
        isInit = true;
        _vibrator = (Vibrator)_view.getContext().getSystemService (_view.getContext().VIBRATOR_SERVICE);
    }

    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        spritesheet.Update(_dt);

        // move relatively to playerPos
        if (xPos >= PlayerEntity.Create().GetPosX()) {
            xPos -= _dt * speed;
        } else {
            xPos += _dt * speed;
        }

        // TODO: Follow Y Position
        yPos -= _dt * (speed + PlayerEntity.Create().speed);
    }

    public void collision() {
        // Collision (i think)
        AABB playerAABB = new AABB();
        playerAABB.minX = PlayerEntity.Create().GetPosX();
        playerAABB.minY = PlayerEntity.Create().yPosOnScreen;
        playerAABB.height = PlayerEntity.Create().width;
        playerAABB.width = PlayerEntity.Create().width;

        AABB enemyAABB = new AABB();
        enemyAABB.minX = xPos;
        enemyAABB.minY = yPos;
        enemyAABB.width = width;
        enemyAABB.height = width;

        //i could have used sphere to sphere wait...
        if (Collision.AABBtoAABB(playerAABB, enemyAABB)) {
            startVibrate();
            if (enemyAABB.minY - playerAABB.minY > ((playerAABB.height / 2) + 50)) {
                // stomp and die bitch
                PlayerEntity.Create().SetStall();

                xPos = -100;
                PlayerEntity.Create().isMidair = false;
                PlayerEntity.Create().iEnemyKillScore += 300;
                SetIsDone(true);
            } else {
                if (PlayerEntity.Create().isDamagable) {
                    //TODO: Set the damagability
                    PlayerEntity.Create().iHealth -= 25;
                    xPos = -100;
                    SetIsDone(true);

                }
            }
        }

        // TODO: figure out why this is lagging so fucking badly
        float imgRadius = width  * 0.5f;
        for (ProjectileEntity pe : ActionButtonEntity.Create().projectileEntities) {
            AABB ProjectileAABB = new AABB();
            ProjectileAABB.minX = pe.GetPosX();
            ProjectileAABB.minY = PlayerEntity.Create().yPosOnScreen + pe.GetPosY() - PlayerEntity.Create().GetPosY();
            ProjectileAABB.height = pe.width;
            ProjectileAABB.width = pe.width;

            if (Collision.AABBtoAABB(ProjectileAABB, enemyAABB)) {
                startVibrate();
                xPos = -100;
                SetIsDone(true);
                pe.SetPosX(-100);
                pe.SetIsDone(true);
            }
        }
    }

    public void Render(Canvas _canvas) {
        spritesheet.Render(_canvas, xPos, yPos);
    }

    public boolean IsInit(){

        return bmp != null;
    }

    public void SetRenderLayer(int _newLayer){
        return;
    }

    public int GetRenderLayer(){
        return LayerConstants.RENDERPLAYER_LAYER;
    }

    public static EnemyEntity Create() {
        EnemyEntity result = new EnemyEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_ENEMY);
        return result;
    }

    public EntityBase.ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_ENEMY;
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

    public void startVibrate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            _vibrator.vibrate(500);
        }
    }

    public void stopVibrate(){
        _vibrator.cancel();
    }
}
