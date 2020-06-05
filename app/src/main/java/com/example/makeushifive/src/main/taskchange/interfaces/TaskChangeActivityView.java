package com.example.makeushifive.src.main.taskchange.interfaces;

import com.example.makeushifive.src.main.taskchange.models.TaskChangeResponse;

public interface TaskChangeActivityView {
    void patchTaskChangeSuccess();
    void patchTaskChangeFail();

    void getTaskSuccess(TaskChangeResponse.Result result);
    void getTaskFail();

    void postTaskRepeatSuccess();
    void postTaskRepeatFail();

}
