package com.example.project;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoStreamDownload;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadAction implements Runnable {
    private final YoutubeDownloader downloader = new YoutubeDownloader();
    private final String id;
    private final String title;
    private final Activity activity;

    DownloadAction(String id, String title, Activity activity){
        this.id = id;
        this.title = title;
        this.activity = activity;
    }


    @Override
    public void run() {
        RequestVideoInfo request = new RequestVideoInfo(id);
        Response<VideoInfo> response = downloader.getVideoInfo(request);

        VideoInfo video = response.data();
        Format audio = video.bestAudioFormat(); //TODO: mb make this editable

        achievePermissions(); //TODO: Devil's toy

        Log.i("File", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + title + "." + audio.extension().value());

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + title + "." + audio.extension().value());
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            RequestVideoStreamDownload request2 = new RequestVideoStreamDownload(audio, fOut);
            Response<Void> response2 = downloader.downloadVideoStream(request2);
            fOut.close();
        } catch (IOException e) {
            Log.e("File", e.getMessage());
        }
        activity.runOnUiThread(() -> {
            Toast.makeText(activity.getApplicationContext(),
                    "Downloaded", Toast.LENGTH_SHORT).show();
        });

    }
    public void achievePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (checkSelfPermission(activity, Manifest.permission.MANAGE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                // You can use the API that requires the permission

            } else if (shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
                requestPermissions(activity,
                        new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                        12345);

            } else {
                // You can directly ask for the permission.
                requestPermissions(activity,
                        new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                        12345);
            }
        } else {

            if (checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                // You can use the API that requires the permission

            } else if (shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
                requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        12345);

            } else {
                // You can directly ask for the permission.
                requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        12345);
            }
        }
    }
}
