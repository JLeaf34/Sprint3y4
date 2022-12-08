package com.example.sprint1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sprint1.Entities.Product;
import com.example.sprint1.ProdOne;
import com.example.sprint1.R;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> arrayProducts;

    public ProductAdapter(Context context, ArrayList<Product> arrayProducts) {
        this.context = context;
        this.arrayProducts = arrayProducts;
    }


    @Override
    public int getCount() {
        return arrayProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.product_template, null);

        Product product = arrayProducts.get(i);

        Button btnProductTemplate = (Button) view.findViewById(R.id.btnProductTemplate);
        ImageView imgProductTemplate = (ImageView) view.findViewById(R.id.imageProductTemplate);
        TextView textNameTemplate = (TextView) view.findViewById(R.id.textNameTemplate);
        TextView textDescriptionTemplate = (TextView) view.findViewById(R.id.textDescriptionTemplate);
        TextView textPriceTemplate = (TextView) view.findViewById(R.id.textPriceTemplate);

        imgProductTemplate.setImageResource(product.getImage());
        textNameTemplate.setText(product.getName());
        textDescriptionTemplate.setText(product.getDescription());
        textPriceTemplate.setText(String.valueOf(product.getPrice()));

        btnProductTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProdOne.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("image", product.getImage());
                context.startActivity(intent);
                //Toast.makeText(context.getApplicationContext(), "Presionado "+product.getName(), Toast.LENGTH_SHORT).show();
            }
        });


        /*
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.product_template, null);

        ImageView imgProduct = (ImageView) view.findViewById(R.id.imageProductTemplate);
        TextView textNameProduct = (TextView) view.findViewById(R.id.textNameTemplate);
        TextView textDescriptionProduct = (TextView) view.findViewById(R.id.textDescriptionTemplate);
        TextView textPriceProduct = (TextView) view.findViewById(R.id.textPriceTemplate);

        Product product = arrayProducts.get(i);

        textNameProduct.setText(product.getName());
        textDescriptionProduct.setText(product.getDescription());
        int Col = product.getPrice() * 5000;
        int Usd = product.getPrice();
        String prices = "Pesos: "+Col+" - "+ "USD: "+Usd;
        textPriceProduct.setText(prices);
        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProdOne.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", prices);
                intent.putExtra("image", product.getImage());
                context.startActivity(intent);
            }
        });

         */




        return view;
    }
}
