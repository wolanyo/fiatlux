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
import com.wolasoft.fiatlux.activities.PublicityDetailsActivity;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.Publicity;

import java.util.List;

/**
 * Created by kkoudo on 10/07/2016.
 */
public class PublicityListAdapter extends RecyclerView.Adapter<PublicityListAdapter.PublicityViewHolder> implements QueryCallback<Publicity>{
    private List<Publicity> publicityList = null;
    private Context context = null ;
    private static final String TEXT = "TEXT";
    private static final String VIDEO = "VIDEO";
    private static final String AUDIO = "AUDIO";

    public PublicityListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onDataReceived(List<Publicity> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<Publicity> dataList) {
        this.publicityList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public PublicityViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_multimedia_list, viewGroup, false) ;
        return new PublicityViewHolder(itemView, context) ;
    }

    @Override
    public void onBindViewHolder(PublicityViewHolder publicityViewHolder, int position) {
        Publicity publicity = publicityList.get(position) ;
        //publicityViewHolder.publicityImage.setImageResource(R.drawable.spirit);
        publicityViewHolder.display(publicity, publicity.getId());
    }

    @Override
    public int getItemCount() {
        if (publicityList != null)
            return publicityList.size();
        else
            return 0;
    }

    public static class PublicityViewHolder extends RecyclerView.ViewHolder{
        private ImageView publicityImage ;
        private TextView publicityTitle ;
        private TextView publicityDate ;
        private TextView publicityType ;
        private int currentPosition = 0;
        private static final String CURRENT_PUBLICITY_ID = "current_publicity_id";
        Context context;

        public PublicityViewHolder(View view, final Context context) {
            super(view);
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensedRegular.ttf");
            Typeface dateTimeTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoLight.ttf");
            Typeface publicityTypeTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoThin.ttf");
            publicityImage = (ImageView)view.findViewById(R.id.list_image) ;
            publicityTitle = (TextView) view.findViewById(R.id.list_title);
            publicityType = (TextView) view.findViewById(R.id.list_resume);

            publicityTitle.setTypeface(titleTypeFace);
            publicityType.setTypeface(publicityTypeTypeFace);
            publicityDate = (TextView) view.findViewById(R.id.list_date);
            publicityDate.setTypeface(dateTimeTypeFace);
            this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, PublicityDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra(CURRENT_PUBLICITY_ID, currentPosition);
                    context.startActivity(intent);
                }
            });
        }

        public void display(Publicity publicity, int currentPosition){
            Utils.loadImage(context, publicityImage, publicity.getImage());
            publicityTitle.setText(publicity.getTitle());
            publicityDate.setText(publicity.getPublicationDate());
            switch (publicity.getMediaType()){
                case VIDEO:
                    publicityType.setText("Video");
                    break;
                case AUDIO:
                    publicityType.setText("Audio");
                    break;
                case TEXT:
                    publicityType.setText("Texte");
                    break;
            }
            this.currentPosition = currentPosition;
        }
    }
}
