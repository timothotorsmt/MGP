package com.sdm.mgpica;

// Written by Timothy

public interface LinearInterpolation {
    static float Flip(float x)
    {
        return 1 - x;
    }

    static float Lerp(float start_value, float end_value, float pct)
    {
        return (start_value + (end_value - start_value) * pct);
    }

    static float Square(float x) {
        return x * x;
    }

    static float EaseIn(float t)
    {
        return t * t * t;
    }

    static float EaseOut(float t)
    {
        return Flip(Square(Flip(t)));
    }

    static float LerpEaseOut(float a, float b, float t)
    {
        return Lerp(a, b, Flip(Square(Flip(t))));
    }

    public static float EaseInOut(float t)
    {
        return Lerp(EaseIn(t), EaseOut(t), t);
    }
}
