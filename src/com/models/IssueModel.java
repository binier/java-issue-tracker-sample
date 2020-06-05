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
    private int severity;
    private String status;
    private Date createdDate;
    private Date statusChangeDate;

    public static void insert(IssueModel issue) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "INSERT INTO issues (title, description, severity, status, createdDate) VALUES (?, ?, ?, ?, ?);"
        );

        preparedStatement.setString(1, issue.getTitle());
        preparedStatement.setString(2, issue.getDescription());
        preparedStatement.setInt(3, issue.getSeverity());
        preparedStatement.setString(4, issue.getStatus());
        preparedStatement.setDate(5, (java.sql.Date) issue.getCreatedDate());

        preparedStatement.execute();
    }

    public static void readByTitle(String issueTitle) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "SELECT * FROM issues WHERE title=?;"
        );
        preparedStatement.setString(1,issueTitle);
        preparedStatement.execute();
    }

    public static void update(IssueModel newContent, IssueModel oldContent) throws SQLException, IOException {
        DatabaseMetaData md = Database.getInstance().getConnection().getMetaData();
        ResultSet rs = md.getColumns(null, null, "issues", "title");
        if(rs.next()){
            insert(newContent);
            return;
        }

        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                String.format(
                        "UPDATE issues SET title=?, description=?, severity=?, status=?, statusChangeDate=? WHERE title={0}",
                        oldContent.getTitle()
                )
        );

        java.sql.Date statusChangeDate = (java.sql.Date) new java.util.Date(System.currentTimeMillis());

        preparedStatement.setString(1, newContent.getTitle());
        preparedStatement.setString(2, newContent.getDescription());
        preparedStatement.setInt(3, newContent.getSeverity());
        preparedStatement.setString(4, newContent.getStatus());
        preparedStatement.setDate(5, statusChangeDate);

        preparedStatement.execute();
    }

    public static void delete(String title) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "DELETE * FROM issues WHERE title=?;"
        );
        preparedStatement.setString(1,title);
        preparedStatement.execute();
    }

    public IssueModel(String title, String description, int severity, String status) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;

        this.createdDate = new Date(System.currentTimeMillis());
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date date) {
        this.createdDate = date;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }
}
