package com.app.tilo.timelogger.model;

public class CategoryElement {
    private String name;
    private boolean selected;
    private boolean isVisible;

    public CategoryElement(String name) {
        this.name = name;
        selected = false;
        isVisible = false;
    }

    public CategoryElement(String name, boolean isVisible) {
        this.name = name;
        selected = false;
        this.isVisible = isVisible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
