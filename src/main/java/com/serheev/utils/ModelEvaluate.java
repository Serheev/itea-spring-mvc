package com.serheev.utils;

import com.serheev.model.Model;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public class ModelEvaluate {
    public static Model evaluateModel(String model) {
        return Arrays.stream(Model.values())
                .map(Enum::toString)
                .collect(toList())
                .contains(model)
                ? Model.valueOf(model)
                : Model.UNKNOWN;
    }
}
