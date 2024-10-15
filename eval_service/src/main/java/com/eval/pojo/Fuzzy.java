package com.eval.pojo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Fuzzy implements Serializable {
    private static final long serialVersionUID = 1L;
    Double[] a;
    Double[][] b;
}
