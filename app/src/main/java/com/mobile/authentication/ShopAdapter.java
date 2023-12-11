package com.mobile.authentication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ShopAdapter extends FirebaseRecyclerAdapter<Commandeclass,ShopAdapter.myviewholder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ShopAdapter(@NonNull FirebaseRecyclerOptions<Commandeclass> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShopAdapter.myviewholder holder, int position, @NonNull Commandeclass model) {
        holder.name.setText(model.getNameSK());
        holder.price.setText(String.valueOf(model.getPrice()));
        holder.date.setText(String.valueOf(model.getDate()).toString().toString());
        Glide.with(holder.img.getContext())
                .load(model.getImg())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
        holder.img.setClickable(true);

holder.update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String itemId = getSnapshots().getSnapshot(position).getKey();
        Intent intent = new Intent(view.getContext(), Commande.class);
        intent.putExtra("commandeId", itemId);
        intent.putExtra("productName", model.getNameSK());
        intent.putExtra("productPrice", model.getPrice());
        intent.putExtra("productImage", model.getImg());
        intent.putExtra("productDate", model.getDate());
        intent.putExtra("adresse", model.getAdesse());
        intent.putExtra("nom", model.getNom());
        intent.putExtra("prenom", model.getPrenom());
        intent.putExtra("telephone", model.getTelephone());
        intent.putExtra("latitude", model.getLatitude());
        intent.putExtra("longitude", model.getLongitude());
         intent.putExtra("userId", model.getUserId());
        intent.putExtra("typemodel", "update");
        startActivity(view.getContext(),intent,null);
    }
});

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Perform delete operation here
                        getRef(position).removeValue(); // Assuming getRef() returns the DatabaseReference of the clicked item
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); // Dismiss the dialog if canceled
                    }
                });
                builder.show();
            }
        });




    }





    @NonNull

    @Override
    public ShopAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_shop,parent,false);
        return new ShopAdapter.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        ImageView img,delete,update;
        TextView name,price,date;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.sh1);
            name=itemView.findViewById(R.id.names);
            price=itemView.findViewById(R.id.prices);
            date=itemView.findViewById(R.id.date);
            delete=itemView.findViewById(R.id.delete);
            update=itemView.findViewById(R.id.update);
        }

    }

}




