package com.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.json.ToJsonStr;
import com.models.IssueModel;

public class IssueCommand implements Command {
    private String action;
    private JsonObject arg;

    public static IssueCommand fromJsonStr(String jsonStr) {
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(jsonStr, JsonObject.class);

        return new IssueCommand(obj.get("action").getAsString(), obj);
    }

    IssueCommand(String action, JsonObject arg) {
        this.action = action;
        this.arg = arg;
    }

    IssueModel argToIssueModel() {
        return new IssueModel(
                this.arg.get("title").getAsString(),
                this.arg.get("description").getAsString(),
                this.arg.get("severity").getAsInt(),
                this.arg.get("status").getAsString()
        );
    }

    @Override
    public ToJsonStr execute() throws IOException, SQLException, UnknownActionException {
        switch (this.action) {
            case "get":
                return IssueModel.getById(this.arg.get("id").getAsInt());
            case "add":
                return this.argToIssueModel().save();
            case "update":
                return IssueModel.update(
                        this.arg.get("id").getAsInt(),
                        this.argToIssueModel()
                );
            default:
                throw new UnknownActionException(this.action);
        }
    }
}
