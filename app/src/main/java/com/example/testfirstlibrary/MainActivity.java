package com.example.testfirstlibrary;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Bundle argument;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    Path cache_file;
    Path per_file;
    String fileName;
    String per_fileName;
    String current_position;
    String duration;
    String files_dir;
    String selected_item;
    ProgressBar progressBar;
    ImageButton save_file;
    TextView record_mode;
    Button button1;
    Button button2;
    Button button4;
    Button button5;
    Chronometer timer_mic;
    TextView timer_play;
    TextView play_duration;
    Handler handler;
    int permission1;
    int permission2;
    int request_answer1;
    ListView records_list_show;
    String[] files_array;
    ArrayList<String> array_for_adapter = new ArrayList<>();
    ArrayAdapter<String> file_list_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission1 == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, request_answer1);
        }

        getWindow().setStatusBarColor(getColor(R.color.teal_700));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(35, 174, 163)));

        fileName = getExternalCacheDir().getAbsolutePath() + "/record.mp3";
        File cacheDelete = new File(fileName);
        if (cacheDelete.exists()) {
            cacheDelete.delete();
        }
        per_fileName = getExternalFilesDir("/").getAbsolutePath() + "/record.mp3";
        progressBar = findViewById(R.id.progressBar);
        cache_file = Paths.get(fileName);
        per_file = Paths.get(per_fileName);

        files_dir = getExternalFilesDir("/").getAbsolutePath();
        File dir_list = new File(files_dir);
        files_array = dir_list.list();
        for (int i = 0; i < files_array.length; i++) {
            array_for_adapter.add(files_array[i]);
        }


        handler = new Handler();
        record_mode = findViewById(R.id.record_mode);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        timer_mic = findViewById(R.id.timer_mic);
        timer_play = findViewById(R.id.timer_play);
        play_duration = findViewById(R.id.play_duration);
        button2.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        save_file = findViewById(R.id.save_file);
        save_file.setEnabled(false);
        save_file.setOnClickListener(v -> {
            DialogSaveFile save_dialog = new DialogSaveFile();
            save_dialog.show(getSupportFragmentManager(), "save");
        });

        records_list_show = findViewById(R.id.records_list_show);

        file_list_adapter = new ArrayAdapter(this, R.layout.list_item, R.id.arr_item, array_for_adapter);
        records_list_show.setAdapter(file_list_adapter);
        records_list_show.setOnItemClickListener((parent, view, position, id) -> {
            selected_item = array_for_adapter.get(position);
            argument = new Bundle();
            argument.putString("object", selected_item);
            ChoseActionDialog choseDialog = new ChoseActionDialog();
            choseDialog.setArguments(argument);
            choseDialog.show(getSupportFragmentManager(), "chose!");
        });


    }

    public void recordStart() {
        button1.setEnabled(false);
        button2.setEnabled(true);
        save_file.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        timer_mic.setBase(SystemClock.elapsedRealtime());
        timer_mic.start();
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
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR prepare", Toast.LENGTH_SHORT).show();
        }
        try {
            mediaRecorder.start();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR start", Toast.LENGTH_SHORT).show();
        }
        if (mediaRecorder != null) {
            Toast.makeText(getApplicationContext(), "Запись запущена", Toast.LENGTH_SHORT).show();
            record_mode.setText(R.string.mod_true);
        }

    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void recordStop(View view) {
        releasePlayer();
        mediaRecorder.stop();
        button1.setEnabled(true);
        button2.setEnabled(false);
        save_file.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        timer_mic.stop();
        record_mode.setText(R.string.mod_false);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressBar.setMax(mediaPlayer.getDuration());
        duration = Integer.toString(mediaPlayer.getDuration());
        play_duration.setText(duration.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration()), TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration()))));
        new Thread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 1000);
                current_position = Integer.toString(mediaPlayer.getCurrentPosition());
                timer_play.setText(current_position.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition()), TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition()))));
                if (mediaPlayer.getDuration() == mediaPlayer.getCurrentPosition()) {
                    progressBar.setProgress(0);
                    timer_play.setText("00:00");
                }
            }
        }).start();
    }


    public void playStart(View view) {
        button5.setEnabled(false);
        button4.setEnabled(true);
        mediaPlayer.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        releaseRecorder();
        Toast.makeText(getApplicationContext(), "App finished", Toast.LENGTH_SHORT).show();
    }


    public void Show_start(View view) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "example");

    }

    public void pause(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            button4.setEnabled(false);
            button5.setEnabled(true);
        }
    }

    public void saveToTheFiles() {
        try {
            Files.copy(cache_file, per_file, REPLACE_EXISTING);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "copying is failed", Toast.LENGTH_SHORT).show();
        }
        File newPerFile = new File(per_fileName);
        if (newPerFile.exists()) {
            Toast.makeText(getApplicationContext(), "File is saved", Toast.LENGTH_SHORT).show();
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss", Locale.getDefault());
            Date currentDate = new Date();
            File recordName = new File(getExternalFilesDir("/").getAbsolutePath(), format.format(currentDate) + ".mp3");
            newPerFile.renameTo(recordName);
            file_list_adapter.add(format.format(currentDate) + ".mp3");
            file_list_adapter.notifyDataSetChanged();
        }
    }

    public void deleteFileFromDir(String get_file_to_delete) {
        File deleteRecord = new File(files_dir + "/" + get_file_to_delete);
        deleteRecord.delete();
        if (!deleteRecord.exists()) {
            Toast.makeText(getApplicationContext(), "File deleted", Toast.LENGTH_SHORT).show();
            file_list_adapter.remove(get_file_to_delete);
            file_list_adapter.notifyDataSetChanged();
        }
    }

    public void sendFileFromDir(String get_file_to_send) {
        File recordToSend = new File(files_dir + "/" + get_file_to_send);
        Uri resUri = FileProvider.getUriForFile(this, "com.example.testfirstlibrary", recordToSend);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("audio/*");
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.putExtra(Intent.EXTRA_STREAM, resUri);
        startActivity(Intent.createChooser(share, "Share File"));

    }

}
