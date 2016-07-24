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
import com.wolasoft.fiatlux.activities.JokeStoryReflectDetailActivity;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.JokeStoryReflect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class JokeStoryReflectListAdapter extends RecyclerView.Adapter<JokeStoryReflectListAdapter.JokeViewHolder> implements QueryCallback<JokeStoryReflect> {

    private List<JokeStoryReflect> jokeStoryReflectList = null;
    private Context context = null ;
    private static ArrayList<String> jokeIdList;

    public JokeStoryReflectListAdapter() {
        this.context = null ;
        this.jokeStoryReflectList = null ;
    }

    public JokeStoryReflectListAdapter(Context context) {
        this.context = context;
        jokeIdList = new ArrayList<>();
    }

    public JokeStoryReflectListAdapter(Context context, List<JokeStoryReflect> jokeStoryReflectList){
        this.context = context ;
        this.jokeStoryReflectList = jokeStoryReflectList;
    }

    @Override
    public int getItemCount() {
        if (jokeStoryReflectList != null)
            return jokeStoryReflectList.size();
        else
            return 0;
    }

    public void onBindViewHolder(JokeViewHolder holder, int i) {
        JokeStoryReflect jokeStoryReflect = jokeStoryReflectList.get(i) ;
        int numberOfJoke = (jokeStoryReflectList != null) ? jokeStoryReflectList.size() : 0;
        //jokeViewHolder.jokeImage.setImageResource(R.drawable.spirit);
        holder.display(jokeStoryReflect, i);

    }

    @Override
    public JokeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_list, viewGroup, false) ;
        return new JokeViewHolder(itemView, context) ;
    }

    @Override
    public void onDataReceived(List<JokeStoryReflect> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<JokeStoryReflect> dataList) {
        if (jokeIdList!=null && jokeIdList.size() > 0)
            jokeIdList.clear();
        this.jokeStoryReflectList = dataList;
        for (JokeStoryReflect jokeStoryReflect : jokeStoryReflectList) {
            jokeIdList.add(Integer.toString(jokeStoryReflect.getId()));
        }
        notifyDataSetChanged();
    }

    public static class JokeViewHolder extends RecyclerView.ViewHolder{
        private static final String JOKE_ID_LIST = "joke_list";
        private static final String CURRENT_JOKE_POSITION = "current_position";
        private TextView jokeTitle ;
        private TextView jokeResume ;
        private int currentPosition = 0;
        private ArrayList<String> jokeIdList;
        //private Context context;

        public JokeViewHolder(final View view, final Context context){
            super(view);
            this.jokeIdList = JokeStoryReflectListAdapter.jokeIdList;
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensedRegular.ttf");
            Typeface contentTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoLight.ttf");
            jokeTitle = (TextView) view.findViewById(R.id.list_title);
            jokeTitle.setTypeface(titleTypeFace);
            jokeResume = (TextView) view.findViewById(R.id.list_resume);
            jokeResume.setTypeface(contentTypeFace);
            //this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, JokeStoryReflectDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putStringArrayListExtra(JOKE_ID_LIST, jokeIdList);
                    intent.putExtra(CURRENT_JOKE_POSITION, currentPosition);
                    context.startActivity(intent);
                }
            });
        }

        public void display(JokeStoryReflect jokeStoryReflect, int currentPosition){
            jokeTitle.setText(jokeStoryReflect.getTitle());
            jokeResume.setText(Html.fromHtml(jokeStoryReflect.getExcerpt()));
            this.currentPosition = currentPosition;
        }
    }
}
