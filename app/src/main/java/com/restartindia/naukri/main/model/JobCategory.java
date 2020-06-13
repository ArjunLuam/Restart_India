package com.restartindia.naukri.main.model;

public class JobCategory {
    private String category;
    private int jobs;
    private int color;
    private int icon;

    public JobCategory(String category, int jobs, int color, int icon) {
        this.category = category;
        this.jobs = jobs;
        this.color = color;
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getJobs() {
        return jobs;
    }

    public void setJobs(int jobs) {
        this.jobs = jobs;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
