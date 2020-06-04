package com.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueModel {

    private String title;
    private String description;
    private Date date;
    private int severity;
    private String status;
    private Date statusChangeDate;

    public IssueModel(String title, String description, int severity, String status) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;

        date = new Date(System.currentTimeMillis());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

}
