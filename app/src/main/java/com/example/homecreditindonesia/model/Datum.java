
package com.example.homecreditindonesia.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("section_title")
    @Expose
    private String sectionTitle;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Datum{" +
                "section='" + section + '\'' +
                ", sectionTitle='" + sectionTitle + '\'' +
                ", items=" + items +
                '}';
    }
}
