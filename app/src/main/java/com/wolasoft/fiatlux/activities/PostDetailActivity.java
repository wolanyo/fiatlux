package com.wolasoft.fiatlux.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.IPostService;
import com.wolasoft.fiatlux.models.Post;
import com.wolasoft.fiatlux.services.PostService;
import com.wolasoft.fiatlux.services.ServiceInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostDetailActivity extends BaseActivity {

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
    private ArrayList<String> postIdList;
    private static final String POSTS_ID_LIST = "post_list";
    private static final String CURRENT_POST_POSITION = "current_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int currentPosition = getIntent().getIntExtra(CURRENT_POST_POSITION, 0);
        postIdList = new ArrayList<>();
        postIdList = getIntent().getStringArrayListExtra(POSTS_ID_LIST);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), postIdList, fab);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_post_detail, menu);
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
        private ImageView postImage;
        private TextView postTitle ;
        private TextView postDate;
        private TextView postTime;
        private TextView postContent;
        private ImageView mediaTypeImage ;
        private PostService service;
        private Animation fadeInAnimation;
        private Animation fadeOutAnimation;
        private FloatingActionButton floatingActionButton;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(String postId) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, postId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);

            String postId = getArguments().getString(ARG_SECTION_NUMBER);

            fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
            postImage = (ImageView) rootView.findViewById(R.id.post_detail_image);
            postImage.setAnimation(fadeInAnimation);

            postTitle = (TextView) rootView.findViewById(R.id.post_detail_title);
            postTitle.setTypeface(((PostDetailActivity)getActivity()).getTitleTypeFace());

            postDate = (TextView) rootView.findViewById(R.id.post_detail_date);
            postDate.setTypeface(((PostDetailActivity)getActivity()).getDateTypeFace());

            postTime = (TextView) rootView.findViewById(R.id.post_detail_time);
            postTime.setTypeface(((PostDetailActivity)getActivity()).getTimeTypeFace());

            postContent = (TextView) rootView.findViewById(R.id.post_detail_content);
            postContent.setTypeface(((PostDetailActivity)getActivity()).getContentTypeFace());

            mediaTypeImage = (ImageView) rootView.findViewById(R.id.media_type_image);

            View view = getActivity().findViewById(R.id.fab);
            floatingActionButton = (FloatingActionButton) view;

            service = PostService.getInstance();
            initializeView(postId);

            return rootView;
        }

        private void initializeView(String postId){
            ((PostDetailActivity)getActivity()).showProgress(R.string.on_progress, false);
            service.getById(postId, new IPostService.CallBack<Post>() {
                @Override
                public void onSuccess(final Post data) {
                    if (!data.getImage().isEmpty()){
                        Utils.loadImage(getContext(), postImage, data.getImage());
                    }

                    postTitle.setText(data.getTitle());
                    postDate.setText(data.getPostDate());
                    postTime.setText(data.getPostTime());
                    postContent.setText(Html.fromHtml(data.getContent()));

                    if (data.getMediaType().compareToIgnoreCase("VIDEO") == 0) {
                        floatingActionButton.setVisibility(View.VISIBLE);
                        mediaTypeImage.setImageResource(R.drawable.ic_menu_movie);
                    }
                    else if(data.getMediaType().compareToIgnoreCase("AUDIO") == 0) {
                        floatingActionButton.setVisibility(View.VISIBLE);
                        mediaTypeImage.setImageResource(R.drawable.ic_menu_audio);
                    }
                    else {
                        floatingActionButton.setVisibility(View.GONE);
                        mediaTypeImage.setImageResource(R.drawable.ic_text);
                    }

                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), VimeoPlayerActivity.class);
                            intent.putExtra("media_id", data.getMediaURL());
                            startActivity(intent);
                        }
                    });

                    ((PostDetailActivity)getActivity()).hideProgress();
                }

                @Override
                public void onFailure(int statusCode, String message) {
                    ((PostDetailActivity)getActivity()).hideProgress();
                }
            });

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<String> postIdList;
        private FloatingActionButton floatingActionButton;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<String> postIdList, FloatingActionButton fab) {
            super(fm);
            this.postIdList = postIdList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(postIdList.get(position));
        }

        @Override
        public int getCount() {
            if (postIdList!=null)
                return postIdList.size();
            return 0;
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
