package com.sdm.mgpica;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2021
// Modified by TanSzeTing2022
// Modified by Timothy Luk

public interface EntityBase
{
 	 //used for entities such as background
    enum ENTITY_TYPE{
        //ENT_PLAYER,
        //ENT_SMURF,
        //ENT_PAUSE,
        //ENT_TEXT,
        //ENT_NEXT,
        ENT_DEFAULT,
         ENT_SMURF,
         ENT_PLAYER,
         ENT_L_BUTTON,
         ENT_R_BUTTON,
         ENT_ACTION_BUTTON,
         ENT_PROJECTILE,
         ENT_ENEMY,
         ENT_PAUSE,
         ENT_TEXT,
         ENT_BLOCKS
    }

    boolean IsDone();
    void SetIsDone(boolean _isDone);

    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);

    boolean IsInit();

    int GetRenderLayer();
    void SetRenderLayer(int _newLayer);

	ENTITY_TYPE GetEntityType();
}
