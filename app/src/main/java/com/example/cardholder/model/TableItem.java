package com.example.cardholder.model;

import java.io.Serializable;

public class TableItem implements Serializable {

    private String title;
    private String type;

    public TableItem() {
        title = "";
        type = "";
    }

    //region getters / setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    //endregion
}
