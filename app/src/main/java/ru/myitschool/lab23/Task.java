package ru.myitschool.lab23;

import androidx.annotation.NonNull;

import io.realm.RealmObject;
import kotlin.NotImplementedError;

public class Task extends RealmObject {

    int taskId;
    String taskContents;

    public Task() {
    }

    public Task(@NonNull String taskContents) {
        // todo implement
        throw new NotImplementedError();
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public @NonNull String getTaskContents() {
        return taskContents;
    }

    public void setTaskContents(@NonNull String taskContents) {
        this.taskContents = taskContents;
    }
}
