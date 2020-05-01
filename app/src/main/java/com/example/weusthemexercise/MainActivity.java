package com.example.weusthemexercise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weusthemexercise.model.Contacts;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener , ValueEventListener , ChildEventListener {
    Button buttonAdd,buttonSort,buttonSelect;
    ImageView imageView;
    ListView listViewContacts;
    EditText editTextFirstName,editTextLastName,editTextEmail,editTextPhoneNumber;
    DatabaseReference databaseReference;
    ArrayList<String > listOfContacts;
    ArrayAdapter<String> arrayAdapterContacts;
    FirebaseStorage storage;
    Uri uri;
    private final int PICK_IMAGE_REQUEST = 22;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSort = findViewById(R.id.buttonShowContacts);
        buttonSelect = findViewById(R.id.buttonSelectImage);

        editTextEmail = findViewById(R.id.editTextemail);
        editTextFirstName = findViewById(R.id.editTextCName);
        editTextLastName = findViewById(R.id.editTextLastName);
        imageView = findViewById(R.id.imageView);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
         buttonSelect.setOnClickListener(this);

        buttonSort.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Contacts");



    }

    @Override
    public void onClick(View v) {
        int btnid = v.getId();
        switch(btnid)
        {
            case R.id.buttonAdd:
                add();
                break;
            case R.id.buttonShowContacts:
                sort();
                break;
            case R.id.buttonSelectImage:
                select();
                break;

        }

    }

    private void select() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image"),
                PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            uri = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    private void sort() {
        Intent intent = new Intent(getApplicationContext(),showcontacts.class);
        startActivity(intent);

    }

    private void add() {
        String emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailPattern);
        String mobile = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern pattern1 = Pattern.compile(mobile);

        String  firstName = editTextFirstName.getText().toString();
        String  lastName = editTextLastName.getText().toString();
        String  Email = editTextEmail.getText().toString();
        String  phoneNumber = editTextPhoneNumber.getText().toString();
        if(firstName.equals("") || firstName.equals(null)) {
            Toast.makeText(getApplicationContext(),"Username can't be empty",Toast.LENGTH_SHORT).show();
            return;

        } else if (Email.equals("") || Email.equals(null)) {
            Toast.makeText(getApplicationContext(),"Please enter right email Address",Toast.LENGTH_SHORT).show();
            return;

        }

        else if (phoneNumber.equals("") || phoneNumber.equals(null)) {
            Toast.makeText(getApplicationContext(),"Enter a right mobile number",Toast.LENGTH_SHORT).show();
            return;
        }
        else{


                Contacts contacts = new Contacts(firstName, lastName, Email, phoneNumber);
                databaseReference.child(String.valueOf(firstName)).setValue(contacts);
                editTextPhoneNumber.setText(null);
                editTextEmail.setText(null);
                editTextFirstName.setText(null);
                editTextLastName.setText(null);





        }
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Contacts contacts = dataSnapshot.getValue(Contacts.class);
        Toast.makeText(getApplicationContext(),"Contact has been added",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
