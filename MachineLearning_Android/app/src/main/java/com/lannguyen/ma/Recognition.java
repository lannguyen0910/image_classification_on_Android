package com.lannguyen.ma;

import androidx.annotation.NonNull;

public class Recognition {
    private final String label;
    private final float confidence;

    public final String getLabel() {
        return this.label;
    }
    public final float getConfidence(){return this.confidence;}

    public Recognition(String label, float confidence) {
        this.label = label;
        this.confidence = confidence;
    }

    @NonNull
    @Override
    public String toString() {
        return label + " " + String.format("(%.1f%%) ", confidence * 100.0f);
    }
}
