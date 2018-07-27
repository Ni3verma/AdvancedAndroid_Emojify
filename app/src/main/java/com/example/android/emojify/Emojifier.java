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
    private static final double SMILING_PROB_THRESHOLD = .15;
    private static final double EYE_OPEN_PROB_THRESHOLD = .5;

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
                whichEmoji(face);
            }
        }

        detector.release();
    }

    private static void whichEmoji(Face face) {
        Log.d("Nitin", "smiling Prob:" + face.getIsSmilingProbability());
        Log.d("Nitin", "left eye open Prob:" + face.getIsLeftEyeOpenProbability());
        Log.d("Nitin", "right eye open Prob:" + face.getIsRightEyeOpenProbability());


        boolean smiling = face.getIsSmilingProbability() > SMILING_PROB_THRESHOLD;
        boolean leftEyeClosed = face.getIsLeftEyeOpenProbability() < EYE_OPEN_PROB_THRESHOLD;
        boolean rightEyeClosed = face.getIsRightEyeOpenProbability() < EYE_OPEN_PROB_THRESHOLD;

        Emoji emoji;
        if (smiling) {
            if (leftEyeClosed && !rightEyeClosed) {
                emoji = Emoji.LEFT_WINK;
            } else if (rightEyeClosed && !leftEyeClosed) {
                emoji = Emoji.RIGHT_WINK;
            } else if (leftEyeClosed) {
                emoji = Emoji.CLOSED_EYE_SMILE;
            } else {
                emoji = Emoji.SMILE;
            }
        } else {
            if (leftEyeClosed && !rightEyeClosed) {
                emoji = Emoji.LEFT_WINK_FROWN;
            } else if (rightEyeClosed && !leftEyeClosed) {
                emoji = Emoji.RIGHT_WINK_FROWN;
            } else if (leftEyeClosed) {
                emoji = Emoji.CLOSED_EYE_FROWN;
            } else {
                emoji = Emoji.FROWN;
            }
        }

        Log.d("Nitin", "closest match:" + emoji.name());
        Log.d("Nitin", "***********************************************************\n");
    }

    private enum Emoji {
        SMILE, FROWN, WINK, LEFT_WINK, RIGHT_WINK, LEFT_WINK_FROWN, RIGHT_WINK_FROWN, CLOSED_EYE_SMILE, CLOSED_EYE_FROWN
    }
}
