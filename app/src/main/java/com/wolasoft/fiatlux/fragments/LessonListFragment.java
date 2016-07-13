package com.wolasoft.fiatlux.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.adapters.LessonListAdapter;
import com.wolasoft.fiatlux.models.Lesson;
import com.wolasoft.fiatlux.services.LessonService;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonListFragment extends Fragment {

    private LessonListAdapter adapter = null;
    private RecyclerView rv ;

    public LessonListFragment() {
        // Required empty public constructor
    }

    public static LessonListFragment newInstance(){
        Bundle args = new Bundle();
        LessonListFragment fragment = new LessonListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson_type_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle){
        rv = (RecyclerView) view.findViewById(R.id.lesson_list_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LessonListAdapter(getActivity().getApplicationContext());
        rv.setAdapter(adapter);
        LessonService service = LessonService.getInstance();
        //service.getAll("");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private void initializeView(List<Lesson> lessonList){
        adapter.onDataLoaded(lessonList);
    }

}
