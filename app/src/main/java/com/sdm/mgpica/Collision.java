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
    public static boolean AABBtoAABB (AABB a, AABB b)
    {
        if(a.minX < b.minX + b.width &&
                a.minX + a.width > b.minX &&
                a.minY < b.minY + b.height &&
                a.minY + a.height > b.minY)
        {
            return true;
        }
        return false;
    }
}

class AABB {
    public float minX = 5;
    public float minY = 5;
    public float width = 50;
    public float height = 50;
}
