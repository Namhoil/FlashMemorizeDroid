package me.timgu.flashmemorize;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MergeListActivity extends AppCompatActivity
    implements NewDeckDialogueFragment.NewDeckDialogueListener {
    private RecyclerView mRecyclerView;
    private MergeListAdapter mAdapter;
    private LocalDecksManager mLdm;
    private int mRequestCode;
    @Override

    interface MergeListActivityListener{
        public void onMergeListActivityCallBack(List<String> list) throws IOException;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mRequestCode = intent.getIntExtra("REQUEST_CODE", 0);
        setContentView(R.layout.activity_merge_list);
        mLdm = new LocalDecksManager(this);
        mRecyclerView = findViewById(R.id.mergelist_recyclerview);
        mAdapter = new MergeListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void confirm(View view) throws IOException {
        if (mRequestCode == MainActivity.MERGE_LIST_REQUEST_CODE) {
            DialogFragment dialog = new NewDeckDialogueFragment();
            dialog.show(getSupportFragmentManager(), "NewDeckDialogue");
        } else if (mRequestCode == FlashcardActivity.MERGE_LIST_REQUEST_CODE){
            MergeListActivityListener listener = (MergeListActivityListener) getParent();
            listener.onMergeListActivityCallBack(mAdapter.checkOutList());
        }
    }

    public void cancel(View view){
        this.finish();
    }

    @Override
    public void onNewDeckDialogPositiveClick(DialogFragment dialog, String msg) throws IOException {
        List<String> mergeList = mAdapter.checkOutList();
        mLdm.mergeDecks(mergeList,msg);
        dialog.dismiss();
        this.finish();
    }

    @Override
    public void onNewDeckDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
