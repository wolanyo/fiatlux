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

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.activities.MultiMediaActivity;
import com.wolasoft.fiatlux.adapters.MultiMediaArchiveListAdapter;
import com.wolasoft.fiatlux.interfaces.IMultiMediaArchiveService;
import com.wolasoft.fiatlux.models.MultiMediaArchive;
import com.wolasoft.fiatlux.services.MultiMediaArchiveService;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultiMediaArchiveListFragment extends BaseFragment {

    private static final String ARCHIVE_TYPE = "archive_type";

    private MultiMediaArchiveListAdapter mAdapter;
    private MultiMediaArchiveService service = null;
    private String mArchiveType;
    private TextView emptyTextView;
    private ImageView emptyImageView;

    public MultiMediaArchiveListFragment() {
        // Required empty public constructor
    }

    public static MultiMediaArchiveListFragment newInstance(String param1){
        Bundle args = new Bundle();
        args.putString(ARCHIVE_TYPE, param1);
        MultiMediaArchiveListFragment fragment = new MultiMediaArchiveListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArchiveType = getArguments().getString(ARCHIVE_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multimedia_archive_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle){
        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.mediaarchive_recycler_view);
        LinearLayoutManager layoutManager = new  LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        mAdapter = new MultiMediaArchiveListAdapter(getActivity().getApplicationContext());
        emptyTextView = (TextView) view.findViewById(R.id.empty_textview);
        emptyImageView = (ImageView) view.findViewById(R.id.empty_image);
        rv.setAdapter(mAdapter);

        service = MultiMediaArchiveService.getInstance();
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
        ((MultiMediaActivity)getActivity()).showProgress(R.string.on_progress, false);
        service.getAll(CURRENT_SECTION, mArchiveType, new IMultiMediaArchiveService.CallBack<List<MultiMediaArchive>>() {
            @Override
            public void onSuccess(List<MultiMediaArchive> data) {
                if(data.isEmpty() == true){
                    emptyTextView.setVisibility(View.VISIBLE);
                    emptyImageView.setVisibility(View.VISIBLE);
                }
                else {
                    emptyTextView.setVisibility(View.GONE);
                    emptyImageView.setVisibility(View.GONE);
                    mAdapter.onDataLoaded(data);
                }
                ((MultiMediaActivity)getActivity()).hideProgress();
            }

            @Override
            public void onFailure(int statusCode, String message) {
                ((MultiMediaActivity)getActivity()).hideProgress();
                Toast.makeText(getContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText(R.string.network_issue);
                emptyImageView.setVisibility(View.VISIBLE);
                emptyImageView.setImageResource(R.drawable.network);
            }
        });
    }
}
