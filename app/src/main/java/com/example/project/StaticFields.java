package com.example.project;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class StaticFields {
    private static ArrayList<String> ids;
    private static ArrayList<String> views;
    private static ArrayList<String> titles;
    private static ArrayList<String> channels;
    private static ArrayList<Bitmap> thumbs;

    private static final ArrayList<TextView> titleFields = new ArrayList<>();
    private static final ArrayList<TextView> viewsFields = new ArrayList<>();
    private static final ArrayList<TextView> channelsFields = new ArrayList<>();
    private static final ArrayList<ImageView> imagePlots = new ArrayList<>();
    private static final ArrayList<Button> buttons = new ArrayList<>();

    private static boolean isDataCorrect = false;
    private static final int numberOfSections = 3; //TODO: mb make this editable

    public static ArrayList<String> getIds() {
        return ids;
    }

    public static ArrayList<String> getViews() {
        return views;
    }

    public static ArrayList<String> getTitles() {
        return titles;
    }

    public static ArrayList<String> getChannels() {
        return channels;
    }

    public static ArrayList<Bitmap> getThumbs() {
        return thumbs;
    }

    public static ArrayList<TextView> getTitleFields() {
        return titleFields;
    }

    public static ArrayList<TextView> getViewsFields() {
        return viewsFields;
    }

    public static ArrayList<TextView> getChannelsFields() {
        return channelsFields;
    }

    public static ArrayList<ImageView> getImagePlots() {
        return imagePlots;
    }

    public static ArrayList<Button> getButtons() {
        return buttons;
    }

    public static boolean isDataCorrect() {
        return isDataCorrect;
    }

    public static int getNumberOfSections() {
        return numberOfSections;
    }

    public static void onActivityCreate(View view) {
        titleFields.clear();
        viewsFields.clear();
        channelsFields.clear();
        imagePlots.clear();
        buttons.clear();

        titleFields.add(view.findViewById(R.id.title1));
        viewsFields.add(view.findViewById(R.id.views1));
        channelsFields.add(view.findViewById(R.id.channel1));
        imagePlots.add(view.findViewById(R.id.thumb1));
        buttons.add(view.findViewById(R.id.download1));

        titleFields.add(view.findViewById(R.id.title2));
        viewsFields.add(view.findViewById(R.id.views2));
        channelsFields.add(view.findViewById(R.id.channel2));
        imagePlots.add(view.findViewById(R.id.thumb2));
        buttons.add(view.findViewById(R.id.download2));

        titleFields.add(view.findViewById(R.id.title3));
        viewsFields.add(view.findViewById(R.id.views3));
        channelsFields.add(view.findViewById(R.id.channel3));
        imagePlots.add(view.findViewById(R.id.thumb3));
        buttons.add(view.findViewById(R.id.download3));
    }

    public static void receiveData(ArrayList<String> id, ArrayList<String> view,
                                   ArrayList<String> title, ArrayList<String> channel,
                                   ArrayList<Bitmap> thumb){
        ids = id;
        views = view;
        titles = title;
        channels = channel;
        thumbs = thumb;
        isDataCorrect = true;
    }
}
