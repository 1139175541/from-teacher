package com.jndx.csp.goodsprice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.jndx.csp.goodsprice.R;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class AddItemActivity extends AppCompatActivity {
    EditText editTextGoodsName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Bundle bundle=this.getIntent().getExtras();
        String name= bundle.getString("name");
        editTextGoodsName= (EditText) this.findViewById(R.id.edittext_goods_name);
        editTextGoodsName.setText(name);

        Button buttonAdd= (Button) this.findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new ButtonAddClick());
    }

    private class ButtonAddClick implements OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("name",String.valueOf(editTextGoodsName.getText()));
            Log.i("text", String.valueOf(editTextGoodsName.getText()));
            setResult(RESULT_OK,intent);
            AddItemActivity.this.finish();
        }
    }
}

