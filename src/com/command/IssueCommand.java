package com.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.json.ToJsonStr;
import com.models.IssueModel;

/**
 * <h2>Command for `Issue` table</h2>
 *
 * Deserializes command and provides a way to {@link IssueCommand#execute execute} it.
 */
public class IssueCommand implements Command {
    private String action;
    private JsonObject arg;

    public static IssueCommand fromJson(String jsonStr) {
        return IssueCommand.fromJson(
                new Gson().fromJson(jsonStr, JsonObject.class)
        );
    }

    public static IssueCommand fromJson(JsonObject jObj) {
        return new IssueCommand(jObj.get("action").getAsString(), jObj);
    }

    IssueCommand(String action, JsonObject arg) {
        this.action = action;
        this.arg = arg;
    }

    /**
     * build {@link IssueModel Issue} from {@link IssueCommand#arg IssueCommand.arg}.
     */
    IssueModel argToIssueModel() {
        return new IssueModel(
                this.arg.get("title").getAsString(),
                this.arg.get("description").getAsString(),
                this.arg.get("severity").getAsInt(),
                this.arg.get("status").getAsString()
        );
    }

    /**
     * <h2>Executes current command</h2>
     *
     * @return any object which implements {@link ToJsonStr ToJsonStr}
     * and therefore can be converted to Json string.
     * @throws IOException
     * @throws SQLException
     * @throws UnknownActionException if action was not recognized
     */
    @Override
    public ToJsonStr execute() throws IOException, SQLException, UnknownActionException {
        switch (this.action) {
            case "get":
                return IssueModel.getById(this.arg.get("id").getAsLong());
            case "add":
                return this.argToIssueModel().save();
            case "update":
                return IssueModel.update(
                        this.arg.get("id").getAsLong(),
                        this.argToIssueModel()
                );
            default:
                throw new UnknownActionException(this.action);
        }
    }
}
