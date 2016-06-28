package com.wolasoft.fiatlux.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wolasoft.fiatlux.MainActivity;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.adapters.PostAudioVideoListAdapter;
import com.wolasoft.fiatlux.interfaces.IPostService;
import com.wolasoft.fiatlux.models.Post;
import com.wolasoft.fiatlux.services.PostService;
import com.wolasoft.fiatlux.services.ServiceInterface;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostAudioVideoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostAudioVideoListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MEDIA_TYPE = "media_type";

    // TODO: Rename and change types of parameters
    private String mediaType;
    private PostAudioVideoListAdapter adapter = null;
    private LinearLayoutManager layoutManager;
    private RecyclerView rv ;
    private TextView emptyTextView;
    private ImageView emptyImageView;
    private PostService service = null;


    public PostAudioVideoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PostAudioVideoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostAudioVideoListFragment newInstance(String param1) {
        PostAudioVideoListFragment fragment = new PostAudioVideoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDIA_TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mediaType = getArguments().getString(ARG_MEDIA_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle){
         rv = (RecyclerView) view.findViewById(R.id.postlist_recycler_view);
        layoutManager = new  LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new PostAudioVideoListAdapter(getActivity().getApplicationContext());
        emptyTextView = (TextView) view.findViewById(R.id.empty_textview);
        emptyImageView = (ImageView) view.findViewById(R.id.empty_image);
        rv.setAdapter(adapter);
        service = new PostService();
        initializeView();

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                initializeView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initializeView(){
        ((MainActivity)getActivity()).showProgress(R.string.on_progress, false);
        service.getAllMultimedia(CURRENT_SECTION, mediaType, new IPostService.CallBack<List<Post>>() {
            @Override
            public void onSuccess(List<Post> data) {
                if(data.isEmpty() == true){
                    emptyTextView.setVisibility(View.VISIBLE);
                    emptyImageView.setVisibility(View.VISIBLE);
                }
                else {
                    emptyTextView.setVisibility(View.GONE);
                    emptyImageView.setVisibility(View.GONE);
                    adapter.onDataLoaded(data);
                }
                ((MainActivity)getActivity()).hideProgress();
            }

            @Override
            public void onFailure(int statusCode, String message) {
                ((MainActivity)getActivity()).hideProgress();
                Toast.makeText(getContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText(R.string.network_issue);
                emptyImageView.setVisibility(View.VISIBLE);
                emptyImageView.setImageResource(R.drawable.network);
            }
        });
    }

}
