package com.worm2fed.password_generator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
    EditText pass_field;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pass_field = (EditText) findViewById(R.id.pass_field);

        final Button button_low = (Button) findViewById(R.id.button_low);
        final Button button_medium = (Button) findViewById(R.id.button_medium);
        final Button button_strong = (Button) findViewById(R.id.button_strong);

        button_low.setOnClickListener(this);
        button_medium.setOnClickListener(this);
        button_strong.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_low: generatePassword(0); break;
            case R.id.button_medium: generatePassword(1); break;
            case R.id.button_strong: generatePassword(2); break;
        }
    }

    private void generatePassword(int type) {
        String chars;
        String pass = "";
        int length;
        int clen;

        switch (type) {
            case 0:
                chars = "abcdefghijklmnopqrstuvwxyz0123456789";
                length = 6;
                break;
            case 1:
                chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPRQSTUVWXYZ0123456789";
                length = 9;
                break;
            case 2:
                chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHI_!@#$%^&*()-=+[]{}JKLMNOPRQSTUVWXYZ0123456789";
                length = 12;
                break;
            default:
                chars = "abcdefghijklmnopqrstuvwxyz0123456789";
                length = 6;
                break;
        }
        char[] chars_mas = chars.toCharArray();
        clen = chars.length() - 1;
        while (pass.length() < length) {
            int i = (int) (Math.random() * clen);
            pass += chars_mas[i];
        }

        pass_field.setText(pass);
    }
}
