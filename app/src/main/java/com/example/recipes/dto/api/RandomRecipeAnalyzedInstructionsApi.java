package com.example.recipes.dto.api;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomRecipeAnalyzedInstructionsApi {
    @SerializedName("steps")
    List<RandomRecipeAnalyzedInstructionsStepApi> steps;

    public String getInstruction() {
        StringBuilder instruction = new StringBuilder("");
        for (RandomRecipeAnalyzedInstructionsStepApi step : this.steps) {
            instruction.append(" * " + step.step + "\n");
        }
        return instruction.toString();
    }
}
