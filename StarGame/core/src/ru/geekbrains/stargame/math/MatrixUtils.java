package ru.geekbrains.stargame.math;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class MatrixUtils {

    public MatrixUtils() {
    }

    public static void calcTransitionMatrix(Matrix4 mat, Rect src, Rect dest){
        float scaleX = dest.getWidth() / src.getWidth();
        float scaleY = dest.getHeight() / src.getHeight();
        mat.idt().translate(dest.pos.x, dest.pos.y,0f).scale(scaleX,scaleY,1f).translate(-src.pos.x, -src.pos.y,0f);
    }

    public static void calcTransitionMatrix(Matrix3 mat, Rect src, Rect dest){
        float scaleX = dest.getWidth() / src.getWidth();
        float scaleY = dest.getHeight() / src.getHeight();
        mat.idt().translate(dest.pos.x, dest.pos.y).scale(scaleX,scaleY).translate(-src.pos.x, -src.pos.y);
    }
}