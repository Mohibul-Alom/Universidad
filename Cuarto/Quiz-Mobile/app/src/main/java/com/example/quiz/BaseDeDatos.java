package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.logging.Logger;

import javax.sql.StatementEvent;

public class BaseDeDatos extends SQLiteOpenHelper {


    public static final String TABLE_NAME = "usuario";
    public static final String NOMBRE = "nombre";
    public static final String EMAIL = "email";

    SQLiteDatabase db = this.getReadableDatabase();

    public BaseDeDatos(@Nullable Context context) {
        super(context, "login.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table usuario(email text primary key, contra text, nombre text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
        onCreate(db);
    }

    public boolean insert(String nombre, String email, String contra){
        db = this.getWritableDatabase();
        ContentValues contenido = new ContentValues();
        contenido.put("nombre",nombre);
        contenido.put("email",email);
        contenido.put("contra",contra);

        long ins = db.insert("usuario",null,contenido);

        if (ins==1){

            return false;

        }else {

            return true;
        }

    }

    public boolean checkmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from usuario where email=?",new String []{email});

        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkEmailPass(String email,String contra){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from usuario where email=? and contra =?", new String[]{email, contra});


        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }


    }

    public String obtenerNombre(String email) throws SQLException {

        String nombreUsuario="";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor fila = db.rawQuery("Select * from usuario where email = ?",new String[]{email});

        if (fila.moveToFirst()){
           nombreUsuario = fila.getString(2);
        }


        return nombreUsuario;
    }

























}
