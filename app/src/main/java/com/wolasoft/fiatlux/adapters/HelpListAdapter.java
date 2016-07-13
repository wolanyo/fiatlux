package com.wolasoft.fiatlux.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.activities.HelpDetailsActivity;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.Help;

import java.util.List;

/**
 * Created by kkoudo on 10/07/2016.
 */
public class HelpListAdapter extends RecyclerView.Adapter<HelpListAdapter.HelpViewHolder> implements QueryCallback<Help> {
    private List<Help> helpList = null;
    private Context context = null ;

    public HelpListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HelpViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_multimedia_list, viewGroup, false) ;
        return new HelpViewHolder(itemView, context) ;
    }

    @Override
    public void onBindViewHolder(HelpViewHolder helpViewHolder, int position) {
        Help help = helpList.get(position) ;
        //helpViewHolder.helpImage.setImageResource(R.drawable.spirit);
        helpViewHolder.display(help, position);
    }

    @Override
    public int getItemCount() {
        if (helpList != null)
            return helpList.size();
        else
            return 0;
    }

    @Override
    public void onDataReceived(List<Help> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<Help> dataList) {
        this.helpList = dataList;
        notifyDataSetChanged();
    }

    public static class HelpViewHolder extends RecyclerView.ViewHolder{
        private TextView helpTitle ;
        private TextView helpDate ;
        private int currentPosition = 0;
        private static final String CURRENT_HELP_POSITION = "current_position";
        Context context;

        public HelpViewHolder(View view, final Context context) {
            super(view);
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/quenta.otf");
            //helpImage = (ImageView)view.findViewById(R.id.list_image) ;
            helpTitle = (TextView) view.findViewById(R.id.title);
            helpTitle.setTypeface(titleTypeFace);
            helpDate = (TextView) view.findViewById(R.id.list_title);
            helpDate.setTypeface(titleTypeFace);
            this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, HelpDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra(CURRENT_HELP_POSITION, currentPosition);
                    context.startActivity(intent);
                }
            });
        }

        public void display(Help help, int currentPosition){
            //Utils.loadImage(context, helpImage, help.getImage());
            helpTitle.setText(help.getTitle());
            helpDate.setText(help.getPublicationDate());
            this.currentPosition = currentPosition;
        }
    }
}
