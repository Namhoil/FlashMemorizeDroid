package me.timgu.flashmemorize;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class HelpVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_video);
        //final VideoView videoView=(VideoView)findViewById(R.id.videoView);
        //videoView.setVideoURI(Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.video1));
        //videoView.start();

    }
}