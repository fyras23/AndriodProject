package com.mobile.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnLogOut;
    FirebaseAuth mAuth;
    Button btnToLocation;
    MainAdapter mainAdapter;
    Button btnfilter;
    EditText min,max;
    List<MainModel> mainModels;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnfilter = findViewById(R.id.btnFilter);
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);
    mAuth = FirebaseAuth.getInstance();
   recyclerView = findViewById(R.id.recyclerView);
        getdata();

        // ImageView allShopImageView = findViewById(R.id.allShop);

        /*allShopImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click on allShop ImageView
                // Navigate to the Commande activity and pass the user's ID
                FirebaseUser currentUser = mAuth.getCurrentUser();
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
//                    intent.putExtra("userId", userId);
                    startActivity(intent);

//                if (currentUser != null) {
//                    String userId = currentUser.getUid();
//                    Intent intent = new Intent(MainActivity.this, ShopModel.class);
//                    intent.putExtra("userId", userId);
//                    startActivity(intent);
//                } else {
//                    // User is not authenticated, handle this case accordingly
//                    Toast.makeText(MainActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
//                }
            }
        });
*/
        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("sneakers");
                if(min.getText().toString().isEmpty() || max.getText().toString().isEmpty()){
                  getdata();
                }else {
                    int prixMin = Integer.parseInt(min.getText().toString());
                    int prixMax = Integer.parseInt(max.getText().toString());
                    System.out.println(prixMin);
                    // Faites une requête pour filtrer les éléments en fonction du prix
                    Query query = databaseReference.orderByChild("price").startAt(prixMin).endAt(prixMax);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<MainModel> filteredList = new ArrayList<>();
                            if(dataSnapshot.exists()){
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                MainModel model = snapshot.getValue(MainModel.class);
                                Log.d("TAG", "model: " + "gggggg");
                                // Check if the item's price is within the specified range
                                if (model != null && model.getPrice() >= prixMin && model.getPrice() <= prixMax) {
                                    filteredList.add(model);
                                    Log.d("TAG", "onDataChange: " + filteredList);

                                    FirebaseRecyclerOptions<MainModel> filteredOptions =
                                            new FirebaseRecyclerOptions.Builder<MainModel>()
                                                    .setQuery(query, MainModel.class)
                                                    .build();

                                    mainAdapter = new MainAdapter(filteredOptions);
                                    recyclerView.setAdapter(mainAdapter);
                                    mainAdapter.startListening();


                                }}}else{
                                Toast toast = Toast.makeText(MainActivity.this, "No items found", Toast.LENGTH_SHORT);

                                toast.show();
                                Log.d("TAG", "No items found");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    }
                    });
                }
            }
        });

    }

    public void getdata() {
        try {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            FirebaseRecyclerOptions<MainModel> options =
                    new FirebaseRecyclerOptions.Builder<MainModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("sneakers"), MainModel.class)
                            .build();
            Log.d("TAG", "options: " + options);
            mainAdapter = new MainAdapter(options);
            mainAdapter.startListening();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mainAdapter);


            mainAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }



    public void logout(){
        mAuth.signOut();
        Intent i=new Intent(getApplicationContext(),LoginActivity.class);

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else{
            mainAdapter.startListening();

        }
    }


    public void oppenaddbasquest(android.view.View view) {
        Intent intent = new Intent(this, Commande.class);
        startActivity(intent);
    }



    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
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
