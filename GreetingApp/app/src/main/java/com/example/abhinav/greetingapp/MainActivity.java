package com.example.abhinav.greetingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText fn;
    private EditText ln;
    private TextView tvResult;
    public void greetUser(View view) {
        Log.i(TAG, "inside greet user method ");
        String FirstName = "";
        String LastName = "";
        try {
            FirstName = fn.getText().toString();
            LastName = ln.getText().toString();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Enter the character", Toast.LENGTH_LONG).show();

        }
        //tvResult.setText("" + FirstName + "" + LastName);
        Log.i(TAG,FirstName + "" + LastName );
        Toast.makeText(getBaseContext(),FirstName + "" + LastName,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fn = (EditText)findViewById(R.id.etfn);
        ln = (EditText)findViewById(R.id.etln);
        tvResult = (TextView)findViewById(R.id.tvResult);
    }
}
