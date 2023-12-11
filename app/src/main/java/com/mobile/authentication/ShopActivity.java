package com.mobile.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ShopActivity extends AppCompatActivity {
    Button btnLogOut;
    FirebaseAuth mAuth;
    Button btnToLocation;
    ShopAdapter shopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        RecyclerView recyclerView = findViewById(R.id.SrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println(currentUser.getUid());
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        FirebaseRecyclerOptions<Commandeclass> options =
                new FirebaseRecyclerOptions.Builder<Commandeclass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("commandes")
                                .orderByChild("userId")
                                .equalTo(currentUser.getUid()), Commandeclass.class)
                        .build();
        shopAdapter = new ShopAdapter(options);
        recyclerView.setAdapter(shopAdapter);
        shopAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }}});


    }

    @Override
    protected void onStart() {
        super.onStart();
        shopAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shopAdapter.stopListening();
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
