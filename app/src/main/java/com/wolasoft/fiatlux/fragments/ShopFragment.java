package com.wolasoft.fiatlux.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wolasoft.fiatlux.MainActivity;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.adapters.BookListAdapter;
import com.wolasoft.fiatlux.adapters.CdDvdListAdapter;
import com.wolasoft.fiatlux.interfaces.IBookService;
import com.wolasoft.fiatlux.interfaces.ICdDvdService;
import com.wolasoft.fiatlux.models.Book;
import com.wolasoft.fiatlux.models.CdDvd;
import com.wolasoft.fiatlux.services.BookService;
import com.wolasoft.fiatlux.services.CdDvdService;
import com.wolasoft.fiatlux.services.ServiceInterface;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayoutManager bookLayoutManager;
    private LinearLayoutManager cdLayoutManager;
    private LinearLayoutManager dvdLayoutManager;
    private RecyclerView cdRecyclerView = null;
    private RecyclerView dvdRecyclerView = null;
    private RecyclerView bookRecyclerView = null;
    private BookListAdapter bookAdapter = null;
    private CdDvdListAdapter cdAdapter = null;
    private CdDvdListAdapter dvdAdapter = null;
    private BookService bookService = null;
    private CdDvdService cdService = null;
    private CdDvdService dvdService = null;


    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        bookRecyclerView = (RecyclerView) view.findViewById(R.id.book_recycler_view);
        bookLayoutManager = new LinearLayoutManager(getActivity());
        bookLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bookRecyclerView.setLayoutManager(bookLayoutManager);
        bookAdapter = new BookListAdapter(getActivity().getApplicationContext());
        bookRecyclerView.setAdapter(bookAdapter);

        cdRecyclerView = (RecyclerView) view.findViewById(R.id.cd_recycler_view);
        cdLayoutManager = new LinearLayoutManager(getActivity());
        cdLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        cdRecyclerView.setLayoutManager(cdLayoutManager);
        cdAdapter = new CdDvdListAdapter(getActivity().getApplicationContext());
        cdRecyclerView.setAdapter(cdAdapter);

        dvdRecyclerView = (RecyclerView) view.findViewById(R.id.dvd_recycler_view);
        dvdLayoutManager = new LinearLayoutManager(getActivity());
        dvdLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dvdRecyclerView.setLayoutManager(dvdLayoutManager);
        dvdAdapter = new CdDvdListAdapter(getActivity().getApplicationContext());
        dvdRecyclerView.setAdapter(dvdAdapter);;

        bookService = new BookService();
        cdService = new CdDvdService();
        dvdService = new CdDvdService();

        initializeView();
    }

    private void initializeView(){
        ((MainActivity)getActivity()).showProgress(R.string.on_progress, false);
        bookService.getAll("", new IBookService.CallBack<List<Book>>() {
            @Override
            public void onSuccess(List<Book> data) {
                bookAdapter.onDataLoaded(data);
                //((MainActivity)getActivity()).hideProgress();
            }

            @Override
            public void onFailure(int statusCode, String message) {
                //((MainActivity)getActivity()).hideProgress();

            }
        });

        cdService.getAll(CURRENT_SECTION, "CD", new ICdDvdService.CallBack<List<CdDvd>>() {
            @Override
            public void onSuccess(List<CdDvd> data) {
                cdAdapter.onDataLoaded(data);
                //((MainActivity)getActivity()).hideProgress();
            }

            @Override
            public void onFailure(int statusCode, String message) {
                //((MainActivity)getActivity()).hideProgress();

            }
        });

        dvdService.getAll(CURRENT_SECTION, "DVD", new ICdDvdService.CallBack<List<CdDvd>>() {
            @Override
            public void onSuccess(List<CdDvd> data) {
                dvdAdapter.onDataLoaded(data);
                ((MainActivity)getActivity()).hideProgress();
            }

            @Override
            public void onFailure(int statusCode, String message) {
                ((MainActivity)getActivity()).hideProgress();
                Toast.makeText(getContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private class ExecuteService extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            initializeView();
            return null;
        }
    }

}
