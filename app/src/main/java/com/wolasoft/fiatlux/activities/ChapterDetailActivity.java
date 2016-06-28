package com.wolasoft.fiatlux.activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.gson.Gson;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.interfaces.IChapterService;
import com.wolasoft.fiatlux.models.Chapter;
import com.wolasoft.fiatlux.services.ChapterService;
import com.wolasoft.fiatlux.services.ServiceInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChapterDetailActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ArrayList<String> chapterIDList;
    private static final String CHAPTER_ID_LIST = "chapter_list";
    private static final String CURRENT_CHAPTER_POSITION = "current_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int currentPosition = getIntent().getIntExtra(CURRENT_CHAPTER_POSITION, 0);
        chapterIDList = new ArrayList<>();
        chapterIDList = getIntent().getStringArrayListExtra(CHAPTER_ID_LIST);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), chapterIDList);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_chapter_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER= "section_number";
        private TextView chapterTitle = null;
        private TextView chapterLabel = null;
        private TextView chapterOrder = null;
        private TextView chapterContent = null;
        private ChapterService service = null;
        private String chapterId = "";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(String chapterId) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, chapterId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chapter_detail, container, false);
            chapterId = getArguments().getString(ARG_SECTION_NUMBER);

            chapterTitle = (TextView) rootView.findViewById(R.id.chapter_detail_title);
            chapterTitle.setTypeface(((BaseActivity)getActivity()).getTitleTypeFace());
            chapterLabel = (TextView) rootView.findViewById(R.id.chapter_detail_chapter);
            chapterLabel.setTypeface(((BaseActivity)getActivity()).getTitleTypeFace());
            chapterLabel.setText(R.string.chapter_label);
            chapterOrder = (TextView) rootView.findViewById(R.id.chapter_detail_chapter_number);
            chapterOrder.setTypeface(((BaseActivity)getActivity()).getTitleTypeFace());

            chapterContent = (TextView) rootView.findViewById(R.id.chapter_detail_content);
            chapterContent.setTypeface(((BaseActivity)getActivity()).getContentTypeFace());

            service = new ChapterService();

            initializeView();

            return rootView;
        }

        private void initializeView(){
            ((ChapterDetailActivity)getActivity()).showProgress(R.string.on_progress, false);
            service.getById(chapterId, new IChapterService.CallBack<Chapter>(){

                @Override
                public void onSuccess(Chapter data) {
                    chapterContent.setText(Html.fromHtml(data.getContent()));
                    chapterOrder.setText(Integer.toString(data.getPosition()));
                    chapterTitle.setText(data.getTitle());
                    ((ChapterDetailActivity)getActivity()).hideProgress();
                }

                @Override
                public void onFailure(int statusCode, String message) {
                    ((ChapterDetailActivity)getActivity()).hideProgress();
                }
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<String> chapterIDList;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<String> chapterIDList) {
            super(fm);
            this.chapterIDList = chapterIDList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(chapterIDList.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return chapterIDList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            /*switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }*/
            return null;
        }
    }
}
