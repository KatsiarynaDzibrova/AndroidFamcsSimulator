package com.source.studsimulator.ui.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.source.studsimulator.R;
import com.source.studsimulator.model.ActionObjects;
import com.source.studsimulator.model.entity.StudentActivity;
import com.source.studsimulator.model.entity.Work;
import com.source.studsimulator.ui.fragments.adapters.ActiveButtonsAdapter;
import com.source.studsimulator.ui.fragments.adapters.BlockUnactiveButtonsAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkFragment extends Fragment {

    private RecyclerView sideJobRv;
    private RecyclerView workRv;
    private RecyclerView summerWorkRv;

    private List<StudentActivity> sideJobList;
    private List<StudentActivity> workList;
    private List<StudentActivity> summerWorkList;

    private ArrayList<Boolean> isSideJobActive = new ArrayList<>();
    private int activeWorkIndex = -1;
    private int activeSummerWorkIndex = -1;
    private int programming = 0;
    private int english = 0;

    private WorkFragment.OnWorkFragmentListener activityListener;

    ActiveButtonsAdapter sideJobsAdapter;
    BlockUnactiveButtonsAdapter workAdapter;
    BlockUnactiveButtonsAdapter summerWorkAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.work_fragment_activity, null);

        initializeLists();

        initializeRecyclerView(view);

        if (isSideJobActive.size() == 0) {
            for (int i = 0; i < sideJobList.size(); ++i) {
                isSideJobActive.add(false);
            }
        }

        return view;
    }

    public void activateButton(int number, Work.TypeOfWork type) {
        switch (type) {
            case SUMMER:
                summerWorkAdapter.setIndexOfActivatedButton(number);
                changeSummerWorkButtonActivity(number);
                break;
            case FULL_TIME:
                workAdapter.setIndexOfActivatedButton(number);
                changeWorkButtonActivity(number);
                break;
            case SIDE:
                sideJobsAdapter.setButtonDisActivate(number);
                changeAccessForSideButton(number);
                sideJobsAdapter.notifyDataSetChanged();
                break;
        }
    }

    public interface OnWorkFragmentListener {
        void clickOnWorkButton(int position, Work.TypeOfWork type);

        void unclickOnWorkButton(Work work);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WorkFragment.OnWorkFragmentListener) {
            activityListener = (WorkFragment.OnWorkFragmentListener) context;
        }
    }

    public void updateSkills(int programming, int english) {
        this.programming = programming;
        this.english = english;
    }

    private void changeAccessForSideButton(int pos) {
        isSideJobActive.set(pos, !isSideJobActive.get(pos));
    }

    private void changeWorkButtonActivity(int pos) {
        activeWorkIndex = activeWorkIndex == pos ? -1 : pos;
    }

    private void changeSummerWorkButtonActivity(int pos) {
        activeSummerWorkIndex = activeSummerWorkIndex == pos ? -1 : pos;
    }

    private void initializeLists() {
        sideJobList = ActionObjects.getSideJobList();
        workList = ActionObjects.getWorkList();
        summerWorkList = ActionObjects.getSummerWorkList();
    }

    public void unClick(Work workItem, Work.TypeOfWork type) {
        int position = 0;
        switch (type) {
            case SUMMER:
                position = ActionObjects.getSummerWorkList().indexOf(workItem);
            case FULL_TIME:
                position = ActionObjects.getWorkList().indexOf(workItem);
            case SIDE:
                position = ActionObjects.getSideJobList().indexOf(workItem);
        }
        ActionObjects.getSummerWorkList().indexOf(workItem);
        activityListener.unclickOnWorkButton((Work) summerWorkList.get(activeSummerWorkIndex));
        summerWorkAdapter.setIndexOfActivatedButton(position);
        changeSummerWorkButtonActivity(position);
    }

    private void initializeRecyclerView(View view) {
        sideJobRv = view.findViewById(R.id.sideJobRV);
        sideJobRv.setLayoutManager(new LinearLayoutManager(getContext()));
        sideJobRv.setHasFixedSize(true);
        sideJobsAdapter = new ActiveButtonsAdapter(sideJobList);
        sideJobsAdapter.setSkills(programming, english);
        sideJobRv.setAdapter(sideJobsAdapter);
        sideJobsAdapter.setAdapterListener(position -> {
            List<Integer> currentIndices = sideJobsAdapter.getActiveButtonsIndices();
            if (currentIndices.contains(position)) {
                activityListener.unclickOnWorkButton((Work) sideJobList.get(position));
                sideJobsAdapter.setButtonDisActivate(position);
                changeAccessForSideButton(position);
                sideJobsAdapter.notifyDataSetChanged();
            } else {
                activityListener.clickOnWorkButton(position, Work.TypeOfWork.SIDE);
            }
        });

        for (int i = 0; i < isSideJobActive.size(); ++i) {
            if (isSideJobActive.get(i)) {
                sideJobsAdapter.setButtonDisActivate(i);
            }
        }

        workRv = view.findViewById(R.id.workRV);
        workRv.setLayoutManager(new LinearLayoutManager(getContext()));
        workRv.setHasFixedSize(true);
        workAdapter = new BlockUnactiveButtonsAdapter(workList);
        workRv.setAdapter(workAdapter);
        workAdapter.setIndexOfActivatedButton(activeWorkIndex);
        workAdapter.setSkills(programming, english);
        workAdapter.setAdapterListener(position -> {
            int currentPosition = workAdapter.getIndexOfActivatedButton();
            if (currentPosition == position) {
                activityListener.unclickOnWorkButton((Work) workList.get(activeWorkIndex));
                workAdapter.setIndexOfActivatedButton(position);
                changeWorkButtonActivity(position);
            } else {
                activityListener.clickOnWorkButton(position, Work.TypeOfWork.FULL_TIME);
            }
            workAdapter.notifyDataSetChanged();
        });

        summerWorkRv = view.findViewById(R.id.summerWorkRV);
        summerWorkRv.setLayoutManager(new LinearLayoutManager(getContext()));
        summerWorkRv.setHasFixedSize(true);
        summerWorkAdapter = new BlockUnactiveButtonsAdapter(summerWorkList);
        summerWorkRv.setAdapter(summerWorkAdapter);
        summerWorkAdapter.setIndexOfActivatedButton(activeSummerWorkIndex);
        summerWorkAdapter.setSkills(programming, english);
        summerWorkAdapter.setAdapterListener(position -> {
            int currentPosition = summerWorkAdapter.getIndexOfActivatedButton();
            if (currentPosition == position) {
                activityListener.unclickOnWorkButton((Work) summerWorkList.get(activeSummerWorkIndex));
                summerWorkAdapter.setIndexOfActivatedButton(position);
                changeSummerWorkButtonActivity(position);
            } else {
                activityListener.clickOnWorkButton(position, Work.TypeOfWork.SUMMER);
            }
            summerWorkAdapter.notifyDataSetChanged();
        });
    }
}
