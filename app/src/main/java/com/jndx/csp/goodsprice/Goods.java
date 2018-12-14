package com.jndx.csp.goodsprice;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jszx on 2018/10/11.
 */

public class Goods implements Serializable
{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    private String name;
    private int pictureId;
    private double price;
    private Date day;
}
