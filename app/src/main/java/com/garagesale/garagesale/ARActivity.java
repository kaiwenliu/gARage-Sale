package com.garagesale.garagesale;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class ARActivity extends AppCompatActivity {
    private static final String TAG = ARActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ViewRenderable productName;

    private int frameWidth;
    private int frameHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ux);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        ViewRenderable.builder()
                .setView(this, R.layout.productname)
                .build()
                .thenAccept(renderable -> productName = renderable);
        TextView prod = (TextView) productName.getView();
        prod.setText("epicstyle");
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        frameWidth = width;
        frameHeight = height;
    }

    private Bitmap takePicture() throws IOException {
        int pixelData[] = new int[frameWidth * frameHeight];

        // Read the pixels from the current GL frame.
        IntBuffer buf = IntBuffer.wrap(pixelData);
        buf.position(0);
        GLES20.glReadPixels(0, 0, frameWidth, frameHeight,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf);

        // Convert the pixel data from RGBA to what Android wants, ARGB.
        int bitmapData[] = new int[pixelData.length];
        for (int i = 0; i < frameHeight; i++) {
            for (int j = 0; j < frameWidth; j++) {
                int p = pixelData[i * frameWidth + j];
                int b = (p & 0x00ff0000) >> 16;
                int r = (p & 0x000000ff) << 16;
                int ga = p & 0xff00ff00;
                bitmapData[(frameHeight - i - 1) * frameWidth + j] = ga | r | b;
            }
        }
        // Create a bitmap.
        Bitmap bmp = Bitmap.createBitmap(bitmapData,
                frameWidth, frameHeight, Bitmap.Config.ARGB_8888);

        return bmp;
    }
}
