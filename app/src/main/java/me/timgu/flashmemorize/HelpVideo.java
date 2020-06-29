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

        butfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.stopFlipping();
                settingsManager.setFirstTime();
                finish();

            }
        });

    }
}