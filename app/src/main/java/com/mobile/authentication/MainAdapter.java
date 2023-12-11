package com.mobile.authentication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myviewholder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.price.setText(String.valueOf(model.getPrice()+" DT"));

        Glide.with(holder.img.getContext())
                .load(model.getImg())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        // Set click listener on the entire item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click here, for example, start a new activity
                Intent intent = new Intent(view.getContext(), Commande.class);
                // You can also pass data to the next activity using intent extras
                intent.putExtra("typemodel", "ajoute");
                intent.putExtra("productName", model.getName());
                intent.putExtra("productPrice", model.getPrice());
                intent.putExtra("productImage", model.getImg());
                view.getContext().startActivity(intent);
            }
        });
    }


    @NonNull

    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_it,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,price;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.s1);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }
    private List<MainModel> itemList;




}
