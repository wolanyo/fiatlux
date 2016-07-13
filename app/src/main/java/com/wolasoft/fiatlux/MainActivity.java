package com.wolasoft.fiatlux;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wolasoft.fiatlux.activities.BaseActivity;
import com.wolasoft.fiatlux.activities.ForewordActivity;
import com.wolasoft.fiatlux.activities.MultiMediaActivity;
import com.wolasoft.fiatlux.activities.SettingsActivity;
import com.wolasoft.fiatlux.fragments.JokeStoryReflectListFragment;
import com.wolasoft.fiatlux.fragments.LessonTypeListFragment;
import com.wolasoft.fiatlux.fragments.PostAudioVideoListFragment;
import com.wolasoft.fiatlux.fragments.PostListFragment;
import com.wolasoft.fiatlux.fragments.PublicityFragment;
import com.wolasoft.fiatlux.fragments.ShopFragment;
import com.wolasoft.fiatlux.fragments.TimeTableFragment;
import com.wolasoft.fiatlux.gcm.RegistrationService;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TV_PROGRAM = "TVPROGRAM";
    private static final String SEMINARY = "SEMINARY";
    private static final String AUDIO_POST = "AUDIO";
    private static final String VIDEO_POST = "VIDEO";
    private static final String JOKE = "BLAGUE";
    private static final String STORY = "HISTOIRE";
    private static final String REFLECT = "PENSEE";
    private boolean POSTS_MENUES_VISIBLE = true;
    private String CURRENT_SECTION;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private PostListFragment postListFragment;
    private SharedPreferences sharedPreferences;

    private ActionBar actionBar;
    private MenuItem postTypeSpinner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setting up prefenrences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        CURRENT_SECTION = sharedPreferences.getString(SettingsActivity.CURRENT_SECTION_KEY, "");
        //Toast.makeText(this, CURRENT_SECTION, Toast.LENGTH_SHORT).show();

        postListFragment = PostListFragment.newInstance();
        fragmentTransaction.replace(R.id.fragment_container, postListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        /*Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.post_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);*/

        //start the registration service
        Intent i = new Intent(this, RegistrationService.class);
        startService(i);

        getSupportActionBar().setTitle("Articles");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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
        switch (id){
            case R.id.action_post_all:
                replaceFragment(postListFragment, R.string.nav_all_post);
                break;
            case R.id.action_post_video:
                PostAudioVideoListFragment postVideoFragment = PostAudioVideoListFragment.newInstance(VIDEO_POST);
                replaceFragment(postVideoFragment, R.string.nav_video_post);
                break;
            case R.id.action_post_audio:
                PostAudioVideoListFragment postAudioFragment = PostAudioVideoListFragment.newInstance(AUDIO_POST);
                replaceFragment(postAudioFragment, R.string.nav_audio_post);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //super.onPrepareOptionsMenu(menu);

        if (POSTS_MENUES_VISIBLE == true){
            menu.findItem(R.id.action_post_all).setVisible(true);
            menu.findItem(R.id.action_post_video).setVisible(true);
            menu.findItem(R.id.action_post_audio).setVisible(true);
        }
        else {
            menu.findItem(R.id.action_post_all).setVisible(false);
            menu.findItem(R.id.action_post_video).setVisible(false);
            menu.findItem(R.id.action_post_audio).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_foreword:
                Intent aboutIntent = new Intent(getApplicationContext(), ForewordActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.nav_all_post:
                replaceFragment(postListFragment, R.string.nav_all_post);
                break;
            case R.id.nav_course:
                LessonTypeListFragment fragment = LessonTypeListFragment.newInstance();
                replaceFragment(fragment, R.string.nav_course);
                break;
            case R.id.nav_planning:
                TimeTableFragment timeTableFragment = TimeTableFragment.newInstance();
                replaceFragment(timeTableFragment, R.string.nav_planning);
                break;
            case R.id.nav_thinking:
                JokeStoryReflectListFragment reflectListfragment = JokeStoryReflectListFragment.newInstance(REFLECT);
                replaceFragment(reflectListfragment, R.string.nav_thinking);
                break;
            case R.id.nav_joke:
                JokeStoryReflectListFragment jokeListfragment = JokeStoryReflectListFragment.newInstance(JOKE);
                replaceFragment(jokeListfragment, R.string.nav_joke);
                break;
            case R.id.nav_story:
                JokeStoryReflectListFragment storyListFragment = JokeStoryReflectListFragment.newInstance(STORY);
                replaceFragment(storyListFragment, R.string.nav_story);
                break;
            /*case R.id.nav_video_post:
                PostAudioVideoListFragment postVideoFragment = PostAudioVideoListFragment.newInstance(VIDEO_POST);
                replaceFragment(postVideoFragment, R.string.nav_video_post);
                break;
            case R.id.nav_audio_post:
                PostAudioVideoListFragment postAudioFragment = PostAudioVideoListFragment.newInstance(AUDIO_POST);
                replaceFragment(postAudioFragment, R.string.nav_audio_post);
                break;*/
            case R.id.nav_multi_media:
                Intent multiMediaIntent = new Intent(getApplicationContext(), MultiMediaActivity.class);
                startActivity(multiMediaIntent);
                break;
            /*case R.id.nav_tv_program:
                MultiMediaArchiveListFragment tvProgramArchiveListFragment = MultiMediaArchiveListFragment.newInstance(TV_PROGRAM);
                replaceFragment(tvProgramArchiveListFragment, R.string.nav_tv_program);
                break;
            case R.id.nav_seminary:
                MultiMediaArchiveListFragment seminaryArchiveListFragment = MultiMediaArchiveListFragment.newInstance(SEMINARY);
                replaceFragment(seminaryArchiveListFragment, R.string.nav_seminary);
                break;*/
            case R.id.nav_shop:
                ShopFragment shopFragment = ShopFragment.newInstance("", "");
                replaceFragment(shopFragment, R.string.nav_shop);
                break;
            /*case R.id.nav_donate:
                DonateFragment donateFragment = DonateFragment.newInstance();
                replaceFragment(donateFragment, R.string.nav_donate);
                break;*/
            case R.id.nav_advertising:
                PublicityFragment publicityFragment = PublicityFragment.newInstance();
                replaceFragment(publicityFragment, R.string.nav_advertising);
                break;
            case R.id.nav_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                showAbout();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state

        // Always call the superclass so it can save the view hierarchy state
        //super.onSaveInstanceState(savedInstanceState);
    }*/

    private void replaceFragment(Fragment fragment, int title) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        //fragmentTransaction.setCustomAnimations()
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(getResources().getString(title));
        
        if (fragment instanceof PostListFragment || fragment instanceof PostAudioVideoListFragment){
            POSTS_MENUES_VISIBLE = true;
        }
        else {
            POSTS_MENUES_VISIBLE = false;
        }
        invalidateOptionsMenu();
    }

    protected void showAbout() {
        // Inflate the about message contents
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

        // When linking text, force to always use default color. This works
        // around a pressed color state bug.
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        int defaultColor = textView.getTextColors().getDefaultColor();
        textView.setTextColor(defaultColor);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.aksharam);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
    }
}
