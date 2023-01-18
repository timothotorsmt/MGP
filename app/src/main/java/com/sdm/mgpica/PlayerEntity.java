package com.sdm.mgpica;

// Written by Tan Sze Ting
// Edited by Timothy

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.widget.Switch;
import android.view.View;

import javax.xml.transform.sax.SAXSource;

public class PlayerEntity implements EntityBase, Collidable, SensorEventListener {
    private static PlayerEntity Instance = null;

    // Comment for now and use if code from Slide no 7 is type in
    int ScreenWidth, ScreenHeight;

    private Bitmap bmp = null;
    private Bitmap sbmp = null;
    private Sprite spritesheet = null; // using Sprite class

    private boolean isDone = false;
    private int xPos = 0, yPos = 0;
    public int yPosOnScreen = 600;
    public int idealyPosOnScreen = 600;

    public int AmmoNumber = 8;
    public int MaxAmmoNumber = 8;

    private boolean isInit = false;
    private boolean hasTouched = false;

    public float speed = 0;
    public int ControlScheme = 1;

    public boolean isMidair = true;
    public boolean isStalling = true;
    public boolean isJumping = false;
    public boolean isDamagable = true;


    // Temporary Jump Timer
    private float JumpTimer = 0;
    public boolean isMoving = true;

    public int iEnemyKillScore = 0;
    public int iTotalScore = 0;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] values = {0, 0, 0};

    public int iHealth = 0;
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

        xPos = ScreenWidth/2;
        yPos = yPosOnScreen;
        bmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.tile_0300);
        sbmp = Bitmap.createScaledBitmap(bmp, (int)width,
                (int)width,true);
        //spritesheet = new Sprite(bmp, 4,4, 16);

        //spritesheet = new Sprite(BitmapFactory.decodeResource(_view.getResources(),
        //                R.drawable.smurf_sprite), 4,4, 16);

        spritesheet = new Sprite(sbmp,
                1,1, 16);

        sensorManager = (SensorManager)_view.getContext().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);

        iHealth = 100;
        AmmoNumber = 8;

        isInit = true;
    }

    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        spritesheet.Update(_dt);


        if (Math.abs(yPosOnScreen - idealyPosOnScreen) > 0.1) {
            yPosOnScreen = (int)LinearInterpolation.Lerp(yPosOnScreen, idealyPosOnScreen, 0.5f);
            //yPosOnScreen = idealyPosOnScreen;
        }

        if (isJumping)
        {
            JumpTimer -= _dt;
            if (speed != 300) {
                speed = LinearInterpolation.LerpEaseOut(speed, 300, 0.3f);
            }
            Instance.yPos -= _dt * speed;

            if (JumpTimer < 0)
            {
                isJumping = false;
                JumpTimer = 0;
                speed = 0;
            }
        } else if (isStalling) {
            JumpTimer -= _dt;
            if (speed != 0) {
                speed = LinearInterpolation.Lerp(speed, 0, 0.2f);
            }

            if (JumpTimer < 0)
            {
                isStalling = false;
                JumpTimer = 0;
                speed = 0;
                isMoving = true;
                isMidair = true;
            }
        }
        else if (!isMoving) {
            Instance.yPos -= 0;
        }
        else {
            idealyPosOnScreen = 600;
            if (speed != 250) {
                speed = LinearInterpolation.Lerp(speed, 250, 0.5f);
            }
            Instance.yPos += _dt * speed;
        }

        if (ControlScheme == 1) {
            if (values[0] > 0) {
                // Left
                if (PlayerEntity.Create().GetPosX() > 0)
                    PlayerEntity.Create().SetPosX((int) (PlayerEntity.Create().GetPosX() - (Math.abs(values[0]) * 200 * _dt)));
                else
                    PlayerEntity.Create().SetPosX(0);

            } else if (values[0] < 0) {
                //Right
                if (PlayerEntity.Create().GetPosX() < ScreenWidth)
                    PlayerEntity.Create().SetPosX((int) (PlayerEntity.Create().GetPosX() + (Math.abs(values[0]) * 200 * _dt)));
                else
                    PlayerEntity.Create().SetPosX(ScreenWidth);
            }
        }

        iTotalScore = (yPos/10) + iEnemyKillScore;

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
        spritesheet.Render(_canvas, xPos, yPosOnScreen + 50);
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

    public ENTITY_TYPE GetEntityType(){

        return ENTITY_TYPE.ENT_PLAYER;
    }

    public static PlayerEntity Create(){
        if (Instance == null) {
            Instance = new PlayerEntity();
            EntityManager.Instance.AddEntity(Instance, ENTITY_TYPE.ENT_PLAYER);
        }
        return Instance;
    }

    public void SetToJump(){
        if (!isMidair) {
            isJumping = true;
            speed = 150;
            JumpTimer = 0.6f;
            isMidair = true;
            idealyPosOnScreen = 550;
        }
    }

    public void SetStall() {
        isStalling = true;
        speed = 300;
        JumpTimer = 0.2f;
        isMoving = false;
    }

    public void Reset()
    {
        isInit = false;
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Many sensors return 3 values, one for each axis
        // Do something with this sensor value
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            values = sensorEvent.values;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do something here if sensor accuracy changes
    }

    public void Destroy(){
        Instance = null;
    }
}


