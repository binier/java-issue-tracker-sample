package com.test.models;

import com.models.CommentModel;
import com.models.IssueModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CommentModelTest {

    @Test
    void insert() throws IOException, SQLException {
        CommentModel commentModel1 = new CommentModel("comment",1);
        CommentModel commentModel2 = new CommentModel("comment2",1);
        CommentModel commentModel3 = new CommentModel("comment3",1);
        CommentModel commentModel4 = new CommentModel("comment test",3);
        CommentModel commentModel5 = new CommentModel("comment test2",2);

        CommentModel.insert(commentModel1);
        CommentModel.insert(commentModel2);
        CommentModel.insert(commentModel3);
        CommentModel.insert(commentModel4);
        CommentModel.insert(commentModel5);
    }

    @Test
    void getByIssueId() throws IOException, SQLException {
        CommentModel.getByIssueId(1);
    }

    @Test
    void getById() throws IOException, SQLException {
        CommentModel.getById(1);
    }

    @Test
    void update() throws IOException, SQLException {
        CommentModel commentModel = new CommentModel("comment updated",1);
        CommentModel.update(1,commentModel);
    }

    @Test
    void delete() throws IOException, SQLException {
        CommentModel.delete(1);
    }

    @Test
    void getText() {
        CommentModel commentModel = new CommentModel("getcom1",2);
        assertEquals(commentModel.getText(),"getcom1");
    }

    @Test
    void setText() {
        CommentModel commentModel = new CommentModel("getcom1",2);
        commentModel.setText("set1");
        assertEquals(commentModel.getText(),"set1");
    }

    @Test
    void getIssueId() {
        CommentModel commentModel = new CommentModel("getcom132",4);
        assertEquals(commentModel.getIssueId(),4);
    }

    @Test
    void setIssueId() {
        CommentModel commentModel = new CommentModel("getco32m132",4);
        commentModel.setIssueId(1);
        assertEquals(commentModel.getIssueId(),1);
    }

    @Test
    void save() throws IOException, SQLException {
        CommentModel commentModel = new CommentModel("save function used",2);
        commentModel.save();
    }
}