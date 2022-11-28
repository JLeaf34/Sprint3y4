package com.example.sprint1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProdOne extends AppCompatActivity {

    private Button btnProductInfo;
    private TextView textProductTitle, textProductDescription, textProductPrice;
    private ImageView imgProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_one);

        btnProductInfo = (Button) findViewById(R.id.btnProductInfo);
        textProductTitle = (TextView) findViewById(R.id.textProductTitle);
        textProductDescription = (TextView) findViewById(R.id.textProductDescription);
        textProductPrice = (TextView) findViewById(R.id.textProductPrice);
        imgProduct = (ImageView) findViewById(R.id.imgProduct);

        Intent intentIn = getIntent();
        textProductTitle.setText(intentIn.getStringExtra("title"));
        textProductDescription.setText(intentIn.getStringExtra("productDescription"));
        textProductPrice.setText(intentIn.getStringExtra("productPrice"));
        int codeImage = intentIn.getIntExtra("codeImage", 0);
        imgProduct.setImageResource(codeImage);



        btnProductInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
    }
}