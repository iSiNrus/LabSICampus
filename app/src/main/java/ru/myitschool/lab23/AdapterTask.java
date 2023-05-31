package ru.myitschool.lab23;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import kotlin.NotImplementedError;

public class AdapterTask extends RealmRecyclerViewAdapter<Task, AdapterTask.TaskViewHolder> {

    OrderedRealmCollection<Task> data;
    TaskListChangeListener listener;

    public AdapterTask(@NonNull OrderedRealmCollection<Task> data, @NonNull TaskListChangeListener listener) {
        super(data, true);
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        final Task model = getItem(position);
        if (model != null) {
            holder.tvTaskContents.setText(model.getTaskContents());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int holderPosition = holder.getAdapterPosition();
                listener.updateFocusedItem(data.get(holderPosition).getTaskContents());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int holderPosition = holder.getAdapterPosition();
                RealmManager realmManager = new RealmManager();
                realmManager.deleteTask(data.get(holderPosition).getTaskId());
                return true;
            }
        });
    }

    @Override
    public long getItemId(int index) {
        Task task = getItem(index);
        if (task != null) {
            return task.getTaskId();
        } else {
            return -1;
        }
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskContents;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskContents = itemView.findViewById(R.id.task_contents);
        }
    }
}
