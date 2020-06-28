package me.timgu.flashmemorize;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MainListAdapter.OnListActionListener,
        NewDeckDialogueFragment.NewDeckDialogueListener {
    private static final int READ_REQUEST_CODE = 6936;
    public static final String EXTRA_FILENAME =
            "me.timgu.flashmemorize.extra.FILENAME";
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =
            66;
    public static final int MERGE_LIST_REQUEST_CODE = 1313;

    //Declare RecyclerView
    private RecyclerView mRecyclerView;
    private MainListAdapter mAdapter;
    //Declare progress bar
    private ProgressBar pBar;
    //Declare helper classes
    private LocalDecksManager mDecksManager;
    private SettingsManager mSettingsManager;
    //Declare reference constants
    private boolean editMode = true;

    // 검색할 단어를 EditText UI에서부터 읽어오기 위한 변수
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = (EditText) findViewById(R.id.editSearch);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String stext = searchText.getText().toString();
                mAdapter.searchDeckList(stext);
            }
        });

        //initialize helper classes
        mSettingsManager = new SettingsManager(this);
        mDecksManager = new LocalDecksManager(this);

        //initiate task bar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //initiate recycler view
        mRecyclerView = findViewById(R.id.main_recyclerview);
        mAdapter = new MainListAdapter(this,mDecksManager.getDeckList().getAll());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initiate progress bar
        pBar = findViewById(R.id.main_progressBar);
        pBar.setVisibility(View.GONE);

        //initialization procedures

        check_n_load_tutorial();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mAdapter.flashcard_launched = false;
        mAdapter.updateDeckList();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    private Context getContext(){
        return this;
    }


    public void performFileSearch(View v){
        requestPermission();
        Intent  intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }


    private class AddDeckTask extends AsyncTask<Uri,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }

        protected Void doInBackground(Uri...uri){

            for (Uri u: uri){
                try {
                    mDecksManager.addDeck(u);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Void v){
            pBar.setVisibility(View.GONE);
            mAdapter.updateDeckList();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                //processing the uri to file
                String deckName = mDecksManager.getDeckName(uri);

                if (!(deckName.substring(deckName.length() - 4).equals(".txt") ||
                        deckName.substring(deckName.length() - 5).equals(".json") ||
                        deckName.substring(deckName.length() - 4).equals(".zip"))) {
                    Toast.makeText(this, "File Format Not Supported", Toast.LENGTH_SHORT).show();
                    return;
                }

                new AddDeckTask().execute(uri);
                //if (filename != null) {
                //launchflashcard(filename);
                //}
            }
        } else if (requestCode == MERGE_LIST_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<String> list = resultData.getStringArrayListExtra("Deck_List");
            String deckName = resultData.getStringExtra("Deck_Name");
            new MergeDeckTask(list, deckName).execute();
        }
    }

    private class MergeDeckTask extends AsyncTask<String,Void,Void>{
        String mDeckName;
        List<String> mList;

        MergeDeckTask(List<String> list, String deckName){
            mDeckName = deckName;
            mList = list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... lists) {
            try {
                mDecksManager.mergeDecks(mList,mDeckName);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pBar.setVisibility(View.GONE);
            mAdapter.updateDeckList();
        }
    }
    private class LaunchDeckTask extends AsyncTask<String,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            for (String filename: strings){
                Intent intent = new Intent(getContext(), FlashcardActivity.class);
                intent.putExtra(EXTRA_FILENAME,filename);
                startActivity(intent);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pBar.setVisibility(View.GONE);
        }
    }


    public void editDeckList(MenuItem item) {
        mAdapter.setEditMode(editMode);
        editMode = !editMode;
    }

    public void exportAdk(MenuItem item) {
        mAdapter.setExportMode(true);
        Toast.makeText(this, "Choose a deck to export...", Toast.LENGTH_SHORT).show();
    }

    public void newDeck(MenuItem item) {
        DialogFragment dialog = new NewDeckDialogueFragment();
        dialog.show(getSupportFragmentManager(),"NewDeckDialogue");
    }

    public void mergeDecks(MenuItem item) {
        Intent intent = new Intent(this, MergeListActivity.class);
        intent.putExtra("REQUEST_CODE",MainActivity.MERGE_LIST_REQUEST_CODE);
        startActivityForResult(intent,MainActivity.MERGE_LIST_REQUEST_CODE);
    }
    private class CreateNewDeck extends AsyncTask<String,Void,Void> {
        protected Void doInBackground(String...deckName){
            for (String s: deckName){
                try {
                    mDecksManager.newDeck(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Void v){
            mAdapter.updateDeckList();
        }
    }

    @Override
    public void onNewDeckDialogPositiveClick(DialogFragment dialog, String msg) throws IOException {
        new CreateNewDeck().execute(msg);
        dialog.dismiss();
    }
    @Override
    public void onNewDeckDialogNegativeClick(DialogFragment dialog){
        dialog.dismiss();
    }

    public void renameDeck(MenuItem item) {
        mAdapter.setRenameMode(! mAdapter.getRenameMode());
    }

    public void help1(MenuItem item){

        Intent intent= new Intent(getContext(),HelpVideo.class);
        startActivity(intent);


        /*Intent intent = new Intent(getContext(), ImageViewActivity.class);
        InputStream inputStream= null;
        try {
            inputStream = getAssets().open("image01.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap pic=BitmapFactory.decodeStream(inputStream);
        LocalDecksManager ldm = new LocalDecksManager(getContext());//ファイルネームを与えるためにオブジェクト生成
        String filename = ldm.saveImageToCache(pic);//画像ファイルデータを取り出す
        intent.putExtra("image",filename);
        getContext().startActivity(intent);*/
    }




    @Override
    public void launchDeck(String filename) {
        new LaunchDeckTask().execute(filename);
    }

    @Override
    public void deleteDeck(String deckName) {
        mDecksManager.removeDeck(deckName);
    }

    private void requestPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    private void check_n_load_tutorial(){
        if (mSettingsManager.getFirstTime()){
            mDecksManager.LoadTutorialDeck();
            mSettingsManager.setFirstTime();
        }
    }

    public void LaunchSettings(MenuItem item) {
        Intent intent = new Intent (getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    public void notiSettings(MenuItem item) {
        Intent intent = new Intent (getContext(), NotiMainActivity.class);
        startActivity(intent);

    }
}


