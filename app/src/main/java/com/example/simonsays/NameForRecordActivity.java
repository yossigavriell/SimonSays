package com.example.simonsays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class NameForRecordActivity extends AppCompatActivity {

   private EditText namePlayerText;
   private Button submitBtn;
   private TextView scoreLabelTV;
   private Animation myAnim;
   // Use bounce interpolator with amplitude 0.2 and frequency 20
   private MyBounceInterpolator interpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_for_record);


        submitBtn = findViewById(R.id.submit);
        namePlayerText = findViewById(R.id.player_name_from_edit_text);
        scoreLabelTV = findViewById(R.id.score_label);
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = namePlayerText.getText().toString();
                int score = getIntent().getIntExtra("score", 0);

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(NameForRecordActivity.this, getText(R.string.dialog_alert_main).toString(), Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(NameForRecordActivity.this, GameOverActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("score",score);
                    startActivity(intent);
                    finish();
                }

            }
        });

        int score = getIntent().getIntExtra("score", 0);
        scoreLabelTV.setText(score + "");
        // this is the key for the SP: String highScorePrefName = {name}_{boardType}_{difficult}_HIGH_SCORE

    }

    @Override
    public void onBackPressed()
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getText(R.string.menu_back).toString());
        alertDialogBuilder
                .setMessage(getText(R.string.brings_out).toString())
                .setCancelable(false)
                .setPositiveButton(getText(R.string.lo_rotze).toString(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent =new Intent(NameForRecordActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })

                .setNegativeButton(getText(R.string.rotze).toString(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

