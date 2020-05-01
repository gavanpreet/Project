package com.example.weusthemexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weusthemexercise.model.Contacts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class showcontacts extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Contacts> list;
    Myadaptor myadaptor;
    EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcontacts);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Contacts");
        editTextSearch = findViewById(R.id.editTextSearch);


        recyclerView = (RecyclerView)findViewById(R.id.show);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Contacts>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                    Contacts adds = dataSnapshot1.getValue(Contacts.class);
                    list.add(adds);
                }
                Collections.sort(list, new Comparator<Contacts>() {
                            @Override
                            public int compare(Contacts o1, Contacts o2) {
                               return o1.getFirstName().compareTo(o2.getFirstName());
                            }
                        });
                        myadaptor = new Myadaptor(showcontacts.this, list);

                recyclerView.setAdapter(myadaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"oops",Toast.LENGTH_LONG).show();

            }
        });
    }

}
