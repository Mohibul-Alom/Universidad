package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.StatementEvent;

public class BaseDeDatos extends SQLiteOpenHelper {


    public static final String TABLE_NAME = "usuario";
    public static final String NOMBRE = "nombre";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "contra";
    public static final String PUNTUACION = "puntuacion";




    public BaseDeDatos(@Nullable Context context) {
        super(context, "login.db", null, 1);
        //insert2("alom","alom@gmail.com","1234");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table usuario(email text primary key, contra text, nombre text, puntuacion text)");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
        onCreate(db);
    }

    public boolean insert(String nombre, String email, String contra){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contenido = new ContentValues();
        contenido.put("nombre",nombre);
        contenido.put("email",email);
        contenido.put("contra",contra);
        contenido.put("puntuacion",0);

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
            db.close();
            return false;
        } else {
            db.close();
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


    public Usuario getUsuario(String email){

        SQLiteDatabase db = this.getReadableDatabase();

        Usuario u2 = new Usuario();

        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+EMAIL+" = ?";

        Cursor cursor = db.rawQuery("Select * from usuario where email = ?",new String[]{email});

        if (cursor.moveToFirst()){
            u2.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            u2.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
            u2.setNombre(cursor.getString(cursor.getColumnIndex(NOMBRE)));
            u2.setPuntuacion(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PUNTUACION))));
        }

        cursor.close();
        return u2;
    }


    public boolean insertOne(Usuario u1){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues content = new ContentValues();

        content.put(EMAIL,u1.getEmail());
        content.put(PASSWORD,u1.getPassword());
        content.put(NOMBRE,u1.getNombre());
        content.put(PUNTUACION,u1.getPuntuacion());

        long insert = db.insert(TABLE_NAME, null, content);

        if (insert == -1){
            db.close();
            return false;
        }else{
            db.close();
            return true;
        }

    }



    public ArrayList<Usuario> getTodoUsuario(){

        ArrayList <Usuario> listaUsuario = new ArrayList<>();

        String sql = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()){
           do{

               String email = cursor.getString(0);
               String password = cursor.getString(1);
               String nombre = cursor.getString(2);
               int puntuacion = cursor.getInt(3);

               Usuario user = new Usuario(email,password,nombre,puntuacion);
               listaUsuario.add(user);

           }while (cursor.moveToNext());
        }else{
            //no devuelve nada como no hay ningun usuario
        }

        cursor.close();
        db.close();
        return  listaUsuario;

    }

    public void actualizar(Usuario u1){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(EMAIL,u1.getEmail());
        content.put(PASSWORD,u1.getPassword());
        content.put(NOMBRE,u1.getNombre());
        content.put(PUNTUACION,u1.getPuntuacion());

        db.update(TABLE_NAME,content,"email = ?",new String[]{u1.getEmail()});
        db.close();

    }
















}
