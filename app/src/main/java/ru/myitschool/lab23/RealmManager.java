package ru.myitschool.lab23;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import kotlin.NotImplementedError;

public class RealmManager {

    RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME).schemaVersion(0).allowWritesOnUiThread(true).deleteRealmIfMigrationNeeded().build();
    Realm realm = Realm.getInstance(realmConfiguration);

    public void insertTask(@NonNull Task task) {
        Number number = realm.where(Task.class).max("taskId");
        final int nextId;

        if (number == null) {
            nextId = 0;
        } else {
            nextId = number.intValue() + 1;
        }

        task.setTaskId(nextId);

        realm.beginTransaction();
        realm.copyToRealm(task);
        realm.commitTransaction();
    }

    public @Nullable List<Task> getData() {
        return realm.where(Task.class).findAll();
    }

    public long getNumberOfTasks() {
        List<Task> res = getData();
        if(res == null) return 0;
        else return res.size();
    }

    public void deleteTask(int Task_id) {
        final Task model = realm.where(Task.class).equalTo("taskId", Task_id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                if (model != null) {
                    model.deleteFromRealm();
                }
            }
        });
    }
}
