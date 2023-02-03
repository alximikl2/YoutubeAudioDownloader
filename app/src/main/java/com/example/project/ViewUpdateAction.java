package com.example.project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewUpdateAction {
    private final ArrayList<TextView> titleFields = StaticFields.getTitleFields();
    private final ArrayList<TextView> viewsFields = StaticFields.getViewsFields();
    private final ArrayList<TextView> channelsFields = StaticFields.getChannelsFields();
    private final ArrayList<ImageView> imagePlots = StaticFields.getImagePlots();
    private final ArrayList<Button> buttons = StaticFields.getButtons();

    private final ArrayList<String> ids = StaticFields.getIds();
    private final ArrayList<String> views = StaticFields.getViews();
    private final ArrayList<String> titles = StaticFields.getTitles();
    private final ArrayList<String> channels = StaticFields.getChannels();
    private final ArrayList<Bitmap> thumbs = StaticFields.getThumbs();

    private final Activity activity;
    private final int numberOfSections = StaticFields.getNumberOfSections();

    ViewUpdateAction(Activity activity){
        this.activity = activity;
    }

    public void viewUpdate(){
        activity.runOnUiThread(() -> {
            for(int i = 0; i < numberOfSections; i++) {
                TextView titleField = titleFields.get(i);
                TextView viewsField = viewsFields.get(i);
                TextView channelField = channelsFields.get(i);
                ImageView image = imagePlots.get(i);
                Button button = buttons.get(i);

                titleField.setText(titles.get(i));
                if(titleField.getVisibility() != View.VISIBLE) {titleField.setVisibility(View.VISIBLE);}

                viewsField.setText(views.get(i));
                if(viewsField.getVisibility() != View.VISIBLE) {viewsField.setVisibility(View.VISIBLE);}

                channelField.setText(channels.get(i));
                if(channelField.getVisibility() != View.VISIBLE) {channelField.setVisibility(View.VISIBLE);}

                image.setImageBitmap(thumbs.get(i));
                if(image.getVisibility() != View.VISIBLE) {image.setVisibility(View.VISIBLE);}

                int lambdaI = i;
                button.setOnClickListener(view1 -> {
                    try {
                        DownloadAction action = new DownloadAction(ids.get(lambdaI),
                                titles.get(lambdaI), activity);
                        Thread thread = new Thread(action);
                        thread.start();
                    } catch (Exception e) {
                        Log.e("Download Error", e.getMessage());
                        activity.runOnUiThread(() -> {
                            Toast.makeText(activity.getApplicationContext(), "Download Error",
                                    Toast.LENGTH_SHORT).show();
                        });
                    }
                });
                if(button.getVisibility() != View.VISIBLE) {button.setVisibility(View.VISIBLE);}
            }
        });
    }
}
