package com.song.makeushifive.src.main.taskchange.interfaces;

import com.song.makeushifive.src.main.taskchange.models.TaskChangeResponse;

public interface TaskChangeActivityView {
    void patchTaskChangeSuccess();
    void patchTaskChangeFail();

    void getTaskSuccess(TaskChangeResponse.Result result);
    void getTaskFail();

    void postTaskRepeatSuccess();
    void postTaskRepeatFail();

}
