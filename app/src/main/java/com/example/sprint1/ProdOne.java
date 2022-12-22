package com.example.sprint1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sprint1.DBHelper.DBFirebase;
import com.example.sprint1.Services.ProductService;
import com.squareup.picasso.Picasso;

public class ProdOne extends AppCompatActivity {

    //private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private ProductService productService;

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
        //dbHelper = new DBHelper(this);
        productService = new ProductService();

        Intent intentIn = getIntent();
        int price = Integer.parseInt(intentIn.getStringExtra("price"));
        String prices = "USD: $" + price + " COP: $" + price*4700;
        //String id = intentIn.getStringExtra("id");

        //ArrayList<Product> list = productService.cursorToArray(dbHelper.getDataById(id));
        //Product product = list.get(0);

        textProductTitle.setText(intentIn.getStringExtra("name"));
        textProductDescription.setText(intentIn.getStringExtra("description"));
        textProductPrice.setText(prices);
        Picasso.get().load(intentIn.getStringExtra("image")).into(imgProduct);

        /*
        Intent intentIn = getIntent();

        int codeImage = intentIn.getIntExtra("image", 0);
        imgProduct.setImageResource(codeImage);

         */



        btnProductInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
    }
}