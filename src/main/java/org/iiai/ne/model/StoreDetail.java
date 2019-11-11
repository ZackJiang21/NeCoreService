package org.iiai.ne.model;

import java.util.List;

public class StoreDetail extends Store {
    private int startAm;

    private int endAm;

    private int startPm;

    private int endPm;

    private List<StoreActivity> activities;

    public StoreDetail() {
        super();
    }

    public int getStartAm() {
        return startAm;
    }

    public void setStartAm(int startAm) {
        this.startAm = startAm;
    }

    public int getEndAm() {
        return endAm;
    }

    public void setEndAm(int endAm) {
        this.endAm = endAm;
    }

    public int getStartPm() {
        return startPm;
    }

    public void setStartPm(int startPm) {
        this.startPm = startPm;
    }

    public int getEndPm() {
        return endPm;
    }

    public void setEndPm(int endPm) {
        this.endPm = endPm;
    }

    public List<StoreActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<StoreActivity> activities) {
        this.activities = activities;
    }
}
