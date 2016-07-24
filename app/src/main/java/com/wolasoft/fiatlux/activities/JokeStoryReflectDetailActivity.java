package com.wolasoft.fiatlux.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.IJokeStoryReflectService;
import com.wolasoft.fiatlux.models.JokeStoryReflect;
import com.wolasoft.fiatlux.services.JokeStoryReflectService;

import java.util.ArrayList;

public class JokeStoryReflectDetailActivity extends BaseActivity {

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
    private ArrayList<String> jokeIdList;
    private static final String JOKES_ID_LIST = "joke_list";
    private static final String CURRENT_POST_POSITION = "current_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int currentPosition = getIntent().getIntExtra(CURRENT_POST_POSITION, 0);
        jokeIdList = new ArrayList<>();
        jokeIdList = getIntent().getStringArrayListExtra(JOKES_ID_LIST);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), jokeIdList);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_joke_detail, menu);
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
        private ImageView jokeImage;
        private TextView jokeTitle;
        private TextView jokeContent;
        private JokeStoryReflectService service;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(String jokeId) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, jokeId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_joke_detail, container, false);

            String jokeId = getArguments().getString(ARG_SECTION_NUMBER);

            jokeImage = (ImageView) rootView.findViewById(R.id.joke_detail_image);

            jokeTitle = (TextView) rootView.findViewById(R.id.joke_detail_title);
            jokeTitle.setTypeface(((BaseActivity)getActivity()).getTitleTypeFace());

            jokeContent = (TextView) rootView.findViewById(R.id.joke_detail_content);
            jokeContent.setTypeface(((BaseActivity)getActivity()).getContentTypeFace());

            service = JokeStoryReflectService.getInstance();

            initializeView(jokeId);

            return rootView;
        }

        private void initializeView(String jokeId){
            ((JokeStoryReflectDetailActivity)getActivity()).showProgress(R.string.on_progress, false);
            service.getById(jokeId, new IJokeStoryReflectService.CallBack<JokeStoryReflect>(){

                @Override
                public void onSuccess(JokeStoryReflect data) {
                    if (!data.getImage().isEmpty()){
                        Utils.loadImage(getContext(), jokeImage, data.getImage());
                    }
                    jokeTitle.setText(data.getTitle());
                    jokeContent.setText(Html.fromHtml(data.getContent()));
                    ((JokeStoryReflectDetailActivity)getActivity()).hideProgress();
                }

                @Override
                public void onFailure(int statusCode, String message) {
                    ((JokeStoryReflectDetailActivity)getActivity()).hideProgress();
                }
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<String> jokeIdList;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<String> jokeIdList) {
            super(fm);
            this.jokeIdList = jokeIdList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(jokeIdList.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return jokeIdList.size();
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
