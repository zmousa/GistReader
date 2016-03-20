package com.zenus.gistreader.activity;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zenus.gistreader.R;
import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.controller.GistController;
import com.zenus.gistreader.dao.Gist;
import com.zenus.gistreader.task.GistListListener;
import com.zenus.gistreader.task.GistListTask;
import com.zenus.gistreader.task.ReplayCommandsService;
import com.zenus.gistreader.util.Utilities;

import java.util.HashMap;
import java.util.List;

public class GistListActivity extends ListActivity implements GistListListener, IActivity {
    private static final int PROGRESS_DIALOG = 1;
    private GistListAdapter adapter;
    private ProgressDialog mProgressDialog;
    private GistListTask gistListTask;
    private HashMap<String, Gist> syncedTasks;
    private List<Gist> gists;
    private String mAlertMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gist_list);

        setTitle(getString(R.string.title_activity_gists));
        mAlertMsg = getString(R.string.please_wait);

        gists = new GistController().getGists();
        adapter = new GistListAdapter(this, gists);

        getListView().setItemsCanFocus(false);
        setListAdapter(adapter);

        syncTasksList();
        syncCommands();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getApplicationContext(), GistDetailsActivity.class);
        Gist selectedGist = gists.get(position);
        intent.putExtra("id", selectedGist.getId());
        startActivity(intent);
    }

    private void syncTasksList() {
        syncedTasks = new HashMap();
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(getString(R.string.sync_gists));
        }
        showDialog(PROGRESS_DIALOG);

        gistListTask = new GistListTask();
        gistListTask.setTaskListener(this);
        gistListTask.execute();
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
                                // we use the same progress dialog for both
                                // so whatever isn't null is running
                                if (gistListTask != null) {
                                    gistListTask.setTaskListener(null);
                                    gistListTask.cancel(true);
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
    public void gistListComplete(HashMap<String, Gist> value) {
        dismissDialog(PROGRESS_DIALOG);
        gistListTask.setTaskListener(null);

        if (gistListTask.taskHasErrorMessage()){
            Utilities.toast(gistListTask.getTaskErrorMessage());
        } else {
            gists.clear();
            gists.addAll(value.values());
            adapter.notifyDataSetChanged();
        }
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

    public void syncCommands(){
        List<Gist> gists = GistApplication.getInstance().getDaoSession().getGistDao().loadAll();
        for (Gist gist : gists) {
            Intent serviceIntent = new Intent(this, ReplayCommandsService.class);
            serviceIntent.putExtra("gistId", gist.getId());
            startService(serviceIntent);
        }
    }
}