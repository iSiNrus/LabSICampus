package ru.myitschool.lab23;

import androidx.annotation.NonNull;

public interface TaskListChangeListener {

    void updateFocusedItem(@NonNull String receivedTaskContents);

    void updateTotalNumber(long totalNumber);
}
