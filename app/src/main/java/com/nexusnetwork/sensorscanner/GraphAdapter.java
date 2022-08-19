package com.nexusnetwork.sensorscanner;

import com.robinhood.spark.SparkAdapter;

public class GraphAdapter extends SparkAdapter {
    private float[] yData;
    int counter;

    public GraphAdapter() {
        yData = new float[50];
        this.yData = yData;
        counter = 0;
        for (int x = 0; x < 50; x++) {
            yData[x] = 0;
        }
    }

    public void addData(int data) {
        if (counter < 50) {
            yData[counter] = (float) data;
            counter++;
        }
        else {
            float num = (float) data;
            float numTwo;
            for (int x = 49; x <= 0; x--) {
                numTwo = yData[x];
                yData[x] = num;
                num = numTwo;
            }
        }
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}
