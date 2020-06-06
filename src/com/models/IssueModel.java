package com.models;

import java.io.IOException;
import java.sql.*;
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

    /**
     * Insert issue model into database
     *
     * @param  issue  Issue model object
     * @return database insert statement
     *
     * @throws SQLException
     * @throws IOException
     */

    public static long insert(IssueModel issue) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "INSERT INTO issue (title, description, severity, status) VALUES (?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        preparedStatement.setString(1, issue.getTitle());
        preparedStatement.setString(2, issue.getDescription());
        preparedStatement.setInt(3, issue.getSeverity());
        preparedStatement.setString(4, issue.getStatus());

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("creating issue failed, no rows affected");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("creating issue failed, no ID obtained.");
            }
        }
    }

    /**
     * find and return issue record by id
     *
     * @param id int
     *
     * @throws SQLException
     * @throws IOException
     */
    public static IssueModel getById(long id) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "SELECT * FROM issue WHERE id=?;"
        );
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        return IssueModel.fromResultSet(preparedStatement.getResultSet());
    }

    /**
     * find and return issue record by title
     *
     * @param issueTitle string
     *
     * @throws SQLException
     * @throws IOException
     */
    public static void readByTitle(String issueTitle) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "SELECT * FROM issue WHERE title=?;"
        );
        preparedStatement.setString(1,issueTitle);
        preparedStatement.execute();
    }

    /**
     * issue table find by id row and delete.
     *
     * @param id int
     * @param newContent issueModel
     *
     * @throws SQLException
     * @throws IOException
     */
    public static IssueModel update(long id, IssueModel newContent) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "UPDATE issue SET title=?, description=?, severity=?, status=? WHERE id=?"
        );

        preparedStatement.setString(1, newContent.getTitle());
        preparedStatement.setString(2, newContent.getDescription());
        preparedStatement.setInt(3, newContent.getSeverity());
        preparedStatement.setString(4, newContent.getStatus());
        preparedStatement.setLong(5, id);

        preparedStatement.execute();

        return IssueModel.getById(id);
    }

    /**
     * issue table find by id row and delete.
     *
     * @param id int
     *
     * @throws SQLException
     * @throws IOException
     */
    public static void delete(long id) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "DELETE * FROM issue WHERE id=?;"
        );
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
    }

    /**
     * return issue.
     *
     * @param rs ResultSet object
     */
    static IssueModel fromResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;
        IssueModel issue = new IssueModel(
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("severity"),
                rs.getString("status")
        );

        issue.id = rs.getLong("id");
        issue.createdDate = rs.getDate("createdDate");
        issue.statusChangeDate = rs.getDate("statusChangeDate");

        return issue;
    }

    /**
     * Parameter constructor.
     * @param title
     * @param description
     * @param severity
     * @param status
     */

    public IssueModel(String title, String description, int severity, String status) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
    }

    /**
     * Return issueModel title
     * type is String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Registers title in a IssueModel
     *
     * @param title  the string to display.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return issueModel description
     * type is String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Registers description in a issue model
     *
     * @param description  the string to display.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return issueModel severity
     * type is int
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * Registers severity in a issue model
     *
     * @param severity  the int to display.
     */
    public void setSeverity(int severity) {
        this.severity = severity;
    }

    /**
     * Return issueModel status
     * type is String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Registers status in a issue model
     *
     * @param status  the String to display.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Return issueModel createdDATE
     * type is Date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Return issueModel statusChangeDate
     * type is Date
     */
    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    /**
     * Saves the current record.
     */
    public IssueModel save() throws SQLException, IOException {
        if (this.id == null) {
            this.id = IssueModel.insert(this);
        } else {
            IssueModel.update(this.id, this);
        }
        return this;
    }

    @Override
    public String toJsonStr() {
        return new Gson().toJson(this);
    }
}
