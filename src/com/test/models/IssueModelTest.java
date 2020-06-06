package com.test.models;

import com.models.IssueModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class IssueModelTest {

    @Test
    void insert() throws IOException, SQLException {
        IssueModel issueModel = new IssueModel("titl1","description1",2,"status1");
        issueModel.insert(issueModel);
    }

    @Test
    void getById() throws IOException, SQLException {
        IssueModel.getById(1);
    }

    @Test
    void readByTitle() throws IOException, SQLException {
        IssueModel.readByTitle("title1");
    }

    @Test
    void update() throws IOException, SQLException {
        IssueModel issueModel = new IssueModel("titl1 updated","description1",2,"status1");
        IssueModel.update(1,issueModel);
    }

    @Test
    void delete() throws IOException, SQLException {
        IssueModel issueModel = new IssueModel("titl1 updated","description1",2,"status1");
        IssueModel.delete(1);
    }

    @Test
    void getTitle() {
        IssueModel issueModel = new IssueModel("same","description1",2,"status1");
        assertEquals(issueModel.getTitle(),"same");
    }

    @Test
    void setTitle() {
        IssueModel issueModel = new IssueModel("same","description1",2,"status1");
        issueModel.setTitle("same2");
        assertEquals(issueModel.getTitle(),"same2");
    }

    @Test
    void getDescription() {
        IssueModel issueModel = new IssueModel("same","description1",2,"status1");
        assertEquals(issueModel.getDescription(),"description1");
    }

    @Test
    void setDescription() {
        IssueModel issueModel = new IssueModel("same","description1",2,"status1");
        issueModel.setDescription("desc2");
        assertEquals(issueModel.getDescription(),"desc2");
    }

    @Test
    void getSeverity() {
        IssueModel issueModel = new IssueModel("same","description1",2,"status1");
        assertEquals(issueModel.getSeverity(),2);
    }

    @Test
    void setSeverity() {
        IssueModel issueModel = new IssueModel("same","description1",2,"status1");
        issueModel.setSeverity(1);
        assertEquals(issueModel.getSeverity(),1);
    }

    @Test
    void getStatus() {
        IssueModel issueModel = new IssueModel("same","description1",2,"status1");
        assertEquals(issueModel.getStatus(),"status1");
    }

    @Test
    void setStatus() {
        IssueModel issueModel = new IssueModel("same","description1",2,"change1");
        issueModel.setStatus("change2");
        assertEquals(issueModel.getStatus(),"change2");
    }

    @Test
    void save() throws IOException, SQLException {
        IssueModel issueModel = new IssueModel("save2","save2",2,"status1");
        issueModel.save();
    }
}