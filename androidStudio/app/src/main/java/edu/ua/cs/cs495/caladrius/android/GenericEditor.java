package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public abstract class GenericEditor extends AppCompatActivity {
    protected abstract Fragment makeFragment();

    @Override
    public void onBackPressed()
    {
        onCancelClick();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar tb = findViewById(R.id.editor_toolbar);
        tb.setTitle("tmp toolbar title"); //TODO give editors a real title
        tb.setNavigationIcon(R.drawable.ic_baseline_cancel_24px);
        //NOTE: gotta set the support action bar BEFORE setting the navigation on click listener
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClick();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.editor_container);

        if (fragment == null) {
            fragment = makeFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.editor_container, fragment);
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    protected void onCancel()
    {
        // NOP; subclass might want to do something though.
    }

    /**
     * Determines whether or not the user will be asked for confirmation before navigating
     * away without saving.
     *
     * @return false if the user should be forced to confirm their intentions before navigating away
     */
    protected boolean shouldConfirmCancel()
    {
        return true;
    }

    private void cancel()
    {
        onCancel();
        finish();
    }

    protected CharSequence getConfirmationTitle(Context cntxt)
    {
        return cntxt.getText(R.string.editor_confirmation_defaulttitle);
    }

    protected CharSequence getConfirmationMessage(Context cntxt)
    {
        return cntxt.getText(R.string.editor_confirmation_defaultmessage);
    }

    protected void onCancelClick()
    {
        if (!shouldConfirmCancel()) {
            cancel();
        }
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setTitle(getConfirmationTitle(this));
        adb.setMessage(getConfirmationMessage(this));
        adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel();
            }
        });
        adb.setNegativeButton(android.R.string.no, null);
        adb.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.editor_save:
                //TODO call abstract save function
                Log.i("TMP", "Totally saving this thing rn");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
