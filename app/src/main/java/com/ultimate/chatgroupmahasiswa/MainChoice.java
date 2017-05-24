package com.ultimate.chatgroupmahasiswa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainChoice extends AppCompatActivity {
    private Button job;
    private Button lounge;
    private Button alumni;
    private Button kelas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choice);
        job = (Button) findViewById(R.id.buttonJobVac);
        alumni = (Button) findViewById(R.id.buttonAlumni);
        kelas = (Button) findViewById(R.id.buttonClass);
        lounge = (Button) findViewById(R.id.buttonLounge);
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="job";
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                i.putExtra("value",status);
                startActivity(i);
            }
        });
        alumni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="alumni";
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                i.putExtra("value",status);
                startActivity(i);
            }
        });
        kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "kelas";
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                i.putExtra("value",status);
                startActivity(i);
            }
        });
        lounge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "lounge";
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                i.putExtra("value",status);
                startActivity(i);
            }
        });


    }
}
