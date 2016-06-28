package com.wolasoft.fiatlux.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wolasoft.fiatlux.MainActivity;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.adapters.LessonTypeListAdapter;
import com.wolasoft.fiatlux.interfaces.ILessonTypeService;
import com.wolasoft.fiatlux.models.LessonType;
import com.wolasoft.fiatlux.services.LessonTypeService;
import com.wolasoft.fiatlux.services.ServiceInterface;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonTypeListFragment extends BaseFragment {

    private RecyclerView rv = null;
    private LessonTypeListAdapter adapter = null;
    private  LessonTypeService service = null;
    private TextView emptyTextView;
    private ImageView emptyImageView;

    public LessonTypeListFragment() {
        // Required empty public constructor
    }

    public static LessonTypeListFragment newInstance(){
        Bundle args = new Bundle();
        LessonTypeListFragment fragment = new LessonTypeListFragment();
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
        rv = (RecyclerView) view.findViewById(R.id.lessontype_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LessonTypeListAdapter(getActivity().getApplicationContext());
        rv.setAdapter(adapter);

        emptyTextView = (TextView) view.findViewById(R.id.empty_textview);
        emptyImageView = (ImageView) view.findViewById(R.id.empty_image);

        service = new LessonTypeService();
        initializeView();
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initializeView();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private void initializeView(){
        ((MainActivity)getActivity()).showProgress(R.string.on_progress, false);
        service.getAll(CURRENT_SECTION, new ILessonTypeService.CallBack<List<LessonType>>() {
            @Override
            public void onSuccess(List<LessonType> data) {
                if(data.isEmpty() == true){
                    emptyTextView.setVisibility(View.VISIBLE);
                    emptyImageView.setVisibility(View.VISIBLE);
                }
                else {
                    emptyTextView.setVisibility(View.GONE);
                    emptyImageView.setVisibility(View.GONE);
                    adapter.onDataLoaded(data);
                }
                ((MainActivity)getActivity()).hideProgress();
            }

            @Override
            public void onFailure(int statusCode, String message) {
                ((MainActivity)getActivity()).hideProgress();
                Toast.makeText(getContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText(R.string.network_issue);
                emptyImageView.setVisibility(View.VISIBLE);
                emptyImageView.setImageResource(R.drawable.network);
            }
        });
    }

}
