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
import com.wolasoft.fiatlux.activities.ChapterDetailActivity;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.Chapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ChapterViewHolder> implements QueryCallback<Chapter> {

    private List<Chapter> chapterList = null;
    private Context context = null ;
    private static ArrayList<String> chapterIdList;

    public ChapterListAdapter(Context context) {
        this.context = context;
        chapterIdList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        if (chapterList != null)
            return chapterList.size();
        else
            return 0;
    }

    public void onBindViewHolder(ChapterViewHolder holder, int i) {
        Chapter chapter = chapterList.get(i) ;
        int numberOfChapter = (chapterList != null) ? chapterList.size() : 0;
        //chapterViewHolder.chapterImage.setImageResource(R.drawable.spirit);
        holder.display(chapter, i);

    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_list, viewGroup, false) ;
        return new ChapterViewHolder(itemView, context) ;
    }

    @Override
    public void onDataReceived(List<Chapter> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<Chapter> dataList) {
        if (chapterIdList!=null && chapterIdList.size() > 0)
            chapterIdList.clear();
        this.chapterList = dataList;
        for (Chapter chapter: chapterList) {
            chapterIdList.add(Integer.toString(chapter.getId()));
        }
        notifyDataSetChanged();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder{
        private static final String CHAPTER_ID_LIST = "chapter_list";
        private static final String CURRENT_CHAPTER_POSITION = "current_position";
        //private ImageView chapterImage ;
        private TextView chapterTitle ;
        private TextView chapterResume ;
        private int currentPosition = 0;
        private ArrayList<String> chapterIdList;
        //private Context context;

        public ChapterViewHolder(final View view, final Context context){
            super(view);
            this.chapterIdList = ChapterListAdapter.chapterIdList;
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensedRegular.ttf");
            Typeface contentTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoLight.ttf");
            //chapterImage = (ImageView)view.findViewById(R.id.list_image) ;
            chapterTitle = (TextView) view.findViewById(R.id.list_title);
            chapterTitle.setTypeface(titleTypeFace);
            chapterResume = (TextView) view.findViewById(R.id.list_resume);
            chapterResume.setTypeface(contentTypeFace);
            //this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, ChapterDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra(CHAPTER_ID_LIST, chapterIdList);
                    intent.putExtra(CURRENT_CHAPTER_POSITION, currentPosition);
                    context.startActivity(intent);
                }
            });
        }

        public void display(Chapter chapter, int currentPosition){
            //chapterImage.setImageResource();
            //Utils.loadImage(context, chapterImage, chapter.getImage());
            chapterTitle.setText(chapter.getTitle());
            chapterResume.setText(Html.fromHtml(chapter.getExcerpt()));
            this.currentPosition = currentPosition;
        }
    }
}
