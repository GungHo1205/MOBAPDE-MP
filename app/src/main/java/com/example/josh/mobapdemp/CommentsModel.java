package com.example.josh.mobapdemp;

public class CommentsModel {
    private String commentUsername;
    private String commentDescription;
    private String id;
    private float crRating;

    public CommentsModel(String commentUsername, String commentDescription, float crRating, String id) {
        this.commentUsername = commentUsername;
        this.commentDescription = commentDescription;
        this.id = id;
        this.crRating = crRating;
    }
    public CommentsModel(){

    }

    public String getCommentUsername() {
        return commentUsername;
    }

    public void setCommentUsername(String commentUsername) {
        this.commentUsername = commentUsername;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getCrRating() {
        return crRating;
    }

    public void setCrRating(float crRating) {
        this.crRating = crRating;
    }
}
