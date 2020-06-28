package me.timgu.flashmemorize;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.ViewFlipper;

public class HelpVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_video);
        final VideoView videoView=(VideoView)findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.video1));
        videoView.start();

        Button butpre, butnext;
        final ViewFlipper viewFlipper;

        butpre=(Button)findViewById(R.id.btnpre);
        butnext=(Button)findViewById(R.id.btnnext);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewflipper);

        butpre.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               viewFlipper.showPrevious();
           }
        });

        butnext.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                viewFlipper.showNext();
            }
        });

    }
}