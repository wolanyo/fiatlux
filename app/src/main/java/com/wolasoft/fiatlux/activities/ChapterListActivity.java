package com.wolasoft.fiatlux.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.adapters.ChapterListAdapter;
import com.wolasoft.fiatlux.interfaces.IChapterService;
import com.wolasoft.fiatlux.models.Chapter;
import com.wolasoft.fiatlux.services.ChapterService;
import com.wolasoft.fiatlux.services.ServiceInterface;

import java.util.List;

public class ChapterListActivity extends BaseActivity {

    private final String LESSON_ID = "lesson_id";

    private LinearLayoutManager layoutManager = null;
    private RecyclerView rv = null;
    private ChapterListAdapter adapter = null;
    private TextView textView = null;
    private ChapterService service = null;
    private int lessonId = 0;
    private TextView emptyTextView;
    private ImageView emptyImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        /*if (savedInstanceState == null){
            Toast.makeText(getApplicationContext(), "instance null", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "instance saved", Toast.LENGTH_SHORT).show();
        }*/

        rv = (RecyclerView) findViewById(R.id.chapterlist_recycler_view);
        layoutManager = new  LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        emptyTextView = (TextView) findViewById(R.id.empty_textview);
        emptyImageView = (ImageView) findViewById(R.id.empty_image);

        adapter = new ChapterListAdapter(getApplicationContext());
        rv.setAdapter(adapter);
        lessonId = getIntent().getIntExtra(LESSON_ID, 0);

        service = ChapterService.getInstance();

        initializeView();

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

                @Override
                public void onRefresh() {
                    Toast.makeText(getApplicationContext(), R.string.refreshing, Toast.LENGTH_SHORT).show();
                    initializeView();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt(LESSON_ID, lessonId);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        lessonId = savedInstanceState.getInt(LESSON_ID, 0);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chapter_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView(){
        showProgress(R.string.on_progress, false);
        service.getAll(Integer.toString(lessonId), new IChapterService.CallBack<List<Chapter>>() {
            @Override
            public void onSuccess(List<Chapter> data) {
                if(data.isEmpty() == true){
                    emptyTextView.setVisibility(View.VISIBLE);
                    emptyImageView.setVisibility(View.VISIBLE);
                }
                else{
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
