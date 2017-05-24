package com.ultimate.chatgroupmahasiswa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button buttonEnter;
    private EditText nimText;
    private EditText namaText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonEnter = (Button) findViewById(R.id.setSettings);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nimText = (EditText) findViewById(R.id.nimSettings);
                namaText = (EditText) findViewById(R.id.namaSettings);
                String nimMhs = nimText.getText().toString();
                String namaMhs = namaText.getText().toString();
                if (nimMhs.isEmpty() || namaMhs.isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence text = "NIM dan Nama Harus Di Isi!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences("ChatServicesGroup",MODE_PRIVATE);
                    SharedPreferences.Editor editSharedPreferences = sharedPreferences.edit();
                    editSharedPreferences.putString("nama",namaMhs);
                    editSharedPreferences.putString("nim",nimMhs);
                    editSharedPreferences.commit();
                    Intent i = new Intent(getApplicationContext(), MainChoice.class);
                    startActivity(i);
                }
            }
        });
    }
}
