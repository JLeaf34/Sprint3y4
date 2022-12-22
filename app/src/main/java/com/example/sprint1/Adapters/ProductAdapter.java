package com.example.sprint1.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sprint1.DBHelper.DBFirebase;
import com.example.sprint1.Entities.Product;
import com.example.sprint1.Home;
import com.example.sprint1.ProdOne;
import com.example.sprint1.ProductForm;
import com.example.sprint1.R;
import com.squareup.picasso.Picasso;

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

        ImageView imgProduct = (ImageView) view.findViewById(R.id.imageProductTemplate);
        TextView textNameProduct = (TextView) view.findViewById(R.id.textNameTemplate);
        TextView textDescriptionProduct = (TextView) view.findViewById(R.id.textDescriptionTemplate);
        TextView textPriceProduct = (TextView) view.findViewById(R.id.textPriceTemplate);
        ImageButton btnDelete = (ImageButton) view.findViewById(R.id.btnDeleteTemplate);
        ImageButton btnUpdate = (ImageButton) view.findViewById(R.id.btnUpdateTemplate);

        Button btnProductTemplate = (Button) view.findViewById(R.id.btnProductTemplate);

        Product product = arrayProducts.get(i);

        //byte[] image = product.getImage();
        //Bitmap bitmap  = BitmapFactory.decodeByteArray(image, 0, image.length );
        //imgProduct.setImageBitmap(bitmap);

        textNameProduct.setText(product.getName());
        textDescriptionProduct.setText(product.getDescription());
        int Col = product.getPrice() * 4800;
        int Usd = product.getPrice();
        String prices = "Pesos: $"+Col+" / "+ "USD: $"+Usd;
        textPriceProduct.setText(prices);

        //recibir imagen
        /*
        if(!TextUtils.isEmpty(product.getImage())){
            Picasso.get().load(product.getImage()).into(imgProduct);
        }else{
            imgProduct.setImageResource(R.drawable.broken1);
        }

         */

        Picasso.get().load(product.getImage()).into(imgProduct);

        btnProductTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProdOne.class);
                intent.putExtra("id", String.valueOf(product.getId()));
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", String.valueOf(product.getPrice()));
                intent.putExtra("image", product.getImage());
                context.startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductForm.class);
                intent.putExtra("edit", true);
                intent.putExtra("id", product.getId());
                intent.putExtra("name", product.getName());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("image", product.getImage());
                intent.putExtra("latitud", product.getLatitud());
                intent.putExtra("longitud", product.getLongitud());

                context.startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("¿Are you sure you want to delete this product?")
                        .setTitle("Confirm")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBFirebase dbFirebase = new DBFirebase();
                                dbFirebase.deleteData(product.getId());
                                Toast.makeText(context.getApplicationContext(), "Reload to see updates", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context.getApplicationContext(), Home.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });


        /*
        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProdOne.class);
                intent.putExtra("id", String.valueOf(product.getId()));
                context.startActivity(intent);
            }
        });

         */




        /*
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

         */

        return view;
    }
}
