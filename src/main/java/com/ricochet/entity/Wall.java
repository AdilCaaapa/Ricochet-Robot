package com.ricochet.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wall {

    boolean open;
    Vector2 pos;
    Image image;


    public Wall( Vector2 pos, Image image) {
        open = false;
        this.pos = pos;
        this.image = image;
    }

    boolean is_open()
    {
        return open;
    }
    void open_wall()
    {
        open = true;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    void draw(GraphicsContext g){
        if(open){
            g.drawImage(image,pos.x,pos.y);
        }
    }

    @Override
    public String toString() {
        return "Wall{" +
                "open=" + open +
                ", pos=" + pos +
                ", image=" + image +
                '}';
    }
}
