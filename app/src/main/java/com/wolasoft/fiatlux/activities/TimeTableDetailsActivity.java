package com.wolasoft.fiatlux.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.ITimeTableService;
import com.wolasoft.fiatlux.models.TimeTable;
import com.wolasoft.fiatlux.services.BookService;
import com.wolasoft.fiatlux.services.TimeTableService;

public class TimeTableDetailsActivity extends BaseActivity {
    private static final String TIME_TABLE_ID = "time_table_id";
    private TextView timeTableTitle;
    private TextView timeTableDateTime;
    private TextView timeTableAddress;
    private TextView timeTableResume;

    private TextView buyButton;
    private TimeTableService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timeTableTitle = (TextView) findViewById(R.id.planning_title);
        timeTableDateTime = (TextView) findViewById(R.id.planning_date_content);
        timeTableAddress = (TextView) findViewById(R.id.planning_address_content);
        timeTableResume = (TextView) findViewById(R.id.planning_resume_content);

        String timeTableId = Integer.toString(getIntent().getIntExtra(TIME_TABLE_ID, 0));

        service = TimeTableService.getInstance();

        initializeView("31");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void initializeView(String id){
        service.getById(id, new ITimeTableService.CallBack<TimeTable>() {
            @Override
            public void onSuccess(final TimeTable data) {
                //Utils.loadImage(getApplicationContext(), bookImage, data.getImage());
                timeTableTitle.setText(data.getTitle());
                timeTableDateTime.setText(data.getEventDate()+"\nDe "+data.getStartTime()+" à "+data.getEndTime()+"\n");
                timeTableAddress.setText(data.getAddress());
                timeTableResume.setText(Html.fromHtml(data.getExcerpt()));
            }

            @Override
            public void onFailure(int statusCode, String message) {
                Toast.makeText(getApplicationContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
