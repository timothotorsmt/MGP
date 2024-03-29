package com.sdm.mgpica;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;
import android.widget.CompoundButton;
import android.widget.Switch;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public final static String SHARED_PREF_ID = "com.sdm.mgpica.sharedpreferences";

    // Game stuff
    private boolean isPaused = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID, 0);

        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new Settings());
        StateManager.Instance.AddState(new Losescreen());

        GameSystem.Instance.SaveEditBegin();
        GameSystem.Instance.SaveEditEnd();
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    public void SaveEditBegin(){
        if (editor != null)
            return;

        editor = sharedPref.edit();
    }

    public void SaveEditEnd(){
        if (editor == null)
            return;

        editor.commit();
        editor = null;
    }

    public void SetIntInSave(String _key, int _value)
    {
        if (editor == null)
            return;

        editor.putInt(_key, _value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key, 0);
    }

    public void SetStringInSave(String _key, String m_text) {
        if (editor == null)
            return;

        editor.putString(_key, m_text);
    }

    public String GetStringFromSave(String _key)
    {
        return sharedPref.getString(_key, "");
    }
}
