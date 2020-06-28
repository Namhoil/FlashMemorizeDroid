package me.timgu.flashmemorize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AddEditAlarmActivity extends AppCompatActivity {
    public static final String ALARM_EXTRA = "alarm_extra";
    public static final String MODE_EXTRA = "mode_extra";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EDIT_ALARM,ADD_ALARM,UNKNOWN})
    @interface Mode{}
    public static final int EDIT_ALARM = 1;
    public static final int ADD_ALARM = 2;
    public static final int UNKNOWN = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        //noinspection ConstantConditions
        setSupportActionBar((Toolbar) findViewById(R.id.edil_alarm_toolber));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getToolbarTitle()); // 모드를 식별하여 title을 결정

        final Alarm alarm = getAlarm();

        if(getSupportFragmentManager().findFragmentById(R.id.edit_alarm_frag_container) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.edit_alarm_frag_container, AddEditAlarmFragment.newInstance(alarm))
                    .commit();
        }

    }

    // 식별한 모드에 따라 알람에 관한 동작을 결정하는 메소드
    private Alarm getAlarm() {
        switch (getMode()) {
            case EDIT_ALARM:
                return getIntent().getParcelableExtra(ALARM_EXTRA);
            case ADD_ALARM:
                final long id = DatabaseHelper.getInstance(this).addAlarm();
                LoadAlarmsService.launchLoadAlarmsService(this);
                return new Alarm(id);
            case UNKNOWN:
            default:
                throw new IllegalStateException("Mode supplied as intent extra for " +
                        AddEditAlarmActivity.class.getSimpleName() + " must match value in " +
                        Mode.class.getSimpleName());
        }
    }

    // 알람 추가인지 알람 편집인지를 식별하기 위한 메소드
    private @Mode int getMode() {
        final @Mode int mode = getIntent().getIntExtra(MODE_EXTRA, UNKNOWN);
        return mode;
    }

    // 실별한 모드에 따라 Toolber의 title을 정하는 메소드
    private String getToolbarTitle() {
        int titleResId;
        switch (getMode()) {
            case EDIT_ALARM:
                titleResId = R.string.edit_alarm;
                break;
            case ADD_ALARM:
                titleResId = R.string.add_alarm;
                break;
            case UNKNOWN:
            default:
                throw new IllegalStateException("Mode supplied as intent extra for " +
                        AddEditAlarmActivity.class.getSimpleName() + " must match value in " +
                        Mode.class.getSimpleName());
        }
        return getString(titleResId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Intent buildAddEditAlarmActivityIntent(Context context, @Mode int mode) {
        final Intent i = new Intent(context, AddEditAlarmActivity.class);
        i.putExtra(MODE_EXTRA, mode);
        return i;
    }
}
