package com.lannguyen.ma;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static com.lannguyen.ma.ModelConfig.CLASSIFICATION_THRESHOLD;
import static com.lannguyen.ma.ModelConfig.MAX_CLASSIFICATION_RESULTS;

public class Classifier {
    private final Interpreter interpreter;

    private Classifier(Interpreter interpreter){
        this.interpreter = interpreter;
    }

    public static Classifier classifier(AssetManager assetManager, String modelPath) throws IOException {
        ByteBuffer byteBuffer = loadModelFile(assetManager, modelPath);
        Interpreter interpreter = new Interpreter(byteBuffer);
        return new Classifier(interpreter);
    }

    private static ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws  IOException{
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(ModelConfig.MODEL_INPUT_SIZE);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] pixels = new int[ModelConfig.INPUT_IMG_SIZE_WIDTH * ModelConfig.INPUT_IMG_SIZE_HEIGHT];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int pixel : pixels) {  // Convert Pixel
            /**
             * https://github.com/nex3z/tflite-mnist-android/blob/master/android/app/src/main/java/com/nex3z/tflite/mnist/classifier/Classifier.kt
             **/
            float pixelValue = ((float)255 - ((float)(pixel >> 16 & 255) * 0.299F + (float)(pixel >> 8 & 255) * 0.587F + (float)(pixel & 255) * 0.114F)) / 255.0F;
            byteBuffer.putFloat(pixelValue);
        }
        return byteBuffer;
    }

    private List<Recognition> getSortedResult(float[][] resultsArray) {
        PriorityQueue<Recognition> sortedResults = new PriorityQueue<>(
                MAX_CLASSIFICATION_RESULTS,
                (lhs, rhs) -> Float.compare(rhs.getConfidence(), lhs.getConfidence())
        );

        for (int i = 0; i < ModelConfig.OUTPUT_LABELS.size(); ++i) {
            float confidence = resultsArray[0][i];
            if (confidence > CLASSIFICATION_THRESHOLD) {
                ModelConfig.OUTPUT_LABELS.size();
                sortedResults.add(new Recognition(ModelConfig.OUTPUT_LABELS.get(i), confidence));
            }
        }

        return new ArrayList<>(sortedResults);
    }

    public List<Recognition> recognizeImage(Bitmap bitmap) {
        ByteBuffer byteBuffer = convertBitmapToByteBuffer(bitmap);
        float[][] result = new float[1][ModelConfig.OUTPUT_LABELS.size()];
        interpreter.run(byteBuffer, result);
        return getSortedResult(result);
    }
}
