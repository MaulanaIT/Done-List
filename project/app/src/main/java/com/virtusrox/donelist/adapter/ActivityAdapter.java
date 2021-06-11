package com.virtusrox.donelist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtusrox.donelist.R;
import com.virtusrox.donelist.model.ActivityModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ListViewHolder> {

    List<ActivityModel> listActivity;
    Context context;

    public ActivityAdapter(List<ActivityModel> listActivity, Context context) {
        this.listActivity = listActivity;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView textActivity;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            textActivity = itemView.findViewById(R.id.text_activity);
        }
    }

    @NonNull
    @NotNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_activity, parent, false);
        ActivityAdapter.ListViewHolder listViewHolder = new ListViewHolder(view);

        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityAdapter.ListViewHolder holder, int position) {
        final ActivityModel currentItem = listActivity.get(position);

        holder.textActivity.setText(currentItem.getActivity());
    }

    @Override
    public int getItemCount() {
        return listActivity.size();
    }
}
