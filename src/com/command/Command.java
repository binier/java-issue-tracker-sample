package com.command;

import com.json.ToJsonStr;

public interface Command {
    ToJsonStr execute() throws Exception;
}
