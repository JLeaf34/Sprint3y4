package com.example.sprint1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprint1.DBHelper.DBFirebase;
import com.example.sprint1.Entities.Product;
import com.example.sprint1.Services.ProductService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProductForm extends AppCompatActivity {

    private ProductService productService;
    //private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private Button btnFormProduct, btnCancelForm;
    private EditText editNameFormProduct, editDescriptionFormProduct, editPriceFormProduct, editImageFormProduct;
    private ImageView imgFormProduct;
    private TextView textLatitudFormProduct, textLongitudFormProduct;
    private MapView map;
    private MapController mapController;
    ActivityResultLauncher<String> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);
        btnCancelForm = (Button) findViewById(R.id.btnCancel);
        editNameFormProduct = (EditText) findViewById(R.id.editNameFormProduct);
        editDescriptionFormProduct = (EditText) findViewById(R.id.editDescriptionFormProduct);
        editPriceFormProduct = (EditText) findViewById(R.id.editPriceFormProduct);
        editImageFormProduct = (EditText) findViewById(R.id.editImageFormProduct);
        imgFormProduct = (ImageView) findViewById(R.id.imgFormProduct);
        textLatitudFormProduct = (TextView) findViewById(R.id.textLatitudForm);
        textLongitudFormProduct = (TextView) findViewById(R.id.textLongitudForm);

        //btnGet = (Button) findViewById(R.id.btnGet);
        //btnDelete = (Button) findViewById(R.id.btnDelete);
        //btnUpdate = (Button) findViewById(R.id.btnUpdate);
        //editIdFormProduct = (EditText) findViewById(R.id.editIdFormProduct);
        //byte[] img = "".getBytes(StandardCharsets.UTF_8);

        try {
            productService = new ProductService();
            //dbHelper = new DBHelper(this);
            dbFirebase = new DBFirebase();
        }catch (Exception e){
            Log.e("DB", e.toString());
        }

        Intent intentIN = getIntent();
        Boolean edit = intentIN.getBooleanExtra("edit", false);

        map = (MapView) findViewById(R.id.mapForm);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        mapController = (MapController) map.getController();

        GeoPoint colombia = new GeoPoint(4.570868, -74.297333);


        mapController.setCenter(colombia);
        mapController.setZoom(12);
        map.setMultiTouchControls(true);

        if (edit) {
            btnFormProduct.setText("Update");
            editNameFormProduct.setText(intentIN.getStringExtra("name"));
            editDescriptionFormProduct.setText(intentIN.getStringExtra("description"));
            editPriceFormProduct.setText(String.valueOf(intentIN.getIntExtra("price", 0)));
            Picasso.get().load(intentIN.getStringExtra("image")).into(imgFormProduct);
            editImageFormProduct.setText(String.valueOf(intentIN.getStringExtra("image")));
            textLatitudFormProduct.setText(String.valueOf(intentIN.getDoubleExtra("latitud", 0.0)));
            textLongitudFormProduct.setText(String.valueOf(intentIN.getDoubleExtra("longitud", 0.0)));
            GeoPoint geoPoint = new GeoPoint(intentIN.getDoubleExtra("latitud", 0.0), intentIN.getDoubleExtra("longitud", 0.0));
            Marker marker = new Marker(map);
            marker.setPosition(geoPoint);
            map.getOverlays().add(marker);
        }

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                textLatitudFormProduct.setText(String.valueOf(p.getLatitude()));
                textLongitudFormProduct.setText(String.valueOf(p.getLongitude()));
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, mapEventsReceiver);
        map.getOverlays().add(mapEventsOverlay);

        /*
        content = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(result);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            imgFormProduct.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        imgFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.launch("image/*");
            }
        });

         */

        btnFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editNameFormProduct.getText().toString();
                String desc = editDescriptionFormProduct.getText().toString();
                String price = editPriceFormProduct.getText().toString();
                String image = editImageFormProduct.getText().toString();
                String lat = textLatitudFormProduct.getText().toString().trim();
                String lon = textLongitudFormProduct.getText().toString().trim();

                Product prod = new Product();
                prod.setName(name);
                prod.setDescription(desc);
                prod.setPrice(Integer.parseInt(price));
                prod.setImage(image);
                prod.setLatitud(Double.parseDouble(lat));
                prod.setLongitud(Double.parseDouble(lon));

                if(prod == null){
                    Log.e("error", new Gson().toJson(prod));
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);

                }

                if (edit) {
                    prod.setId(intentIN.getStringExtra("id"));
                    dbFirebase.updateData(prod);
                    Toast.makeText(ProductForm.this, "Reload to see the updates", Toast.LENGTH_LONG).show();
                } else {
                    //Log.d("resultado", prod.getId() + "/ " + prod.getName() + "/" + prod.getDescription() + "/" + prod.getPrice() + "/" + prod.getImage() + "/" + prod.getLatitud() + "/" + prod.getLongitud() + "/" + prod.getCreatedAt() + "/" + prod.getUpdatedAt() + "/" + prod.isDeleted());
                    //Log.e("error", new Gson().toJson(prod));
                    dbFirebase.insertData(prod);
                }

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);


            }
        });

        btnCancelForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });



        /*
        btnFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Product product = null;

                try {
                    Product product = new Product(
                            editNameFormProduct.getText().toString(),
                            editDescriptionFormProduct.getText().toString(),
                            Integer.parseInt(editPriceFormProduct.getText().toString()),
                            "abc",
                            Double.parseDouble(textLatitudFormProduct.getText().toString().trim()),
                            Double.parseDouble(textLongitudFormProduct.getText().toString().trim())
                    );
                    Log.d("resultado", product.getId() + "/ " + product.getName() + "/" + product.getDescription() + "/" + product.getPrice() + "/" + product.getImage() + "/" + product.getLatitud() + "/" + product.getLongitud() + "/" + product.getCreatedAt() + "/" + product.getUpdatedAt() + "/" + product.isDeleted());

                    if (edit) {
                        product.setId(intentIN.getStringExtra("id"));
                        dbFirebase.updateData(product);
                    } else {
                        dbFirebase.insertData(product);
                    }

                } catch (Exception e) {
                    Log.e("DB Insert", e.toString());
                    //Log.d("resultado", product.getId() + "/ " + product.getName() + "/" + product.getDescription() + "/" + product.getPrice() + "/" + product.getImage() + "/" + product.getLatitud() + "/" + product.getLongitud() + "/" + product.getCreatedAt() + "/" + product.getUpdatedAt() + "/" + product.isDeleted());

                }

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

         */

        /*

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if(id.compareTo("") != 0){
                    ArrayList<Product> list = productService.cursorToArray(dbHelper.getDataById(id));
                    list.add(dbFirebase.getDataById(id));

                    if(list.size() != 0){
                        Product product = list.get(0);
                        //imgFormProduct.setImageBitmap(productService.byteToBitmap(product.getImage()));
                        editNameFormProduct.setText(product.getName());
                        editDescriptionFormProduct.setText(product.getDescription());
                        editPriceFormProduct.setText(String.valueOf(product.getPrice()));
                    }else{
                        Toast.makeText(getApplicationContext(),"No existe",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Ingrese id",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if(id.compareTo("") != 0){
                    Log.d("DB",id);
                    //dbHelper.deleteDataById(id);
                    dbFirebase.deleteDataById(id);
                    clean();

                }else{
                    Toast.makeText(getApplicationContext(),"Ingrese id a eliminar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editIdFormProduct.getText().toString().trim();
                if(id.compareTo("") != 0){
                    dbFirebase.updateDataById(
                            id,
                            editNameFormProduct.getText().toString(),
                            editDescriptionFormProduct.getText().toString(),
                            editPriceFormProduct.getText().toString()
                            //productService.imageViewToByte(imgFormProduct)
                    );
                    clean();
                }

            }
        });
    }

         */
    }
        public void clean(){
            editNameFormProduct.setText("");
            editDescriptionFormProduct.setText("");
            editPriceFormProduct.setText("");
            imgFormProduct.setImageResource(R.drawable.broken1);
        }

    }


