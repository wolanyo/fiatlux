package com.wolasoft.fiatlux.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.adapters.LessonListAdapter;
import com.wolasoft.fiatlux.interfaces.ILessonService;
import com.wolasoft.fiatlux.models.Lesson;
import com.wolasoft.fiatlux.services.LessonService;
import com.wolasoft.fiatlux.services.ServiceInterface;

import java.util.List;

public class LessonListActivity extends BaseActivity {

    private int lessonTypeID = 0;
    private LessonService service;
    private RecyclerView rv = null;
    private LessonListAdapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private TextView emptyTextView;
    private ImageView emptyImageView;
    private final String LESSON_TYPE_ID = "lesson_type_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        /*if (savedInstanceState == null){
            lessonTypeID = getIntent().getIntExtra(LESSON_TYPE_ID, 0);
            Toast.makeText(getApplicationContext(), "instance not saved", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "instance saved", Toast.LENGTH_SHORT).show();
            lessonTypeID = savedInstanceState.getInt(LESSON_TYPE_ID, 0);
        }*/

        lessonTypeID = getIntent().getIntExtra(LESSON_TYPE_ID, 0);
        //Toast.makeText(getApplicationContext(), "instance not saved", Toast.LENGTH_SHORT).show();

        rv = (RecyclerView) findViewById(R.id.lesson_list_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LessonListAdapter(getApplicationContext());
        rv.setAdapter(adapter);
        emptyTextView = (TextView) findViewById(R.id.empty_textview);
        emptyImageView = (ImageView) findViewById(R.id.empty_image);

        service = new LessonService();

        initializeView();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), R.string.refreshing, Toast.LENGTH_SHORT).show();
                initializeView();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        lessonTypeID = getIntent().getIntExtra("lesson_type_id", 0);
        //Toast.makeText(getApplicationContext(), "On Resume "+lessonTypeID, Toast.LENGTH_SHORT).show();
        //service.getAll(Integer.toString(lessonTypeID));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt(LESSON_TYPE_ID, lessonTypeID);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lessonTypeID = savedInstanceState.getInt(LESSON_TYPE_ID);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private void initializeView(){
        ((this)).showProgress(R.string.on_progress, false);
        service.getAll(Integer.toString(lessonTypeID), new ILessonService.CallBack<List<Lesson>>() {

            @Override
            public void onSuccess(List<Lesson> data) {
                if(data.isEmpty() == true){
                    emptyTextView.setVisibility(View.VISIBLE);
                    emptyImageView.setVisibility(View.VISIBLE);
                }
                else {
                    emptyTextView.setVisibility(View.GONE);
                    emptyImageView.setVisibility(View.GONE);
                    adapter.onDataLoaded(data);
                }
                hideProgress();
            }

            @Override
            public void onFailure(int statusCode, String message) {
                hideProgress();
                Toast.makeText(getApplicationContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText(R.string.network_issue);
                emptyImageView.setVisibility(View.VISIBLE);
                emptyImageView.setImageResource(R.drawable.network);
            }
        });
    }
}
