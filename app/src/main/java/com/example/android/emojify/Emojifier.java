package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class Emojifier {

    public static void detectFaces(Context context, Bitmap bitmapImage) {
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        Frame frame = new Frame.Builder().setBitmap(bitmapImage).build();

        SparseArray<Face> faces = detector.detect(frame);

        if (faces.size() == 0) {
            Toast.makeText(context, "No face idenetified !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, faces.size() + " faces detected !", Toast.LENGTH_SHORT).show();

            for (int i = 0; i < faces.size(); i++) {
                Face face = faces.valueAt(i);
                getClassifications(face);
            }
        }

        detector.release();
    }

    private static void getClassifications(Face face) {
        Log.d("Nitin", "smiling Prob:" + face.getIsSmilingProbability());
        Log.d("Nitin", "left eye open Prob:" + face.getIsLeftEyeOpenProbability());
        Log.d("Nitin", "right eye open Prob:" + face.getIsRightEyeOpenProbability());
        Log.d("Nitin", "***********************************************************\n");
    }
}
