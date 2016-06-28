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
import com.wolasoft.fiatlux.adapters.JokeStoryReflectListAdapter;
import com.wolasoft.fiatlux.interfaces.IJokeStoryReflectService;
import com.wolasoft.fiatlux.models.JokeStoryReflect;
import com.wolasoft.fiatlux.services.JokeStoryReflectService;
import com.wolasoft.fiatlux.services.ServiceInterface;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JokeStoryReflectListFragment extends BaseFragment {

    private static final String TYPE = "type";
    private JokeStoryReflectListAdapter adapter ;
    private JokeStoryReflectService service = null;
    private TextView emptyTextView;
    private ImageView emptyImageView;
    private String type = "";

    public JokeStoryReflectListFragment() {
        // Required empty public constructor
    }

    public static JokeStoryReflectListFragment newInstance(String param1){
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        JokeStoryReflectListFragment fragment = new JokeStoryReflectListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_joke_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle){
        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.joke_recycler_view);
        LinearLayoutManager layoutManager = new  LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new JokeStoryReflectListAdapter(getActivity().getApplicationContext());
        emptyTextView = (TextView) view.findViewById(R.id.empty_textview);
        emptyImageView = (ImageView) view.findViewById(R.id.empty_image);
        rv.setAdapter(adapter);
        TextView textView = (TextView) view.findViewById(R.id.network_issue_textview);

        service = new JokeStoryReflectService();
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
        service.getAll(CURRENT_SECTION, type, new IJokeStoryReflectService.CallBack<List<JokeStoryReflect>>() {
            @Override
            public void onSuccess(List<JokeStoryReflect> data) {
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
