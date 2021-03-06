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
import com.wolasoft.fiatlux.activities.PostDetailActivity;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class PostAudioVideoListAdapter extends RecyclerView.Adapter<PostAudioVideoListAdapter.PostViewHolder> implements QueryCallback<Post> {

    private List<Post> postList = null;
    private Context context = null ;
    private static ArrayList<String> postIdList;

    public PostAudioVideoListAdapter(Context context) {
        this.context = context;
        postIdList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        if (postList != null)
            return postList.size();
        else
            return 0;
    }

    public void onBindViewHolder(PostViewHolder postViewHolder, int i) {
        Post post = postList.get(i) ;
        //postViewHolder.postImage.setImageResource(R.drawable.spirit);
        postViewHolder.display(post, i);

    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_post_multimedia_list, viewGroup, false) ;
        return new PostViewHolder(itemView, context) ;
    }

    @Override
    public void onDataReceived(List<Post> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<Post> dataList) {
        if (postIdList!=null && postIdList.size() > 0)
            postIdList.clear();
        this.postList = dataList;
        for (Post post: postList) {
            postIdList.add(Integer.toString(post.getId()));
        }
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        private ImageView postImage ;
        private TextView postTitle ;
        private TextView postResume ;
        private TextView postDate ;
        private int currentPosition = 0;
        private ArrayList<String> postIdList ;
        private static final String POSTS_ID_LIST = "post_list";
        private static final String CURRENT_POST_POSITION = "current_position";
        private Context context;

        public PostViewHolder(final View view, final Context context){
            super(view);
            postIdList = PostAudioVideoListAdapter.postIdList;
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensedRegular.ttf");
            postImage = (ImageView)view.findViewById(R.id.list_image) ;
            postTitle = (TextView) view.findViewById(R.id.list_title);
            postTitle.setTypeface(titleTypeFace);
            postResume = (TextView) view.findViewById(R.id.list_resume);
            postDate = (TextView) view.findViewById(R.id.list_date);
            postDate.setTypeface(titleTypeFace);
            this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, PostDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra(CURRENT_POST_POSITION, currentPosition);
                    intent.putStringArrayListExtra(POSTS_ID_LIST, postIdList);
                    context.startActivity(intent);
                }
            });
        }

        public void display(Post post, int currentPosition){
            if (post.getImage() != null) {
                if (!post.getImage().isEmpty()) {
                    Utils.loadImage(context, postImage, post.getImage());
                }
            }

            postTitle.setText(post.getTitle());
            postResume.setText(Html.fromHtml(post.getExcerpt()));
            postDate.setText(post.getPostDate());
            this.currentPosition = currentPosition;
        }
    }
}
