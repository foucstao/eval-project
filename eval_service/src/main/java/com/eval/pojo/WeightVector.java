package com.eval.pojo;

import lombok.Data;

@Data
public class WeightVector {
    private double[][] importances;
    private double[][] eigValue;
    private double[][] eigVector;
    private double fmax;
    private int r_;
    private int c_;
    private double[] weight;
    private String description;
}
