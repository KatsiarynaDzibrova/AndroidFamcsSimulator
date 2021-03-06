package com.source.studsimulator.ui.fragments.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.source.studsimulator.R;
import com.source.studsimulator.model.entity.StudentActivity;

import java.util.ArrayList;
import java.util.List;

public class ActiveButtonsAdapter
        extends RecyclerView.Adapter<ActiveButtonsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Button button;

        ViewHolder(View view) {
            super(view);
            button = view.findViewById(R.id.button);
        }
    }

    private List<StudentActivity> studentActivities;
    private ArrayList<Boolean> isButtonActivated;
    private AdapterListener adapterListener;

    public void setButtonDisActivate(int indexOfActivatedButton) {
        isButtonActivated.set(indexOfActivatedButton, !isButtonActivated.get(indexOfActivatedButton));
    }

    public ActiveButtonsAdapter(List<StudentActivity> activities) {
        this.studentActivities = activities;

        isButtonActivated = new ArrayList<>();
        for (int i = 0; i < activities.size(); ++i) {
            isButtonActivated.add(false);
        }
    }

    public List<Integer> getActiveButtonsIndices() {
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < isButtonActivated.size(); ++i) {
            if (isButtonActivated.get(i)) {
                indices.add(i);
            }
        }
        return indices;
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public void setSkills(int programming, int english) {
        for (StudentActivity activity : studentActivities) {
            activity.setSkills(programming, english);
        }
    }

    @Override
    public int getItemCount() {
        return studentActivities.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ind) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buttons_linear_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int ind) {
        viewHolder.button.setText(studentActivities.get(ind).getTitle());
        if (studentActivities.get(ind).isEnable()) {
            viewHolder.button.setBackgroundColor(isButtonActivated.get(ind) ? Color.GREEN : Color.WHITE);
        } else {
            viewHolder.button.setBackgroundColor(Color.GRAY);
        }
        viewHolder.button.setOnClickListener(v -> adapterListener.onClick(ind));
    }

    public interface AdapterListener {
        void onClick(int position);
    }
}
