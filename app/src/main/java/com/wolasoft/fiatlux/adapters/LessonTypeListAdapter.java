package com.wolasoft.fiatlux.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.activities.LessonListActivity;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.LessonType;

import java.util.List;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class LessonTypeListAdapter extends RecyclerView.Adapter<LessonTypeListAdapter.LessonTypeViewHolder> implements QueryCallback<LessonType> {

    private List<LessonType> lessonTypeList = null;
    private Context context = null ;

    public LessonTypeListAdapter() {
        this.context = null ;
        this.lessonTypeList = null ;
    }

    public LessonTypeListAdapter(Context context) {
        this.context = context;
    }

    public LessonTypeListAdapter(Context context, List<LessonType> lessonTypeList){
        this.context = context ;
        this.lessonTypeList = lessonTypeList ;
    }

    @Override
    public int getItemCount() {
        if (lessonTypeList != null)
            return lessonTypeList.size();
        else
            return 0;
    }

    public void onBindViewHolder(LessonTypeViewHolder holder, int i) {
        LessonType lessonType = lessonTypeList.get(i) ;
        holder.display(lessonType);

    }

    @Override
    public LessonTypeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_list, viewGroup, false) ;
        return new LessonTypeViewHolder(itemView, context) ;
    }

    @Override
    public void onDataReceived(List<LessonType> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<LessonType> dataList) {
        this.lessonTypeList = dataList;
        notifyDataSetChanged();
    }

    public static class LessonTypeViewHolder extends RecyclerView.ViewHolder{
        private TextView lessonTypeTitle ;
        private TextView lessonTypeDescription ;
        private int lessonTypeId = 0;

        public LessonTypeViewHolder(final View view, final Context context){
            super(view);
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensedRegular.ttf");
            Typeface contentTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoLight.ttf");
            lessonTypeTitle = (TextView) view.findViewById(R.id.list_title);
            lessonTypeDescription = (TextView) view.findViewById(R.id.list_resume);
            lessonTypeDescription.setTypeface(contentTypeFace);
            lessonTypeTitle.setTypeface(titleTypeFace);

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, LessonListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra("lesson_type_id", lessonTypeId);
                    context.startActivity(intent);
                }
            });
        }

        public void display(LessonType lessonType){
            lessonTypeTitle.setText(lessonType.getLabel());
            lessonTypeDescription.setText(Html.fromHtml(lessonType.getDescription()));
            lessonTypeId = lessonType.getId();
        }
    }
}
