package com.zenus.gistreader.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zenus.gistreader.R;
import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.controller.GistController;
import com.zenus.gistreader.dao.Gist;
import com.zenus.gistreader.task.DeleteGistListener;
import com.zenus.gistreader.task.DeleteGistTask;
import com.zenus.gistreader.task.GistViewListener;
import com.zenus.gistreader.task.GistViewTask;
import com.zenus.gistreader.task.StarGistListener;
import com.zenus.gistreader.task.StarGistTask;
import com.zenus.gistreader.task.UnStarGistTask;
import com.zenus.gistreader.util.Utilities;

public class GistDetailsActivity extends Activity implements GistViewListener, StarGistListener, DeleteGistListener, IActivity{
    private static final int PROGRESS_DIALOG = 1;
    private ProgressDialog mProgressDialog;
    private GistViewTask gistViewTask;
    private StarGistTask starGistTask;
    private UnStarGistTask unStarGistTask;
    private DeleteGistTask deleteGistTask;
    private String id;
    private Gist gist;
    private TextView responseText;
    private String mAlertMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gist_details);
        setTitle(getString(R.string.title_activity_gist_details));
        mAlertMsg = getString(R.string.please_wait);
        setTitleFromActivityLabel();
        id = getIntent().getExtras().getString("id");
        String description = getIntent().getExtras().getString("description");

        gist = GistApplication.getInstance().getDaoSession().getGistDao().load(id);

        TextView title = (TextView) findViewById(R.id.gist_title_text);
        title.setText(description);
        responseText = (TextView) findViewById(R.id.gist_response_text);

        Button starButton = (Button) findViewById(R.id.star_button);
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starGist();
            }
        });
        Button deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGist();
            }
        });

        if (gist != null){
            if (gist.getIsStared()) {
                starButton.setText(R.string.unstar);
            } else {
                starButton.setText(R.string.star);
            }
        }

        getGistDetails();
    }

    private void getGistDetails() {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(getString(R.string.sync_gists));
        }
        showDialog(PROGRESS_DIALOG);

        gistViewTask = new GistViewTask();
        gistViewTask.setTaskListener(this);
        gistViewTask.execute(id);
    }

    private void starGist() {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(getString(R.string.sync_gists));
        }
        showDialog(PROGRESS_DIALOG);

        if (gist != null){
            if (gist.getIsStared()) {
                unStarGistTask = new UnStarGistTask();
                unStarGistTask.setTaskListener(this);
                unStarGistTask.execute(id);
            } else {
                starGistTask = new StarGistTask();
                starGistTask.setTaskListener(this);
                starGistTask.execute(id);
            }
        }
    }

    private void deleteGist() {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(getString(R.string.sync_gists));
        }
        showDialog(PROGRESS_DIALOG);

        deleteGistTask = new DeleteGistTask();
        deleteGistTask.setTaskListener(this);
        deleteGistTask.execute(id);
    }

    @Override
    public void gistDetailsComplete(String value) {
        dismissDialog(PROGRESS_DIALOG);
        gistViewTask.setTaskListener(null);

        if (gistViewTask.taskHasErrorMessage()){
            Utilities.toast(gistViewTask.getTaskErrorMessage());
        }
        responseText.setText(value);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PROGRESS_DIALOG:
                mProgressDialog = new ProgressDialog(this/*, R.style.CustomAlertDialogStyle*/);
                DialogInterface.OnClickListener loadingButtonListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (gistViewTask != null) {
                                    gistViewTask.setTaskListener(null);
                                    gistViewTask.cancel(true);
                                }
                                if (starGistTask != null) {
                                    starGistTask.setTaskListener(null);
                                    starGistTask.cancel(true);
                                }
                                if (deleteGistTask != null) {
                                    deleteGistTask.setTaskListener(null);
                                    deleteGistTask.cancel(true);
                                }
                            }
                        };
                mProgressDialog.setTitle(getString(R.string.sync_gists));
                mProgressDialog.setMessage(mAlertMsg);
                mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setButton(getString(R.string.cancel), loadingButtonListener);
                return mProgressDialog;
        }
        return null;
    }

    @Override
    public void onClickHome(View v) {
        final Intent intent = new Intent(getApplicationContext(), GistListActivity.class);
        intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void setTitleFromActivityLabel() {
        TextView tv = (TextView) findViewById (R.id.title_text);
        if (tv != null) tv.setText (getTitle());
    }

    @Override
    public void gistStarComplete() {
        dismissDialog(PROGRESS_DIALOG);
        if (starGistTask != null) {
            starGistTask.setTaskListener(null);
            Utilities.toast("Stared");
        } else {
            unStarGistTask.setTaskListener(null);
            Utilities.toast("UnStared");
        }

        if (starGistTask.taskHasErrorMessage()){
            Utilities.toast(starGistTask.getTaskErrorMessage());
        }
        Intent intent = new Intent(getApplicationContext(), GistListActivity.class);
        startActivity(intent);
    }

    @Override
    public void gistDeleteComplete() {
        dismissDialog(PROGRESS_DIALOG);
        deleteGistTask.setTaskListener(null);

        if (deleteGistTask.taskHasErrorMessage()){
            Utilities.toast(deleteGistTask.getTaskErrorMessage());
        }
        Utilities.toast("Deleted");

        Intent intent = new Intent(getApplicationContext(), GistListActivity.class);
        startActivity(intent);
    }
}
