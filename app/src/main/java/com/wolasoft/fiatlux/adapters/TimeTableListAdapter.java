package com.wolasoft.fiatlux.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.activities.TimeTableDetailsActivity;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.TimeTable;

import java.util.List;

/**
 * Created by kkoudo on 10/07/2016.
 */
public class TimeTableListAdapter extends RecyclerView.Adapter<TimeTableListAdapter.TimeTableViewHolder> implements QueryCallback<TimeTable> {
    private List<TimeTable> timeTableList = null;
    private Context context = null ;

    public TimeTableListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onDataReceived(List<TimeTable> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<TimeTable> dataList) {
        this.timeTableList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public TimeTableViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_time_table, viewGroup, false) ;
        return new TimeTableViewHolder(itemView, context) ;
    }

    @Override
    public void onBindViewHolder(TimeTableViewHolder timeTableViewHolder, int position) {
        TimeTable timeTable = timeTableList.get(position) ;
        //timeTableViewHolder.timeTableImage.setImageResource(R.drawable.spirit);
        timeTableViewHolder.display(timeTable, timeTable.getId());
    }

    @Override
    public int getItemCount() {
        if (timeTableList != null)
            return timeTableList.size();
        else
            return 0;
    }

    public static class TimeTableViewHolder extends RecyclerView.ViewHolder{
        private TextView timeTableTitle ;
        private TextView timeTableDate ;
        private TextView timeTableTime ;
        private TextView timeTableAddress ;
        private int currentPosition = 0;
        private static final String TIME_TABLE_ID = "time_table_id";
        Context context;

        public TimeTableViewHolder(View view, final Context context) {
            super(view);
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensedRegular.ttf");
            Typeface dateTimeTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoLight.ttf");
            //timeTableImage = (ImageView)view.findViewById(R.id.list_image) ;
            timeTableTitle = (TextView) view.findViewById(R.id.time_table_title);
            timeTableTitle.setTypeface(titleTypeFace);
            timeTableDate = (TextView) view.findViewById(R.id.time_table_date);
            timeTableDate.setTypeface(dateTimeTypeFace);
            timeTableTime = (TextView) view.findViewById(R.id.time_table_time);
            timeTableTime.setTypeface(dateTimeTypeFace);
            timeTableAddress = (TextView) view.findViewById(R.id.time_table_address);
            timeTableAddress.setTypeface(dateTimeTypeFace);

            this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, TimeTableDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra(TIME_TABLE_ID, currentPosition);
                    context.startActivity(intent);
                }
            });
        }

        public void display(TimeTable timeTable, int currentPosition){
            //Utils.loadImage(context, timeTableImage, timeTable.getImage());
            timeTableTitle.setText(timeTable.getTitle());
            timeTableDate.setText(timeTable.getEventDate());
            timeTableTime.setText(timeTable.getStartTime()+" - "+timeTable.getEndTime());
            timeTableAddress.setText(timeTable.getAddress());
            this.currentPosition = currentPosition;
        }
    }
}
