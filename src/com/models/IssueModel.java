package com.models;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.database.Database;

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

    public void insert(IssueModel issue) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "INSERT INTO issues (title, description, date, severity, status, statusChangeDate) VALUES (?, ?, ?, ?, ?, ?);"
        );
        preparedStatement.setString(1, issue.getTitle());
        preparedStatement.setString(2, issue.getDescription());
        preparedStatement.setDate(3, (java.sql.Date) issue.getDate());
        preparedStatement.setInt(4, issue.getSeverity());
        preparedStatement.setString(5, issue.getStatus());
        preparedStatement.setDate(6, (java.sql.Date) issue.getDate());

        preparedStatement.execute();
    }

    public void readByTitle(String issueTitle) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "SELECT * FROM issues WHERE title=?;"
        );
        preparedStatement.setString(1,issueTitle);
        preparedStatement.execute();
    }

    public void update(IssueModel newContent, IssueModel oldContent) throws SQLException, IOException {
        DatabaseMetaData md = Database.getInstance().getConnection().getMetaData();
        ResultSet rs = md.getColumns(null, null, "issues", "title");
        if(rs.next()){
            insert(newContent);
            return;
        }

        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                String.format(
                        "UPDATE issues SET title=?, description=?, date=?, severity=?, status=? WHERE title={0}",
                        oldContent.getTitle()
                )
        );

        java.sql.Date statusChangeDate = (java.sql.Date) new java.util.Date(System.currentTimeMillis());

        preparedStatement.setString(1, newContent.getTitle());
        preparedStatement.setString(2, newContent.getDescription());
        preparedStatement.setDate(3, (java.sql.Date) newContent.getDate());
        preparedStatement.setInt(4, newContent.getSeverity());
        preparedStatement.setString(5, newContent.getStatus());
        preparedStatement.setDate(5, statusChangeDate);

        preparedStatement.execute();
    }

    public void delete(String title) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "DELETE * FROM issues WHERE title=?;"
        );
        preparedStatement.setString(1,title);
        preparedStatement.execute();
    }

}
