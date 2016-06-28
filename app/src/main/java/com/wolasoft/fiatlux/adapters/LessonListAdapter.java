package com.wolasoft.fiatlux.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.activities.ChapterListActivity;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.Lesson;

import java.util.List;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.LessonViewHolder> implements QueryCallback<Lesson> {

    private List<Lesson> lessonList = null;
    private Context context = null ;

    public LessonListAdapter() {
        this.context = null ;
        this.lessonList = null ;
    }

    public LessonListAdapter(Context context) {
        this.context = context;
    }

    public LessonListAdapter(Context context, List<Lesson> lessonList){
        this.context = context ;
        this.lessonList = lessonList ;
    }

    @Override
    public int getItemCount() {
        if (lessonList != null)
            return lessonList.size();
        else
            return 0;
    }

    public void onBindViewHolder(LessonViewHolder lessonViewHolder, int i) {
        Lesson lesson = lessonList.get(i) ;
        //lessonViewHolder.lessonImage.setImageResource(R.drawable.spirit);
        lessonViewHolder.display(lesson);

    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_lesson, viewGroup, false) ;
        return new LessonViewHolder(itemView, context) ;
    }

    @Override
    public void onDataReceived(List<Lesson> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<Lesson> dataList) {
        this.lessonList = dataList;
        notifyDataSetChanged();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder{
        private static final String LESSON_ID = "lesson_id";
        private ImageView lessonImage ;
        private TextView lessonTitle ;
        private TextView lessonDescription ;
        private int lessonId = 0;
        private List<Lesson> lessonList;
        Context context;

        public LessonViewHolder(final View view, final Context context){
            super(view);
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/quenta.otf");
            Typeface contentTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/caviar.ttf");
            lessonImage = (ImageView)view.findViewById(R.id.lesson_thumbnail) ;
            lessonTitle = (TextView) view.findViewById(R.id.lesson_title);
            lessonTitle.setTypeface(titleTypeFace);
            lessonDescription = (TextView) view.findViewById(R.id.lesson_resume);
            lessonDescription.setTypeface(contentTypeFace);
            this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, ChapterListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra(LESSON_ID, lessonId);
                    context.startActivity(intent);
                }
            });
        }

        public void display(Lesson lesson){
            Utils.loadImage(context, lessonImage, lesson.getImage());
            lessonTitle.setText(lesson.getTitle());
            lessonDescription.setText(Html.fromHtml(lesson.getExcerpt()));
            this.lessonId = lesson.getId();
        }
    }
}