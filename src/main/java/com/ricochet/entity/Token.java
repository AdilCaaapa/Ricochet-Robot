package com.ricochet.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Token {
    Vector2 position;
    int row;
    int col;

    Image image;
    public enum Color{
        red,green,blue,yellow
    }
    Color color;

    public Token(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;

        this.position = new Vector2(col * 40 + 20,row * 40 + 20);


        switch (color){
            case red:
                image = new Image("redToken.png");
                break;
            case green:
                image = new Image("greenToken.png");
                break;
            case blue:
                image = new Image("blueToken.png");
                break;
            case yellow:
                image = new Image("yellowToken.png");
                break;
        }
    }

    public void setPosition(int row,int col){
        this.position = new Vector2(row * 40 + 20,col * 40 + 20);
        this.row = row;
        this.col = col;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void draw(GraphicsContext g){
        g.drawImage(image,position.x,position.y);
    }
}

