package com.example.czareg.foodment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;
public class MainActivity extends AppCompatActivity {
    //foodmentDb = new Database(this,null, null, 1);
    EditText wzrostTxt, wagaTxt, wiekTxt;
    String strWzrost,strWaga,strWiek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        wzrostTxt=(EditText) findViewById(R.id.wzrost);
        wagaTxt=(EditText) findViewById(R.id.waga);
        wiekTxt=(EditText) findViewById(R.id.wiek);
        Bundle bundle = getIntent().getExtras();
        String filename = "config.txt";
        String filepath = "settings";
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
        if(myExternalFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(myExternalFile);
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(in));
                strWzrost=br.readLine();
                wzrostTxt.setText(strWzrost);
                strWaga=br.readLine();
                wagaTxt.setText(strWaga);
                strWiek=br.readLine();
                wiekTxt.setText(strWiek);

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final Button gotowe = (Button) findViewById(R.id.gotowe);
        gotowe.setEnabled(true);

        gotowe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    int wzr = Integer.parseInt(wzrostTxt.getText().toString());
                    int waga = Integer.parseInt(wagaTxt.getText().toString());
                    int wiek = Integer.parseInt(wiekTxt.getText().toString());
                    if (wzr >= 50 && wzr <= 230 && waga >= 30 && waga <= 300 && wiek >= 3 && wiek <= 100) {
                        goToMenu();
                    } else {
                        Toast.makeText(MainActivity.this, "Podaj poprawne dane!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Podaj dane!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void goToMenu() {

        Intent intent = new Intent(this, MenuGlowne.class);
        Bundle bundle = getIntent().getExtras();
        String filename = "config.txt";
        String filepath = "settings";
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
        try {
            FileOutputStream fos = new FileOutputStream(myExternalFile);

            intent.putExtra("wzrost", wzrostTxt.getText().toString());
            intent.putExtra("waga", wagaTxt.getText().toString());
            intent.putExtra("wiek", wiekTxt.getText().toString());

            fos.write(wzrostTxt.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(wagaTxt.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(wiekTxt.getText().toString().getBytes());

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startActivity(intent);

    }
}