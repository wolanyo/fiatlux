package com.wolasoft.fiatlux.activities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.adapters.ChapterListAdapter;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.IChapterService;
import com.wolasoft.fiatlux.interfaces.ILessonService;
import com.wolasoft.fiatlux.models.Chapter;
import com.wolasoft.fiatlux.models.Lesson;
import com.wolasoft.fiatlux.services.ChapterService;
import com.wolasoft.fiatlux.services.FileDownloadService;
import com.wolasoft.fiatlux.services.LessonService;

import java.util.List;

public class ChapterListActivity extends BaseActivity {

    private final String LESSON_ID = "lesson_id";
    private static final String LESSON_FILE_URL = "lesson_file_url";

    private LinearLayoutManager layoutManager = null;
    private RecyclerView rv = null;
    private ChapterListAdapter adapter = null;
    private TextView textView = null;
    private ChapterService service = null;
    private LessonService lessonService = null;
    private String lessonId ;
    private String fileUrl;
    private TextView emptyTextView;
    private TextView resumeTextView;
    private TextView resumeTitleTextView;
    private ImageView emptyImageView;
    private FileDownloadService fileService;

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
        resumeTextView = (TextView) findViewById(R.id.resume_textview);
        resumeTitleTextView = (TextView) findViewById(R.id.resume_title_textview);
        resumeTitleTextView.setTypeface(getTitleTypeFace());
        emptyTextView = (TextView) findViewById(R.id.empty_textview);
        emptyImageView = (ImageView) findViewById(R.id.empty_image);

        adapter = new ChapterListAdapter(getApplicationContext());
        rv.setAdapter(adapter);
        lessonId = Integer.toString(getIntent().getIntExtra(LESSON_ID, 0));
        fileUrl = getIntent().getStringExtra(LESSON_FILE_URL);

        service = ChapterService.getInstance();
        lessonService = LessonService.getInstance();
        //fileService = FileDownloadService.getInstance(getApplicationContext());

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

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(enqueue);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {

                            String uriString = c
                                    .getString(c
                                            .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        }
                    }
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(LESSON_ID, lessonId);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        lessonId = savedInstanceState.getString(LESSON_ID, "");
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
            switch (item.getItemId()){
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                case R.id.action_download:
                    Toast.makeText(getApplicationContext(), R.string.download, Toast.LENGTH_SHORT).show();
                    dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(Utils.DOWNLOAD_BASE_URL+fileUrl));
                    enqueue = dm.enqueue(request);
                    showDownload();
                    return true;
                default:
            }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView(){
        showProgress(R.string.on_progress, false);
        service.getAll(lessonId, new IChapterService.CallBack<List<Chapter>>() {
            @Override
            public void onSuccess(List<Chapter> data) {
                if(data.isEmpty() == true){
                    emptyTextView.setVisibility(View.GONE);
                    emptyImageView.setVisibility(View.GONE);
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

        lessonService.getById(lessonId, new ILessonService.CallBack<Lesson>() {
            @Override
            public void onSuccess(Lesson data) {
                resumeTextView.setText(Html.fromHtml(data.getExcerpt()));
            }

            @Override
            public void onFailure(int statusCode, String message) {
                resumeTextView.setVisibility(View.GONE);
            }
        });
    }
}
