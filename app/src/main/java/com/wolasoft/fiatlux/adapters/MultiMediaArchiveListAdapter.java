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

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.activities.MultiMediaArchiveDetailActivity;
import com.wolasoft.fiatlux.activities.VimeoPlayerActivity;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.MultiMediaArchive;

import java.util.List;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class MultiMediaArchiveListAdapter extends RecyclerView.Adapter<MultiMediaArchiveListAdapter.MultiMediaArchiveViewHolder> implements QueryCallback<MultiMediaArchive> {

    private List<MultiMediaArchive> multiMediaArchiveList = null;
    private Context context = null ;

    public MultiMediaArchiveListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (multiMediaArchiveList != null)
            return multiMediaArchiveList.size();
        else
            return 0;
    }

    public void onBindViewHolder(MultiMediaArchiveViewHolder multiMediaArchiveViewHolder, int i) {
        MultiMediaArchive multiMediaArchive = multiMediaArchiveList.get(i) ;
        //multiMediaArchiveViewHolder.multiMediaArchiveImage.setImageResource(R.drawable.spirit);
        multiMediaArchiveViewHolder.display(multiMediaArchive, i);

    }

    @Override
    public MultiMediaArchiveViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_multimedia_list, viewGroup, false) ;
        return new MultiMediaArchiveViewHolder(itemView, context) ;
    }

    @Override
    public void onDataReceived(List<MultiMediaArchive> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<MultiMediaArchive> dataList) {
        this.multiMediaArchiveList = dataList;
        notifyDataSetChanged();
    }

    public static class MultiMediaArchiveViewHolder extends RecyclerView.ViewHolder{
        private ImageView multiMediaArchiveImage ;
        private TextView multiMediaArchiveTitle ;
        private TextView multiMediaArchiveResume ;
        private TextView multiMediaArchiveDate ;
        private String archiveId = "";
        private String mediaURL = "";
        private static final String ARCHIVE_ID= "archive_id";
        private static final String MEDIA_URL= "media_url";
        private Context context;

        public MultiMediaArchiveViewHolder(final View view, final Context context){
            super(view);
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensedRegular.ttf");
            Typeface contentTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoLight.ttf");
            multiMediaArchiveImage = (ImageView)view.findViewById(R.id.list_image) ;
            multiMediaArchiveTitle = (TextView) view.findViewById(R.id.list_title);
            multiMediaArchiveTitle.setTypeface(titleTypeFace);
            multiMediaArchiveResume = (TextView) view.findViewById(R.id.list_resume);
            multiMediaArchiveResume.setTypeface(contentTypeFace);
            multiMediaArchiveDate = (TextView) view.findViewById(R.id.list_date);
            multiMediaArchiveDate.setTypeface(contentTypeFace);
            this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, MultiMediaArchiveDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra(ARCHIVE_ID, archiveId);
                    intent.putExtra(MEDIA_URL, mediaURL);
                    context.startActivity(intent);
                }
            });
        }

        public void display(MultiMediaArchive multiMediaArchive, int currentPosition){
            if (multiMediaArchive.getImage() != null) {
                if (!multiMediaArchive.getImage().isEmpty()) {
                    Utils.loadImage(context, multiMediaArchiveImage, multiMediaArchive.getImage());
                }
            }
            multiMediaArchiveTitle.setText(multiMediaArchive.getTitle());
            multiMediaArchiveResume.setText(Html.fromHtml(multiMediaArchive.getExcerpt()));
            multiMediaArchiveDate.setText(multiMediaArchive.getPublishDate());
            archiveId = Integer.toString(multiMediaArchive.getId());
            mediaURL = multiMediaArchive.getMediaURL();
        }
    }
}
