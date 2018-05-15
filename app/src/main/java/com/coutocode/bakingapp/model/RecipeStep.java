package com.coutocode.bakingapp.model;

public class RecipeStep {
    int id;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    RecipeStep(int id, String shortDescription, String description, String videoURL,
               String thumbnailURL){
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }
}
