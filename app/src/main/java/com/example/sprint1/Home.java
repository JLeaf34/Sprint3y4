package com.example.sprint1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.sprint1.Adapters.ProductAdapter;
import com.example.sprint1.DBHelper.DBFirebase;
import com.example.sprint1.Entities.Product;
import com.example.sprint1.Services.ProductService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    //private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private ProductService productService;
    private ListView listViewProducts;
    private ArrayList<Product> arrayProducts;
    private ProductAdapter productAdapter;

    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        arrayProducts = new ArrayList<>();
        dbFirebase = new DBFirebase();
        productService = new ProductService();
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);

        productAdapter = new ProductAdapter(this, arrayProducts);
        listViewProducts.setAdapter(productAdapter);

        dbFirebase.getData(productAdapter, arrayProducts);

        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductForm.class);
                startActivity(intent);
            }
        });

        /*
        Product product1 = new Product(R.drawable.batman_thelonghalloween, "Batman: The Long Halloween (1996-97)", "The acclaimed series " +
                "by writer Joseph Loeb and art by Tim Sale. " +
                "As the calendar's days stack up, so do the bodies littered in the streets of Gotham City. " +
                "A murderer is loose, killing only on holidays. The only man that can stop this fiend? The Dark Knight. " +
                "A mystery taking place during Batman's early days of crime fighting.", 340000);

        Product product2 = new Product(R.drawable.avengers_vol_7_1, "Avengers Vol 7-1 (2017)", "Written by Mark Waid and art by Michael Del Mundo. " +
                "Following the shattering after Civil War, the Avengers don't get a breather, for it is time for the Kang War!", 75000);

        Product product3 = new Product(R.drawable.watchmen_10, "Watchmen: Vol 10-1 (1987)", "Written by the great Alan Moore and art by Dave Gibbons. " +
                "The nation is going into DEFCON 2 and total war is closer than ever before. Will someone, anyone, be able to do something?", 500000);
        Product product4 = new Product(R.drawable.broken1, "Comic 4", "Useless Description", 20000);
        Product product5 = new Product(R.drawable.broken1, "Comic 5", "Useless Description", 40000);
        Product product6 = new Product(R.drawable.broken1, "Comic 6", "Useless Description", 60000);

        arrayProducts.add(product1);
        arrayProducts.add(product2);
        arrayProducts.add(product3);
        arrayProducts.add(product4);
        arrayProducts.add(product5);
        arrayProducts.add(product6);

        productAdapter = new ProductAdapter(this, arrayProducts);

        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        listViewProducts.setAdapter(productAdapter);

         */



        /*
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
                intent.putExtra("productDescription", "The acclaimed series " +
                        "by writer Joseph Loeb and art by Tim Sale. " +
                        "As the calendar's days stack up, so do the bodies littered in the streets of Gotham City. " +
                        "A murderer is loose, killing only on holidays. The only man that can stop this fiend? The Dark Knight. " +
                        "A mystery taking place during Batman's early days of crime fighting.");
                intent.putExtra("productPrice", "$340.000 COP");
                startActivity(intent);
            }
        });

        btnProd2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ProdOne.class);
                intent.putExtra("title", textProduct2.getText().toString());
                intent.putExtra("codeImage", R.drawable.avengers_vol_7_1);
                intent.putExtra("productDescription", "Written by Mark Waid and art by Michael Del Mundo. " +
                        "Following the shattering after Civil War, the Avengers don't get a breather, for it is time for the Kang War!");
                intent.putExtra("productPrice", "$75.000 COP");
                startActivity(intent);
            }
        });

        btnProd3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ProdOne.class);
                intent.putExtra("title", textProduct3.getText().toString());
                intent.putExtra("codeImage", R.drawable.watchmen_10);
                intent.putExtra("productDescription", "Written by the great Alan Moore and art by Dave Gibbons. " +
                        "The nation is going into DEFCON 2 and total war is closer than ever before. Will someone, anyone, be able to do something?");
                intent.putExtra("productPrice", "$500.000 COP (version signed by Alan Moore)");
                startActivity(intent);
            }
        });


         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionAdd:
                Intent intent = new Intent(getApplicationContext(), ProductForm.class);
                startActivity(intent);
                return true;

            case R.id.itemMap:
                intent = new Intent(getApplicationContext(), Maps.class);
                ArrayList<String> latitudes = new ArrayList<>();
                ArrayList<String> longitudes = new ArrayList<>();

                for(int i=0; i<arrayProducts.size(); i++){
                    latitudes.add(String.valueOf(arrayProducts.get(i).getLatitud()));
                    longitudes.add(String.valueOf(arrayProducts.get(i).getLongitud()));
                }
                intent.putStringArrayListExtra("latitudes", latitudes);
                intent.putStringArrayListExtra("longitudes", longitudes);
                startActivity(intent);
                return true;

            case R.id.refresh:
                Intent intentOn = new Intent(getApplicationContext(), Home.class);
                startActivity(intentOn);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}