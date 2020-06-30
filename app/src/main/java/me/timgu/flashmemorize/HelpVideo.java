package me.timgu.flashmemorize;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.ViewFlipper;

public class HelpVideo extends AppCompatActivity {

    private SettingsManager settingsManager;
    VideoView video;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_video);

        settingsManager=new SettingsManager(this);

        Button butpre, butnext,butfin;
        final ViewFlipper viewFlipper;

        butpre=(Button)findViewById(R.id.btnpre);
        butnext=(Button)findViewById(R.id.btnnext);
        butfin=(Button)findViewById(R.id.btnfin);
        viewFlipper=(ViewFlipper)findViewById(R.id.viewflipper);
        video=(VideoView)findViewById(R.id.v2);
        video.setVideoURI(Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.guide01));


        butpre.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){

               i--;
               if(i>=0) {
                   viewFlipper.showPrevious();
               }
               if(i<0)i=0;

           }
        });

        butnext.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                i++;
                if(i<=5) {
                    viewFlipper.showNext();
                    if (i == 5) {
                        video.resume();
                        video.start();
                    }//video.stopPlayback();
                }

                if (i>5)i=5;
            }
        });

        butfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.stopFlipping();
                settingsManager.setFirstTime();
                video.stopPlayback();
                finish();

            }
        });

    }
}