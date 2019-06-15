package com.example.examen2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examen2.Utils.EstudianteDBHelper;
import com.example.examen2.model.Estudiante;


public class UpdateRecordActivity extends AppCompatActivity {

    private EditText mNombreEditText;
    private EditText mEdadEditText;
    private EditText mCorreoEditText;
    private EditText mFotoEditText;
    private Button mUpdateBtn;

    private EstudianteDBHelper dbHelper;
    private long receivedPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);

        //init
        mNombreEditText = (EditText)findViewById(R.id.userNameUpdate);
        mEdadEditText= (EditText)findViewById(R.id.userAgeUpdate);
        mCorreoEditText = (EditText)findViewById(R.id.userOccupationUpdate);
        mFotoEditText = (EditText)findViewById(R.id.userProfileImageLinkUpdate);
        mUpdateBtn = (Button)findViewById(R.id.updateUserButton);

        dbHelper = new EstudianteDBHelper(this);

        try {
            //get intent to get person id
            receivedPersonId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Estudiante queriedPerson = dbHelper.getEstudiante(receivedPersonId);
        //set field to this user data
        mNombreEditText.setText(queriedPerson.getNombre());
        mEdadEditText.setText(queriedPerson.getEdad());
        mCorreoEditText.setText(queriedPerson.getCorreo());
        mFotoEditText.setText(queriedPerson.getFoto());



        //listen to add button click to update
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updatePerson();
            }
        });





    }

    private void updatePerson(){
        String nombre = mNombreEditText.getText().toString().trim();
        String edad = mEdadEditText.getText().toString().trim();
        String correo= mCorreoEditText.getText().toString().trim();
        String foto = mFotoEditText.getText().toString().trim();


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

        //create updated person
        Estudiante updatedPerson = new Estudiante(nombre, edad, correo, foto);

        //call dbhelper update
        dbHelper.updateEstudianteRecord(receivedPersonId, this, updatedPerson);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(this, MainActivity.class));
    }
}