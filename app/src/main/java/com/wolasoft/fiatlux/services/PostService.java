package com.wolasoft.fiatlux.services;

import android.content.Context;
import android.widget.TextView;

import com.wolasoft.fiatlux.adapters.PostAudioVideoListAdapter;
import com.wolasoft.fiatlux.adapters.PostListAdapter;
import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.IPostService;
import com.wolasoft.fiatlux.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class PostService implements IPostService {
    private PostListAdapter adapter = null;
    private PostAudioVideoListAdapter audiVideoAdapter = null;
    private Context context = null;
    private TextView textView = null;
    private Post myPost ;

    public PostService() {
    }

    public PostService(Post myPost) {
        this.myPost = myPost;
    }

    public PostService(PostListAdapter adapter, Context context, TextView textView) {
        this.adapter = adapter;
        this.context = context;
        this.textView = textView;
    }

    public PostService(PostAudioVideoListAdapter audiVideoAdapter, Context context, TextView textView) {
        this.audiVideoAdapter = audiVideoAdapter;
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void getAll(String section, final CallBack<List<Post>> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<Post>> call = client.listPost(section) ;
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    if(callback!=null){
                        callback.onSuccess(response.body());
                    }
                } else {
                    if (callback!=null){
                        callback.onFailure(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getAllMultimedia(String section, String mediaType, final CallBack<List<Post>> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<Post>> call = client.listMultiMediaPost(section, mediaType) ;

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    if(callback!=null){
                        callback.onSuccess(response.body());
                    }
                } else {
                    if (callback!=null){
                        callback.onFailure(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, final CallBack<Post> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<Post> call = client.getPostById(id) ;

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    if(callback!=null){
                        callback.onSuccess(response.body());
                    }
                } else {
                    if (callback!=null){
                        callback.onFailure(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }
}
