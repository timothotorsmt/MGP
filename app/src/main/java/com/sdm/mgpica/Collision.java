package com.sdm.mgpica;

// Created by TanSiewLan2021

public class Collision {

    public static boolean SphereToSphere(float x1, float y1, float radius1, float x2, float y2, float radius2)
    {
        float xVec = x2 - x1;
        float yVec = y2 - y1;

        float distSquared = xVec * xVec + yVec * yVec;

        float rSquared = radius1 + radius2;
        rSquared *= rSquared;

        if (distSquared > rSquared)
            return false;

        return true;
    }

    // TODO: Actually try to see if it works
    public static boolean AABBtoAABB (float minx1, float miny1, float maxx1, float maxy1, float minx2, float miny2, float maxx2, float maxy2)
    {
        if (maxx1 < minx2 || minx1 > maxx2) return false;
        if (maxy1 < miny2 || miny1 > maxy2) return false;

        return true;
    }
}
