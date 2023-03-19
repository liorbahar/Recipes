package com.example.recipes.models.api;


import com.example.recipes.models.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomRecipeApi {
    @SerializedName("title")
    String Title;
    @SerializedName("image")
    String Image;
    @SerializedName("analyzedInstructions")
    List<RandomRecipeAnalyzedInstructionsApi> AnalyzedInstructions;

    public String getFullInstructions(){
        StringBuilder instruction = new StringBuilder("");
        Integer numberCount = 1;
        for (RandomRecipeAnalyzedInstructionsApi step : this.AnalyzedInstructions) {
            instruction.append(numberCount + ") " + step.getInstruction() + "\n");
            numberCount += 1;
        }
        return instruction.toString();
    }

    public Recipe toRecipe(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String imageUrl = (this.Image != null) ? this.Image : "";
        return new Recipe("", this.Title, this.getFullInstructions(), userId, imageUrl);
    }

}
