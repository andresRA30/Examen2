package com.example.examen2;


import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.examen2.Utils.EstudianteDBHelper;
import com.example.examen2.model.Estudiante;


public class AddRecordActivity extends AppCompatActivity {

    private EditText mNombreEditText;
    private EditText mEdadEditText;
    private EditText mCorreoEditText;
    private EditText mFotoEditText;
    private Button mAddBtn;
    private ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTUE = 101;
    private EstudianteDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        imageView = findViewById(R.id.imageView);
        //init
        mNombreEditText = (EditText)findViewById(R.id.userName);
        mEdadEditText = (EditText)findViewById(R.id.userAge);
        mCorreoEditText = (EditText)findViewById(R.id.userOccupation);
        mFotoEditText= (EditText)findViewById(R.id.userProfileImageLink);
        mAddBtn = (Button)findViewById(R.id.addNewUserButton);

        //listen to add button click
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                saveEstudiante();
            }
        });

    }
    public void takePicture(View view) {
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(imageTakeIntent.resolveActivity(getPackageManager()) !=null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTUE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTUE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
    private void saveEstudiante(){
        String nombre = mNombreEditText.getText().toString().trim();
        String edad = mEdadEditText.getText().toString().trim();
        String correo = mCorreoEditText.getText().toString().trim();
        String foto = mFotoEditText.getText().toString().trim();
        dbHelper = new EstudianteDBHelper(this);

        if(nombre.isEmpty()){
            //error name is empty
            Toast.makeText(this, "Debes ingresar tu nombre", Toast.LENGTH_SHORT).show();
        }

        if(edad.isEmpty()){
            //error name is empty
            Toast.makeText(this, "Debes ingresar tu edad", Toast.LENGTH_SHORT).show();
        }

        if(correo.isEmpty()){
            //error name is empty
            Toast.makeText(this, "Debes ingresar tu correo", Toast.LENGTH_SHORT).show();
        }

        if(foto.isEmpty()){
            //error name is empty
            Toast.makeText(this, "Debes ingresar tu foto", Toast.LENGTH_SHORT).show();
        }

        //create new person
        Estudiante estudiante = new Estudiante(nombre, edad, correo, foto);
        dbHelper.saveNewPerson(estudiante);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(AddRecordActivity.this, MainActivity.class));
    }
}