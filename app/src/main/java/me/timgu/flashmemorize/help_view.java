package me.timgu.flashmemorize;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class help_view extends AppCompatActivity {

    private int[] list=new int[5];
    private boolean[] off =new boolean[5];

    Button btn01, btn02;
    //VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_view);

        /*videoView=(VideoView)findViewById(R.id.v);
        videoView.setVideoURI(Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.video1));
        videoView.start();
        videoView.setMediaController(new MediaController(this));*/

        final ImageView imageView1=findViewById(R.id.image1);
        TextView textView=findViewById(R.id.text1);

        btn01=(Button)findViewById(R.id.bt1);
        btn02=(Button)findViewById(R.id.bt2);
        //imageView1.setImageResource(R.drawable.unnamed);
        findViewById(R.id.image1).setVisibility(View.GONE);


        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(off[0]!=true){
                    off[0]=true;
                }else off[0]=false;
                change(list[0], off[0]);
            }
        });
    }

    private void change(int n,boolean q){
        if(q==true){
            switch(n){
                case 0:
                    findViewById(R.id.image1).setVisibility(View.VISIBLE);
                    findViewById(R.id.text1).setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }else if(q==false){
            switch(n){
                case 0:
                    findViewById(R.id.image1).setVisibility(View.GONE);
                    findViewById(R.id.text1).setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }
}