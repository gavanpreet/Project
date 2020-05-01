package com.example.weusthemexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.weusthemexercise.model.Contacts;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {
    Button buttonUpdate;
    EditText editTextFirstName,editTextLastName,editTextEmail,editTextPhoneNumber;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        buttonUpdate = findViewById(R.id.buttonUpdate1);
        editTextEmail = findViewById(R.id.editTextemail1);
        editTextFirstName = findViewById(R.id.editTextCName1);
        editTextLastName = findViewById(R.id.editTextLastName1);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber1);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  firstName = editTextFirstName.getText().toString();
                String  lastName = editTextLastName.getText().toString();
                String  Email = editTextEmail.getText().toString();
                String  phoneNumber = editTextPhoneNumber.getText().toString();
                Contacts contacts = new Contacts(firstName,lastName,Email,phoneNumber);
                databaseReference =  FirebaseDatabase.getInstance().getReference().child("Contacts").child(String.valueOf(firstName));
                databaseReference.setValue(contacts);
                editTextPhoneNumber.setText(null);
                editTextEmail.setText(null);
                editTextFirstName.setText(null);
                editTextLastName.setText(null);
            }
        });
    }
}
