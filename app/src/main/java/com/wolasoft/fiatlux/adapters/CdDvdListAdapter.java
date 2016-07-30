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
import com.wolasoft.fiatlux.activities.CdDvdDetailActivity;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.CdDvd;

import java.util.List;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class CdDvdListAdapter extends RecyclerView.Adapter<CdDvdListAdapter.CdDvdViewHolder> implements QueryCallback<CdDvd> {

    private List<CdDvd> cdDvdList = null;
    private Context context = null ;
    private static final String CURRENCY = " €";

    public CdDvdListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (cdDvdList != null)
            return cdDvdList.size();
        else
            return 0;
    }

    public void onBindViewHolder(CdDvdViewHolder holder, int i) {
        CdDvd cdDvd = cdDvdList.get(i) ;
        int numberOfCdDvd = (cdDvdList != null) ? cdDvdList.size() : 0;
        //cdDvdViewHolder.cdDvdImage.setImageResource(R.drawable.spirit);
        holder.display(cdDvd, i);

    }

    @Override
    public CdDvdViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_cddvd, viewGroup, false) ;
        return new CdDvdViewHolder(itemView, context) ;
    }

    @Override
    public void onDataReceived(List<CdDvd> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<CdDvd> dataList) {
        this.cdDvdList = dataList;
        notifyDataSetChanged();
    }

    public static class CdDvdViewHolder extends RecyclerView.ViewHolder{
        private String CD_DVD_ID = "cddvd_id";
        private static final String MEDIA_URL= "media_url";
        private ImageView cdDvdImage;
        private TextView cdDvdTitle ;
        private TextView cdDvdAuthor;
        private TextView cdDvdPrice;
        private String cdDvdId = "";
        private String mediaURL = "";
        private Context context;

        public CdDvdViewHolder(final View view, final Context context){
            super(view);
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensedRegular.ttf");
            Typeface contentTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/caviar.ttf");
            cdDvdImage = (ImageView) view.findViewById(R.id.cddvd_image);
            cdDvdTitle = (TextView) view.findViewById(R.id.cddvd_title);
            cdDvdTitle.setTypeface(titleTypeFace);
            cdDvdAuthor = (TextView) view.findViewById(R.id.cddvd_author);
            cdDvdAuthor.setTypeface(contentTypeFace);
            cdDvdPrice = (TextView) view.findViewById(R.id.cddvd_price);
            cdDvdPrice.setTypeface(contentTypeFace);
            this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, CdDvdDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra(CD_DVD_ID, cdDvdId);
                    intent.putExtra(MEDIA_URL, mediaURL);
                    context.startActivity(intent);
                }
            });
        }

        public void display(CdDvd cdDvd, int currentPosition){
            if (cdDvd.getImage() != null) {
                if (!cdDvd.getImage().isEmpty()) {
                    Utils.loadImage(context, cdDvdImage, cdDvd.getImage());
                }
            }
            cdDvdTitle.setText(cdDvd.getTitle());
            cdDvdAuthor.setText(cdDvd.getAuthor());
            cdDvdPrice.setText(Float.toString(cdDvd.getPrice())+CURRENCY);
            cdDvdId = Integer.toString(cdDvd.getId());
            mediaURL = cdDvd.getMediaURL();

        }
    }
}
