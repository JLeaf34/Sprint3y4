package com.example.sprint1.DBHelper;

import static android.content.ContentValues.TAG;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sprint1.Adapters.ProductAdapter;
import com.example.sprint1.Entities.Product;
import com.example.sprint1.Services.ProductService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebase {

    private FirebaseFirestore db;
    private ProductService productService;

    public DBFirebase() {

        this.db = FirebaseFirestore.getInstance();
        this.productService = new ProductService();
    }

    public void insertData(Product prod){
        // Create a new user with a first and last name
        Map<String, Object> product = new HashMap<>();
        product.put("id",prod.getId() );
        product.put("name", prod.getName());
        product.put("description", prod.getDescription());
        product.put("price", prod.getPrice());
        if(TextUtils.isEmpty(prod.getImage())){
            product.put("image", "https://i.ibb.co/ygvd9K6/broken-1.png");
        }else{
            product.put("image", prod.getImage());
        }
        product.put("deleted", prod.isDeleted());
        product.put("createdAt", prod.getCreatedAt());
        product.put("updatedAt", prod.getUpdatedAt());
        product.put("latitud", prod.getLatitud());
        product.put("longitud", prod.getLongitud());

        //Log.d("res dbfirebase", new Gson().toJson(product));

        // Add a new document with a generated ID
        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Log.d("res dbfirebase", new Gson().toJson(product));
                    }
                });
        //db.terminate();
    }

    public void getData(ProductAdapter productAdapter, ArrayList<Product> list){
        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Product product = null;
                                if (!Boolean.valueOf(document.getData().get("deleted").toString())) {
                                    product = new Product(
                                            document.getData().get("id").toString(),
                                            document.getData().get("name").toString(),
                                            document.getData().get("description").toString(),
                                            Integer.parseInt(document.getData().get("price").toString()),
                                            document.getData().get("image").toString(),
                                            Boolean.valueOf(document.getData().get("deleted").toString()),
                                            productService.stringToDate(document.getData().get("createdAt").toString()),
                                            productService.stringToDate(document.getData().get("updatedAt").toString()),
                                            Double.parseDouble(document.getData().get("latitud").toString()),
                                            Double.parseDouble(document.getData().get("longitud").toString())
                                    );


                                /*
                                Product product = new Product(
                                        document.getData().get("name").toString(),
                                        document.getData().get("description").toString(),
                                        Integer.parseInt(document.getData().get("price").toString()),
                                        document.getData().get("image").toString()
                                );
                                 */
                                    list.add(product);
                                }
                            }
                            productAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /*
    public Product getDataById(String id){
        final Product[] product = {null};
        db.collection("products")
                //.whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if(document.getData().get("id").toString().compareTo(id) == 0){
                                    product[0] = new Product(

                                            document.getData().get("name").toString(),
                                            document.getData().get("description").toString(),
                                            Integer.parseInt(document.getData().get("price").toString())
                                    );
                                    ;
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return product[0];
    }

    public void updateDataById(String id, String name, String description, String price){
        db.collection("products")
                .document(id)
                .update(
                        "name",name,
                        "description",description,
                        "price", price)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //
                    }
                });
    }

    public void deleteDataById(String id){
        db.collection("products")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //
                    }
                });
    }

     */

    public void deleteData(String id){
        db.collection("products").whereEqualTo("id",id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                documentSnapshot.getReference().delete();
                            }
                        }
                    }
                });
    }

    public void updateData(Product producto){
        if(TextUtils.isEmpty(producto.getImage())){
            producto.setImage("https://i.ibb.co/ygvd9K6/broken-1.png");
        }
        db.collection("products").whereEqualTo("id", producto.getId())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                documentSnapshot.getReference().update(
                                        "name", producto.getName(),
                                        "description", producto.getDescription(),
                                        "price", producto.getPrice(),
                                        "image", producto.getImage(),
                                        "latitud", producto.getLatitud(),
                                        "longitud", producto.getLongitud()
                                );
                            }
                        }
                    }
                });
    }

}
