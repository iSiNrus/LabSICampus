package ru.myitschool.lab23;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvTasks;
    EditText etTask;
    TextView tvTotalTasks;

    RealmManager realmManager;
    AdapterTask adapterTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());
        realmManager = new RealmManager();

        etTask = findViewById(R.id.new_task_contents);
        tvTotalTasks = findViewById(R.id.total_tasks);
        rvTasks = findViewById(R.id.vRV);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        updateRecycler();
        updateTotalTasks(realmManager.getNumberOfTasks());

        etTask.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                if (keyCode >= KeyEvent.KEYCODE_CALL) {
                    insert(etTask.getText().toString());
                    v.setText(getString(R.string.new_task));
                    v.clearFocus();
                    v.setFocusableInTouchMode(true);
                    InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        etTask.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((TextView) v).setText("");
                }
            }
        });

    }


    private void insert(@NonNull String text) {
        realmManager.insertTask(new Task(text));
        etTask.setText("");
        updateRecycler();
    }


    public void updateRecycler() {
        if (adapterTask == null) {
            adapterTask = new AdapterTask(
                    (OrderedRealmCollection<Task>) realmManager.getData(),
                    new TaskListChangeListener() {
                        @Override
                        public void updateFocusedItem(String receivedTaskContents) {
                            etTask.setText(receivedTaskContents);
                        }

                        @Override
                        public void updateTotalNumber(long totalNumber) {
                            updateTotalTasks(totalNumber);
                        }
                    }
            );
            rvTasks.setAdapter(adapterTask);
        }
    }

    public void updateTotalTasks(long totalNumber) {
        tvTotalTasks.setText(String.format(getResources().getString(R.string.total_tasks), totalNumber + ""));
    }

}
