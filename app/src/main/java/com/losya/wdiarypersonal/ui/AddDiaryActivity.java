package com.losya.wdiarypersonal.ui;

import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.*;

//import com.losya.wdiarypersonal.R;
import com.losya.wdiarypersonal.R;
import com.losya.wdiarypersonal.db.DiaryDatabaseHelper;
import com.losya.wdiarypersonal.utils.AppManager;
import com.losya.wdiarypersonal.utils.GetDate;
import com.losya.wdiarypersonal.utils.StatusBarCompat;
import com.losya.wdiarypersonal.widget.LinedEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

/**
 */
public class AddDiaryActivity extends AppCompatActivity {

    @Bind(R.id.add_diary_tv_date)
    TextView mAddDiaryTvDate;
    @Bind(R.id.add_diary_et_title)
    EditText mAddDiaryEtTitle;
    @Bind(R.id.add_diary_et_content)
    LinedEditText mAddDiaryEtContent;
    @Bind(R.id.add_diary_fab_back)
    FloatingActionButton mAddDiaryFabBack;
    @Bind(R.id.add_diary_fab_add)
    FloatingActionButton mAddDiaryFabAdd;

    @Bind(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @Bind(R.id.common_tv_title)
    TextView mCommonTvTitle;
    @Bind(R.id.common_title_ll)
    LinearLayout mCommonTitleLl;
    @Bind(R.id.common_iv_back)
    ImageView mCommonIvBack;
    @Bind(R.id.common_iv_test)
    ImageView mCommonIvTest;

    private DiaryDatabaseHelper mHelper;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String title, String content) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String downloadType = SP.getString("downloadType","1");
        boolean themeType = (boolean) SP.getBoolean("btheme", Boolean.parseBoolean("false"));
        RelativeLayout view = (RelativeLayout) findViewById(R.id.main_rl_main);
        LinearLayout view3 = (LinearLayout) findViewById(R.id.numberPadLayout);
        if (themeType) {
            view.setBackgroundColor(Color.parseColor("#223344"));
            mAddDiaryTvDate.setTextColor(Color.parseColor("#d3d3d3"));
            view3.setBackgroundColor(Color.parseColor("#223344"));
//
        }
        else {
            mAddDiaryTvDate.setTextColor(Color.parseColor("#808080"));
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
            view3.setBackgroundColor(Color.parseColor("#FFFFFF"));
//
        }
        switch (downloadType) {
            case "1":
                mCommonTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                mAddDiaryTvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                mAddDiaryEtContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                break;
            case "2":
                mCommonTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,23);
                mAddDiaryTvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,19);
                mAddDiaryEtContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
                break;
            case "3":
                mCommonTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                mAddDiaryTvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,26);
                mAddDiaryEtContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
                break;
            default:
                mCommonTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                mAddDiaryTvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                mAddDiaryEtContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        }
        mAddDiaryEtTitle.setText(intent.getStringExtra("title"));
        StatusBarCompat.compat(this, Color.parseColor("#161414"));

        mCommonTvTitle.setText("Add note");
        mAddDiaryTvDate.setText("Today " + GetDate.getDate());
        mAddDiaryEtContent.setText(intent.getStringExtra("content"));
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
    }


    @OnClick({R.id.common_iv_back, R.id.add_diary_et_title, R.id.add_diary_et_content, R.id.add_diary_fab_back, R.id.add_diary_fab_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_iv_back:
                MainActivity.startActivity(this);
            case R.id.add_diary_et_title:
                break;
            case R.id.add_diary_et_content:
                break;
            case R.id.add_diary_fab_back:
                String date = GetDate.getDate().toString();
                String tag = String.valueOf(System.currentTimeMillis());
                String title = mAddDiaryEtTitle.getText().toString() + "";
                String content = mAddDiaryEtContent.getText().toString() + "";
                if (!title.equals("") || !content.equals("")) {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("date", date);
                    values.put("title", title);
                    values.put("content", content);
                    values.put("tag", tag);
                    db.insert("Diary", null, values);
                    values.clear();
                }
                MainActivity.startActivity(this);
                break;
            case R.id.add_diary_fab_add:
                final String dateBack = GetDate.getDate().toString();
                final String titleBack = mAddDiaryEtTitle.getText().toString();
                final String contentBack = mAddDiaryEtContent.getText().toString();
                if(!titleBack.isEmpty() || !contentBack.isEmpty()){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("Do you want to save?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("date", dateBack);
                            values.put("title", titleBack);
                            values.put("content", contentBack);
                            db.insert("Diary", null, values);
                            values.clear();
                            MainActivity.startActivity(AddDiaryActivity.this);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.startActivity(AddDiaryActivity.this);
                        }
                    }).show();
                }else{
                    MainActivity.startActivity(this);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.startActivity(this);
    }
}











