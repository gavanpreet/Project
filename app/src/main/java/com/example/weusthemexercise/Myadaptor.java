package com.example.weusthemexercise;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weusthemexercise.model.Contacts;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class Myadaptor  extends RecyclerView.Adapter<Myadaptor.AddsViewHolder>  {
    Context context;
    ArrayList<Contacts> addsArrayList;
    String firstnae;
    Button buttonUpdate,buttonDelete;

    public Myadaptor(Context c,ArrayList<Contacts> al)
    {
        context = c;
        addsArrayList = al;
    }
    @NonNull
    @Override
    public AddsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddsViewHolder(LayoutInflater.from(context).inflate(R.layout.addcontact,parent,false));

    }


    @Override
    public void onBindViewHolder(@NonNull final AddsViewHolder holder, final int position) {
        holder.lastname.setText(addsArrayList.get(position).getLastName());
        holder.firstname.setText(addsArrayList.get(position).getFirstName());
        holder.email.setText(addsArrayList.get(position).getEmail());
        holder.phonenumber.setText(addsArrayList.get(position).getPhoneNumber());
        Picasso.get().load(addsArrayList.get(position).getImage()).into(holder.imageView);
        firstnae = addsArrayList.get(position).getFirstName();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference personChild = FirebaseDatabase.getInstance().getReference().child("Contacts").child(String.valueOf(firstnae));
                personChild.removeValue();
                Log.d("FIREBASE","The document with "+firstnae+"is deleted from person collection");
                addsArrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),addsArrayList.size());
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            v.getContext().startActivity(new Intent(v.getContext(),Main2Activity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return addsArrayList.size();
    }

    class AddsViewHolder extends RecyclerView.ViewHolder {
        TextView lastname,firstname,phonenumber,email;
        ImageView imageView;



        public AddsViewHolder(View itemview) {
            super(itemview);
            firstname = itemview.findViewById(R.id.textViewfirstName);
            lastname = itemview.findViewById(R.id.textLastName);
            phonenumber = itemview.findViewById(R.id.TextPhoneNumber);
            imageView = itemview.findViewById(R.id.imageView1);
            email = itemview.findViewById(R.id.TextEmail);
            buttonDelete = itemview.findViewById(R.id.buttonDelete);
            buttonUpdate = itemview.findViewById(R.id.buttonUpdate);



        }
    }
}
