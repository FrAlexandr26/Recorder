package com.example.testfirstlibrary;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String fileName;
    String per_fileName;
    ProgressBar progressBar;
    ImageButton save_file;
    Handler handler;
    int permission1;
    int permission2;
    int request_answer1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission1 == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, request_answer1);
        }

        fileName = getExternalCacheDir().getAbsolutePath() + "/record.mp3";
        File cacheDelete = new File(fileName);
        if (cacheDelete.exists()) {
            cacheDelete.delete();
        }
        per_fileName = getExternalFilesDir("/").getAbsolutePath() + "/record.mp3";
        progressBar = findViewById(R.id.progressBar);
        Path cache_file = Paths.get(fileName);
        Path per_file = Paths.get(per_fileName);
        handler = new Handler();
        save_file = findViewById(R.id.save_file);
        save_file.setOnClickListener(v -> {
            try {
                Files.copy(cache_file, per_file, REPLACE_EXISTING);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "copying is failed", Toast.LENGTH_SHORT).show();
            }
            File newPerFile = new File(per_fileName);
            if (newPerFile.exists()){
                Toast.makeText(getApplicationContext(), "File is saved", Toast.LENGTH_SHORT).show();
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss", Locale.getDefault());
                Date currentDate = new Date();
                File recordName = new File(getExternalFilesDir("/").getAbsolutePath(),   format.format(currentDate) + ".mp3");
                newPerFile.renameTo(recordName);
            }
        });
    }

    public void recordStart() {
        releaseRecorder();
        try {
            mediaRecorder = new MediaRecorder();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR object", Toast.LENGTH_SHORT).show();
        }
        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR set audio source", Toast.LENGTH_SHORT).show();
        }
        try {
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR set output format", Toast.LENGTH_SHORT).show();
        }
        try {
            mediaRecorder.setOutputFile(fileName);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR set output file", Toast.LENGTH_SHORT).show();
        }
        try {
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR set encoder", Toast.LENGTH_SHORT).show();
        }
        try {
            mediaRecorder.prepare();
        }  catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR prepare", Toast.LENGTH_SHORT).show();
        }
        try {
            mediaRecorder.start();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR start", Toast.LENGTH_SHORT).show();
        }
        if (mediaRecorder != null) {
           Toast.makeText(getApplicationContext(), "Запись запущена", Toast.LENGTH_SHORT).show();
        }

    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void releasePlayer(){
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void recordStop(View view) {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            Toast.makeText(getApplicationContext(), "Запись остановлена", Toast.LENGTH_SHORT).show();
        }
    }

    public void playStart(View view) {
        try {
            releasePlayer();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
            progressBar.setMax(mediaPlayer.getDuration());
            Toast.makeText(getApplicationContext(), "Воспроизведение запущено", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                    if (mediaPlayer.getDuration() == mediaPlayer.getCurrentPosition()){
                        progressBar.setProgress(0);
                    }
                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void playStop(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            Toast.makeText(getApplicationContext(), "Воспроизведение остановлено", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        releaseRecorder();
        Toast.makeText(getApplicationContext(), "App finished", Toast.LENGTH_SHORT).show();
    }


    public void Showstart(View view) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(),"example");

    }

    public void pause(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            Toast.makeText(getApplicationContext(), "Воспроизведение приостановлено", Toast.LENGTH_SHORT).show();
        }
    }
}