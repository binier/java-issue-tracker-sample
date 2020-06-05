package com.models;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.database.Database;
import com.google.gson.Gson;
import com.json.ToJsonStr;

public class IssueModel implements ToJsonStr {

    private Long id;
    private String title;
    private String description;
    private int severity;
    private String status;
    private Date createdDate;
    private Date statusChangeDate;

    public static long insert(IssueModel issue) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "INSERT INTO issues (title, description, severity, status, createdDate) VALUES (?, ?, ?, ?, ?);"
        );

        preparedStatement.setString(1, issue.getTitle());
        preparedStatement.setString(2, issue.getDescription());
        preparedStatement.setInt(3, issue.getSeverity());
        preparedStatement.setString(4, issue.getStatus());
        preparedStatement.setDate(5, (java.sql.Date) new Date(System.currentTimeMillis()));

        preparedStatement.execute();

        // TODO: return inserted `id`
        return 1;
    }

    public static IssueModel getById(int id) {
        throw new UnsupportedOperationException();
    }

    public static void readByTitle(String issueTitle) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "SELECT * FROM issues WHERE title=?;"
        );
        preparedStatement.setString(1,issueTitle);
        preparedStatement.execute();
    }

    public static IssueModel update(long id, IssueModel newContent) throws SQLException, IOException {
        DatabaseMetaData md = Database.getInstance().getConnection().getMetaData();
        ResultSet rs = md.getColumns(null, null, "issues", "title");
        if(rs.next()){
            newContent.id = insert(newContent);
            return newContent;
        }

        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                String.format(
                        "UPDATE issues SET title=?, description=?, severity=?, status=?, statusChangeDate=? WHERE id={0}",
                        id
                )
        );

        preparedStatement.setString(1, newContent.getTitle());
        preparedStatement.setString(2, newContent.getDescription());
        preparedStatement.setInt(3, newContent.getSeverity());
        preparedStatement.setString(4, newContent.getStatus());
        preparedStatement.setDate(5, (java.sql.Date) new Date(System.currentTimeMillis()));

        preparedStatement.execute();

        // TODO: return result from update query if possible to get all the fields.
        newContent.id = id;
        return newContent;
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

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public IssueModel save() throws SQLException, IOException {
        if (this.id == null) {
            this.id = IssueModel.insert(this);
        } else {
            IssueModel.update(this.id, this);
        }
        return this;
    }

    public String toJsonStr() {
        return new Gson().toJson(this);
    }
}
