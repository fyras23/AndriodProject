package com.mobile.authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Commande extends AppCompatActivity {
    ImageView locaton;
    EditText nom,prenom,telephone,adresse;
    TextView title;
    ProgressDialog progressDialog;
    FirebaseApp firebaseApp;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    Commandeclass commandeclass=new Commandeclass();
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        locaton = findViewById(R.id.locaton);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        telephone=findViewById(R.id.telephone);
        adresse=findViewById(R.id.adress);
        title=findViewById(R.id.title);
        intent = getIntent();
        if(intent.getStringExtra("typemodel").equals("update")){
            title.setText("Update Commande");
            nom.setText(intent.getStringExtra("nom"));
            prenom.setText(intent.getStringExtra("prenom"));
            telephone.setText(intent.getStringExtra("telephone"));
            adresse.setText(intent.getStringExtra("adresse"));
            commandeclass.setLongitude(intent.getDoubleExtra("longitude",0));
            commandeclass.setLatitude(intent.getDoubleExtra("latitude",0));

        }else{
            title.setText("Add Commande");
        }

        firebaseApp=FirebaseApp.initializeApp(this);
        locaton.setOnClickListener(v -> {
            if (intent.getStringExtra("typemodel").equals("update")){
                Intent intent1 = new Intent(getApplicationContext(),CurrentLocation.class);
                intent1.putExtra("city",intent.getStringExtra("adresse"));
                intent1.putExtra("latitude",intent.getDoubleExtra("latitude",0));
                intent1.putExtra("longitude",intent.getDoubleExtra("longitude",0));
                intent1.putExtra("typemodel","update");
                startActivityForResult(intent1, 100);
                return;
            }else {
               Intent i= new Intent(getApplicationContext(), CurrentLocation.class);
                i.putExtra("typemodel","add");
                startActivityForResult(i, 100);
            } });
        ImageView imageView = findViewById(R.id.destinationImageView);
        TextView productNameTextView = findViewById(R.id.destinationProductName);
        TextView productPriceTextView = findViewById(R.id.destinationProductPrice);


        // Retrieve data from the intent
        String productName = intent.getStringExtra("productName");
        System.out.println(productName);
        int productPrice = intent.getIntExtra("productPrice", 0);
        String productImage = intent.getStringExtra("productImage");

        // Update UI with the retrieved data
        productNameTextView.setText(productName);
        productPriceTextView.setText(String.valueOf(productPrice));

        // Load image using Glide or another image loading library
        Glide.with(this)
                .load(productImage)
                .placeholder(R.drawable.common_google_signin_btn_icon_dark) // Placeholder image resource
                .error(R.drawable.common_google_signin_btn_icon_dark_normal) // Error image resource
                .into(imageView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if(resultCode==RESULT_OK){
          if(requestCode==100){
              adresse.setText(data.getStringExtra("city"));
              commandeclass.setAdesse(data.getStringExtra("city"));
              commandeclass.setLatitude(data.getDoubleExtra("latitude",0));
              commandeclass.setLongitude(data.getDoubleExtra("longitude",0));
              System.out.println(data.getStringExtra("latitude"));

          }
      }

    }
    public void addCommande(android.view.View view) {
        if(intent.getStringExtra("typemodel").equals("update")){
            try{

                String city1=adresse.getText().toString();
                if(city1.isEmpty()){
                    Toast.makeText(this, "Please enter city", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                commandeclass.setUserId(getIntent().getStringExtra("userId"));
                commandeclass.setNom(nom.getText().toString());
                commandeclass.setPrenom(prenom.getText().toString());
                commandeclass.setTelephone(telephone.getText().toString());
                commandeclass.setAdesse(adresse.getText().toString());
                commandeclass.setNameSK(getIntent().getStringExtra("productName"));
                commandeclass.setPrice(getIntent().getIntExtra("productPrice",0));
                commandeclass.setImg(getIntent().getStringExtra("productImage"));
                commandeclass.setUserId(getIntent().getStringExtra("userId"));
                commandeclass.setDate(new java.util.Date());
                firebaseDatabase.getReference().child("commandes").child(getIntent().getStringExtra("commandeId")).setValue(commandeclass).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Commande updated successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                    startActivity(intent);
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to update Commande", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                });
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(e.getMessage());
                progressDialog.dismiss();
            }
        }
        else{
        try{
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String city1=adresse.getText().toString();
        if(city1.isEmpty()){
            Toast.makeText(this, "Please enter city", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String userId = currentUser.getUid();
            commandeclass.setUserId(userId);
        commandeclass.setNom(nom.getText().toString());
        commandeclass.setPrenom(prenom.getText().toString());
        commandeclass.setTelephone(telephone.getText().toString());
        commandeclass.setAdesse(adresse.getText().toString());
        commandeclass.setNameSK(getIntent().getStringExtra("productName"));
        commandeclass.setPrice(getIntent().getIntExtra("productPrice",0));
        commandeclass.setImg(getIntent().getStringExtra("productImage"));
        commandeclass.setDate(new java.util.Date());


            firebaseDatabase.getReference().child("commandes").push().setValue(commandeclass).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Commande added successfully", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
            startActivity(intent);
            adresse.setText("");
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to add Commande", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        });
    }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println(e.getMessage());
            progressDialog.dismiss();
        }}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.commandes)
        {
            Intent intent = new Intent(this, ShopActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_home){
            Intent i =new Intent(this, MainActivity.class);
            startActivity(i);
        }
//
        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            return true;
        }

//        else if(item.getItemId()==R.id.quit){
//            finish();
//            return true;
//        }     else
        {
            return super.onOptionsItemSelected(item);
        }
    }

}
