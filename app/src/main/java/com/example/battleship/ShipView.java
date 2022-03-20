package com.example.battleship;

import android.widget.ImageView;

public class ShipView {
    private ImageView shipImage;
    private Ship ship;
    private boolean isSelected = false;

    public ShipView(ImageView shipImage, Ship ship) {
        this.shipImage = shipImage;
        this.ship = ship;
    }

    public void setShipImage(ImageView shipImage) {
        this.shipImage = shipImage;
    }
    //set selected or not
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ImageView getShipImage() {
        return shipImage;
    }

    public Ship getShip() {
        return ship;
    }
    //get selected or not
    public boolean isSelected() {
        return isSelected;
    }
}
