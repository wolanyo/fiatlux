package com.wolasoft.fiatlux.dao;

/**
 * Created by osiris on 15/05/16.
 */

import com.wolasoft.fiatlux.models.Book;
import com.wolasoft.fiatlux.models.CdDvd;
import com.wolasoft.fiatlux.models.Chapter;
import com.wolasoft.fiatlux.models.Help;
import com.wolasoft.fiatlux.models.JokeStoryReflect;
import com.wolasoft.fiatlux.models.Lesson;
import com.wolasoft.fiatlux.models.LessonType;
import com.wolasoft.fiatlux.models.MultiMediaArchive;
import com.wolasoft.fiatlux.models.Music;
import com.wolasoft.fiatlux.models.Post;
import com.wolasoft.fiatlux.models.Publicity;
import com.wolasoft.fiatlux.models.TimeTable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FiatLuxClient {

    @GET("posts/{section}")
    Call<List<Post>> listPost(@Path("section") String section);
    @GET("posts/{section}/{type}")
    Call<List<Post>> listMultiMediaPost(@Path("section") String section, @Path("type") String type);
    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") String id);

    @GET("lessontypes/{section}")
    Call<List<LessonType>> listLessonType(@Path("section") String section);

    @GET("lessons/{lessontype}")
    Call<List<Lesson>> listLesson(@Path("lessontype") String id);

    @GET("lessons/{id}/chapter")
    Call<List<Chapter>> listChapter(@Path("id") String id);
    @GET("chapters/{chapter}")
    Call<Chapter> getChapterById(@Path("chapter") String id);

    @GET("books")
    Call<List<Book>> listBook();
    @GET("books/{book}")
    Call<Book> getBookById(@Path("book") String id);

    @GET("cddvds/{cddvdtype}")
    Call<List<CdDvd>> listCdDvd(@Path("cddvdtype") String id);
    @GET("cddvds/{cddvd}")
    Call<CdDvd> getCdDvdById(@Path("cddvd") String id);

    @GET("jokesstoriesreflects/{type}")
    Call<List<JokeStoryReflect>> listJokeStoryReflect(@Path("type") String id);
    @GET("jokesstoriesreflects/{id}")
    Call<JokeStoryReflect> getJokeStoryReflectById(@Path("id") String id);

    /*@GET("stories/")
    Call<List<StrangeStory>> listStory();
    @GET("stories/{storie}")
    Call<StrangeStory> getStoryById(@Path("storie") String id);*/

    @GET("multimediaarchives/{section}/{type}")
    Call<List<MultiMediaArchive>> listMultiMediaArchive(@Path("section") String section, @Path("type") String type);
    @GET("multimediaarchives/{archive}")
    Call<MultiMediaArchive> getMultiMediaArchiveById(@Path("archive") String id);

    @GET("publicities")
    Call<List<Publicity>> listPublicity();
    @GET("publicities/{publicity}")
    Call<Publicity> getPublicityById(@Path("publicity") String id);

    @GET("helps")
    Call<List<Help>> listHelps();
    @GET("helps/{help}")
    Call<Help> getHelpById(@Path("help") String id);

    @GET("timetables")
    Call<List<TimeTable>> listTimeTable();
    @GET("timetables/{timetable}")
    Call<TimeTable> getTimeTableById(@Path("timetable") String id);

    @GET("musics")
    Call<List<Music>> listMusics();
    @GET("musics/{music}")
    Call<Music> getMusicById(@Path("music") String id);

}
