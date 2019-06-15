package com.example.examen2.Utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.examen2.model.Estudiante;

import java.util.LinkedList;
import java.util.List;



public class EstudianteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "estudiante.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "Estudiante";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMNA_ESTUDIANTE_NOMBRE = "nombre";
    public static final String COLUMNA_ESTUDIANTE_EDAD = "edad";
    public static final String COLUMNA_ESTUDIANTE_CORREO = "correo";
    public static final String COLUMNA_ESTUDIANTE_FOTO = "foto";


    public EstudianteDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNA_ESTUDIANTE_NOMBRE + " TEXT NOT NULL, " +
                COLUMNA_ESTUDIANTE_EDAD  + " NUMBER NOT NULL, " +
                COLUMNA_ESTUDIANTE_CORREO+ " TEXT NOT NULL, " +
                COLUMNA_ESTUDIANTE_FOTO  + " BLOB NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    /**create record**/
    public void saveNewPerson(Estudiante estudiante) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_ESTUDIANTE_NOMBRE , estudiante.getNombre());
        values.put(COLUMNA_ESTUDIANTE_EDAD, estudiante.getEdad());
        values.put(  COLUMNA_ESTUDIANTE_CORREO, estudiante.getCorreo());
        values.put( COLUMNA_ESTUDIANTE_FOTO, estudiante.getFoto());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<Estudiante> peopleList(String filter) {
        String query;
        query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ COLUMNA_ESTUDIANTE_NOMBRE +" ASC ";


        List<Estudiante> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Estudiante estudiante;

        if (cursor.moveToFirst()) {
            do {
                estudiante = new Estudiante();

                estudiante.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                estudiante.setNombre(cursor.getString(cursor.getColumnIndex(COLUMNA_ESTUDIANTE_NOMBRE )));
                estudiante.setEdad(cursor.getString(cursor.getColumnIndex(COLUMNA_ESTUDIANTE_EDAD )));
                estudiante.setCorreo(cursor.getString(cursor.getColumnIndex(COLUMNA_ESTUDIANTE_CORREO )));
                estudiante.setFoto(cursor.getString(cursor.getColumnIndex(COLUMNA_ESTUDIANTE_FOTO)));
                personLinkedList.add(estudiante);
            } while (cursor.moveToNext());
        }


        return personLinkedList;
    }

    /**Query only 1 record**/
    public Estudiante getEstudiante(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Estudiante receivedPerson = new Estudiante();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedPerson.setNombre(cursor.getString(cursor.getColumnIndex(COLUMNA_ESTUDIANTE_NOMBRE )));
            receivedPerson.setEdad(cursor.getString(cursor.getColumnIndex(COLUMNA_ESTUDIANTE_EDAD )));
            receivedPerson.setCorreo(cursor.getString(cursor.getColumnIndex(COLUMNA_ESTUDIANTE_CORREO)));
            receivedPerson.setFoto(cursor.getString(cursor.getColumnIndex(COLUMNA_ESTUDIANTE_FOTO)));
        }



        return receivedPerson;


    }


    /**delete record**/
    public void deleteEstudianteRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    /**update record**/
    public void updateEstudianteRecord(long personId, Context context, Estudiante updatedestudiante) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME+" SET nombre ='"+ updatedestudiante.getNombre() + "', edad ='" + updatedestudiante.getEdad()+ "', correo ='"+ updatedestudiante.getCorreo() + "', foto ='"+ updatedestudiante.getFoto() + "'  WHERE _id='" + personId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();


    }




}