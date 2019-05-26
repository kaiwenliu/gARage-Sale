package com.garagesale.garagesale;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ARActivity extends AppCompatActivity {
    private static final String TAG = ARActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ArSceneView arSceneView;
    private ViewRenderable productName;

    private int frameWidth;
    private int frameHeight;
    private boolean capturePicture;
    private boolean hasFinishedLoading;
    private GestureDetector gestureDetector;
    private boolean hasPlacedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ux);
        hasPlacedName = false;
        arSceneView = new ArSceneView(this);
        gestureDetector =
                new GestureDetector(
                        this,
                        new GestureDetector.SimpleOnGestureListener() {
                            @Override
                            public boolean onSingleTapUp(MotionEvent e) {
                                onSingleTap(e);
                                return true;
                            }

                            @Override
                            public boolean onDown(MotionEvent e) {
                                return true;
                            }
                        });

        // Set a touch listener on the Scene to listen for taps.
        arSceneView
                .getScene()
                .setOnTouchListener(
                        (HitTestResult hitTestResult, MotionEvent event) -> {
                            // If the solar system hasn't been placed yet, detect a tap and then check to see if
                            // the tap occurred on an ARCore plane to place the solar system.
                            if (!hasPlacedName) {
                                return gestureDetector.onTouchEvent(event);
                            }

                            // Otherwise return false so that the touch event can propagate to the scene.
                            return false;
                        });
        CompletableFuture<ViewRenderable> productNameFuture = ViewRenderable.builder().setView(this, R.layout.productname).build();
        productNameFuture.handle(
                (notUsed, throwable) -> {
                    try {
                        productName = productNameFuture.get();

                        // Everything finished loading successfully.
                        hasFinishedLoading = true;
                    } catch (InterruptedException | ExecutionException ex) {
                        ex.printStackTrace();
                    }

                    return null;
                });
        Node name = new Node();
        name.setRenderable(productName);
        name.setLocalPosition(new Vector3(0.0f, 0.25f, 0.0f));

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);




    }

    private void onSingleTap(MotionEvent tap) {
        if (!hasFinishedLoading) {
            // We can't do anything yet.
            return;
        }

        Frame frame = arSceneView.getArFrame();
        if (frame != null) {
            if (!hasPlacedName && tryPlaceName(tap, frame)) {
                hasPlacedName = true;
            }
        }
    }

    private boolean tryPlaceName(MotionEvent tap, Frame frame) {
        if (tap != null && frame.getCamera().getTrackingState() == TrackingState.TRACKING) {
            for (HitResult hit : frame.hitTest(tap)) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Plane && ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                    // Create the Anchor.
                    Anchor anchor = hit.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arSceneView.getScene());
                    Node nameNode = createName();
                    anchorNode.addChild(nameNode);
                    return true;
                }
            }
        }
        return false;
    }

    private Node createName(){
        Node base = new Node();
        base.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));
        View prod = productName.getView();
        TextView nameView = prod.findViewById(R.id.productName);
        //nameView.setText("epicstyle");
        return base;
    }
}
