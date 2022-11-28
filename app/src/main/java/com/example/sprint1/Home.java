package com.example.sprint1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private Button btnProd1, btnProd2, btnProd3;
    private TextView textProduct1, textProduct2, textProduct3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnProd1 = (Button) findViewById(R.id.btnProd1);
        btnProd2 = (Button) findViewById(R.id.btnProd2);
        btnProd3 = (Button) findViewById(R.id.btnProd3);

        textProduct1 = (TextView) findViewById(R.id.textProduct1);
        textProduct2 = (TextView) findViewById(R.id.textProduct2);
        textProduct3 = (TextView) findViewById(R.id.textProduct3);

        btnProd1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ProdOne.class);
                intent.putExtra("title", textProduct1.getText().toString());
                intent.putExtra("codeImage", R.drawable.batman_thelonghalloween);
                startActivity(intent);
            }
        });

        btnProd2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ProdOne.class);
                intent.putExtra("title", textProduct2.getText().toString());
                intent.putExtra("codeImage", R.drawable.avengers_vol_7_1);
                startActivity(intent);
            }
        });

        btnProd3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ProdOne.class);
                intent.putExtra("title", textProduct3.getText().toString());
                intent.putExtra("codeImage", R.drawable.watchmen_10);
                startActivity(intent);
            }
        });
    }
}