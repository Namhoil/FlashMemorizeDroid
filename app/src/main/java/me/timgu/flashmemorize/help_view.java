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

    private int[] list={0,1,2,3,4,5,6};
    private boolean[] off =new boolean[7];

    Button btn01, btn02,btn03,btn04,btn05,btn06,btn07;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_view);

        videoView=(VideoView)findViewById(R.id.v1);
        videoView.setVideoURI(Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.help07));
        //videoView.start();
        videoView.setMediaController(new MediaController(this));

        final ImageView imageView1=findViewById(R.id.image1);
        final ImageView imageView2=findViewById(R.id.image2);
        final ImageView imageView3=findViewById(R.id.image3);
        final ImageView imageView4=findViewById(R.id.image4);
        final ImageView imageView5=findViewById(R.id.image5);
        final ImageView imageView6=findViewById(R.id.image6);
        TextView textView=findViewById(R.id.text1);
        TextView textView2=findViewById(R.id.text2);
        TextView textView3=findViewById(R.id.text3);
        TextView textView4=findViewById(R.id.text4);
        TextView textView5=findViewById(R.id.text5);
        TextView textView6=findViewById(R.id.text6);

        btn01=(Button)findViewById(R.id.bt1);
        btn02=(Button)findViewById(R.id.bt2);
        btn03=(Button)findViewById(R.id.bt3);
        btn04=(Button)findViewById(R.id.bt4);
        btn05=(Button)findViewById(R.id.bt5);
        btn06=(Button)findViewById(R.id.bt6);
        btn07=(Button)findViewById(R.id.bt7);

        findViewById(R.id.image1).setVisibility(View.GONE);
        findViewById(R.id.text1).setVisibility(View.GONE);
        findViewById(R.id.image2).setVisibility(View.GONE);
        findViewById(R.id.text2).setVisibility(View.GONE);
        findViewById(R.id.image3).setVisibility(View.GONE);
        findViewById(R.id.text3).setVisibility(View.GONE);
        findViewById(R.id.image4).setVisibility(View.GONE);
        findViewById(R.id.text4).setVisibility(View.GONE);
        findViewById(R.id.image5).setVisibility(View.GONE);
        findViewById(R.id.text5).setVisibility(View.GONE);
        findViewById(R.id.image6).setVisibility(View.GONE);
        findViewById(R.id.text6).setVisibility(View.GONE);
        findViewById(R.id.v1).setVisibility(View.GONE);


        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(off[0]!=true){
                    off[0]=true;
                }else off[0]=false;
                change(list[0], off[0]);
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(off[1]!=true){
                    off[1]=true;
                }else off[1]=false;
                change(list[1], off[1]);
            }
        });
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(off[2]!=true){
                    off[2]=true;
                }else off[2]=false;
                change(list[2], off[2]);
            }
        });
        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(off[3]!=true){
                    off[3]=true;
                }else off[3]=false;
                change(list[3], off[3]);
            }
        });
        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(off[4]!=true){
                    off[4]=true;
                }else off[4]=false;
                change(list[4], off[4]);
            }
        });
        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(off[5]!=true){
                    off[5]=true;
                }else off[5]=false;
                change(list[5], off[5]);
            }
        });
        btn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(off[6]!=true){
                    off[6]=true;
                }else off[6]=false;
                change(list[6], off[6]);
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
                case 1:
                    findViewById(R.id.image2).setVisibility(View.VISIBLE);
                    findViewById(R.id.text2).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    findViewById(R.id.image3).setVisibility(View.VISIBLE);
                    findViewById(R.id.text3).setVisibility(View.VISIBLE);
                    break;
                case 3:
                    findViewById(R.id.image4).setVisibility(View.VISIBLE);
                    findViewById(R.id.text4).setVisibility(View.VISIBLE);
                    break;
                case 4:
                    findViewById(R.id.image5).setVisibility(View.VISIBLE);
                    findViewById(R.id.text5).setVisibility(View.VISIBLE);
                    break;
                case 5:
                    findViewById(R.id.image6).setVisibility(View.VISIBLE);
                    findViewById(R.id.text6).setVisibility(View.VISIBLE);
                    break;
                case 6:
                    findViewById(R.id.v1).setVisibility(View.VISIBLE);
                    videoView.start();
                    break;
                default:
                    break;
            }
        }else if(!q){
            switch(n){
                case 0:
                    findViewById(R.id.image1).setVisibility(View.GONE);
                    findViewById(R.id.text1).setVisibility(View.GONE);
                    break;
                case 1:
                    findViewById(R.id.image2).setVisibility(View.GONE);
                    findViewById(R.id.text2).setVisibility(View.GONE);
                    break;
                case 2:
                    findViewById(R.id.image3).setVisibility(View.GONE);
                    findViewById(R.id.text3).setVisibility(View.GONE);
                    break;
                case 3:
                    findViewById(R.id.image4).setVisibility(View.GONE);
                    findViewById(R.id.text4).setVisibility(View.GONE);
                    break;
                case 4:
                    findViewById(R.id.image5).setVisibility(View.GONE);
                    findViewById(R.id.text5).setVisibility(View.GONE);
                    break;
                case 5:
                    findViewById(R.id.image6).setVisibility(View.GONE);
                    findViewById(R.id.text6).setVisibility(View.GONE);
                    break;
                case 6:
                    findViewById(R.id.v1).setVisibility(View.GONE);
                    videoView.stopPlayback();
                    break;
                default:
                    break;
            }
        }
    }
}