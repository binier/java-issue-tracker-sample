package com.models;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.database.Database;
import com.google.gson.Gson;
import com.json.ToJsonStr;
import com.json.ToJsonStrWrapper;

public class CommentModel implements ToJsonStr {

    private Long id;
    private String text;
    private Date createdDate;
    private long issueId;

    /**
     * Insert CommentModel into database
     *
     * @param  comment  Issue model object
     * @return database insert statement
     *
     * @throws SQLException
     * @throws IOException
     */
    public static long insert(CommentModel comment) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "INSERT INTO comment (text, issueID) VALUES (?, ?);",
                Statement.RETURN_GENERATED_KEYS
        );

        preparedStatement.setString(1, comment.getText());
        preparedStatement.setLong(2, comment.getIssueId());

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("creating comment failed, no rows affected");
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("creating comment failed, no ID obtained.");
            }
        }
    }

    /**
     * comment table find by isueId row and return.
     *
     * @param issueId int
     *
     * @throws SQLException
     * @throws IOException
     */
    public static ToJsonStrWrapper<List<CommentModel>> getByIssueId(long issueId) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "SELECT * FROM comment WHERE issueId=?;"
        );
        preparedStatement.setLong(1, issueId);
        preparedStatement.execute();

        List<CommentModel> list = new ArrayList<>();
        CommentModel comment;

        while ((comment = CommentModel.fromResultSet(preparedStatement.getResultSet())) != null) {
            list.add(comment);
        }

        return new ToJsonStrWrapper(list);
    }

    /**
     * comment table find by id row and return.
     *
     * @param id int
     *
     * @throws SQLException
     * @throws IOException
     */
    public static CommentModel getById(long id) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "SELECT * FROM comment WHERE id=?;"
        );
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        return CommentModel.fromResultSet(preparedStatement.getResultSet());
    }

    /**
     * comment table find by id row and update.
     *
     * @param id int
     * @param newContent CommentModel
     *
     * @throws SQLException
     * @throws IOException
     */
    public static CommentModel update(long id, CommentModel newContent) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "UPDATE comment SET text=?, issueId=? WHERE id=?"
        );

        preparedStatement.setString(1, newContent.getText());
        preparedStatement.setLong(2, newContent.getIssueId());
        preparedStatement.setLong(3, id);

        preparedStatement.execute();

        return CommentModel.getById(id);
    }

    /**
     * comment table find by id row and delete.
     *
     * @param id int
     *
     * @throws SQLException
     * @throws IOException
     */
    public static void delete(long id) throws SQLException, IOException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(
                "DELETE * FROM comment WHERE id=?;"
        );
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
    }

    static CommentModel fromResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;
        CommentModel comment = new CommentModel(
                rs.getString("text"),
                rs.getLong("issueId")
        );

        comment.id = rs.getLong("id");
        comment.createdDate = rs.getDate("createdDate");

        return comment;
    }

    /**
     * Parameter constructor.
     * @param text
     * @param issueId
     */
    public CommentModel(String text, long issueId) {
        this.text = text;
        this.issueId = issueId;
    }

    public String getText() {
        return text;
    }

    /**
     * Registers text in a CommentModel
     *
     * @param title  the String to display.
     */
    public void setText(String title) {
        this.text = text;
    }

    /**
     * Return CommentModel issueid
     * type is int
     */
    public long getIssueId() {
        return issueId;
    }

    /**
     * Registers issueid in a CommentModel
     *
     * @param issueId  the int to display.
     */
    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    /**
     * Return CommentModel createdDate
     * type is Date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    public CommentModel save() throws SQLException, IOException {
        if (this.id == null) {
            this.id = CommentModel.insert(this);
        } else {
            CommentModel.update(this.id, this);
        }
        return this;
    }

    @Override
    public String toJsonStr() {
        return new Gson().toJson(this);
    }
}
