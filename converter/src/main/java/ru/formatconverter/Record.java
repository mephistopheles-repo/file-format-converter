package ru.formatconverter;

/**
 * Created with IntelliJ IDEA.
 * Date: 29.12.13
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
public class Record {
    private String name;
    private int positionX;
    private int positionY;
    private int width;
    private int height;
    private int offsetX;
    private int offsetY;
    private int originalWidth;
    private int originalHeight;
    private boolean isRotated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(int originalWidth) {
        this.originalWidth = originalWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(int originalHeight) {
        this.originalHeight = originalHeight;
    }

    public boolean isRotated() {
        return isRotated;
    }

    public void setRotated(boolean rotated) {
        isRotated = rotated;
    }
}
