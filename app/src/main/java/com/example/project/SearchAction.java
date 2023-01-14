package com.example.project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestSearchResult;
import com.github.kiulian.downloader.model.search.SearchResult;
import com.github.kiulian.downloader.model.search.SearchResultVideoDetails;
import com.github.kiulian.downloader.model.search.field.TypeField;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchAction implements Runnable {
    private final YoutubeDownloader downloader = new YoutubeDownloader();
    private final View view;
    private final Activity activity;
    private final String search;
    private boolean isSearchSuccessful = false;
    private final int numberOfSections = StaticFields.getNumberOfSections();

    private final ArrayList<String> ids = new ArrayList<>();
    private final ArrayList<String> views = new ArrayList<>();
    private final ArrayList<String> titles = new ArrayList<>();
    private final ArrayList<String> channels = new ArrayList<>();
    private final ArrayList<Bitmap> thumbs = new ArrayList<>();

    SearchAction(View view, String search, Activity activity){
        this.view = view;
        this.search = search;
        this.activity = activity;
    }

    @Override
    public void run() {
        search();
        if(isSearchSuccessful){
            StaticFields.receiveData(ids, views, titles, channels, thumbs);
            ViewUpdateAction viewUpdate = new ViewUpdateAction(activity);
            viewUpdate.viewUpdate();
        }
        System.gc(); //clear in the end
    }
    private void search(){
        Log.i("Thread2", Thread.currentThread().getName());

        RequestSearchResult request = new RequestSearchResult(search)
                .type(TypeField.VIDEO);                // TODO: mb make this editable
                //.forceExactQuery(true);
                //.sortBy(SortField.RELEVANCE);
        SearchResult result;
        try {
            result = downloader.search(request).data();
        } catch (NumberFormatException e){
            //Toast.makeText(activity, "Too much results; Try to add more context", Toast.LENGTH_LONG).show();
            return;
        }
        if(result == null){return;} //Log out TODO: try catch at start
        List<SearchResultVideoDetails> videos = result.videos();

        for(int i = 0; i < numberOfSections; i++) {
            SearchResultVideoDetails videoDetails = videos.get(i);

            ids.add(videoDetails.videoId());
            views.add(videoDetails.viewCountText());
            channels.add(videoDetails.author());
            titles.add(videoDetails.title());

            try {
                InputStream is = new URL("https://i.ytimg.com/vi/" + videoDetails.videoId() + "/default.jpg")
                        .openStream();
                thumbs.add(new BitmapDrawable(view.getResources(), is)
                        .getBitmap()
                        .copy(Bitmap.Config.ARGB_8888, true));
            } catch (IOException e) {
                Log.e("Picture Download", e.getMessage());
            }

        }

        SearchResultVideoDetails videoDetails = videos.get(0);
        Log.i("Data", videoDetails.viewCountText());
        Log.i("Data", videoDetails.videoId());
        Log.i("Data", videoDetails.author());
        Log.i("Data", videoDetails.title());
        try {
            InputStream is = new URL("https://i.ytimg.com/vi/" + videoDetails.videoId() + "/default.jpg")
                    .openStream();
            Canvas cv = new Canvas(new BitmapDrawable(view.getResources(), is)
                    .getBitmap()
                    .copy(Bitmap.Config.ARGB_8888, true));
            Log.i("Data", "https://i.ytimg.com/vi/" + videoDetails.videoId() + "/default.jpg");
        } catch (IOException e) {
            Log.e("Picture Download", e.getMessage());
        }
        isSearchSuccessful = ids.size() == numberOfSections && views.size() == numberOfSections
                && channels.size() == numberOfSections && titles.size() == numberOfSections
                && thumbs.size() == numberOfSections;
    }
}
