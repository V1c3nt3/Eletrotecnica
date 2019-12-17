package br.edu.vicente.eletrotecnica.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexaoDB extends SQLiteOpenHelper {
    //define uma constante para o nome do db
    private static final String name = "rede.db";
    private static final int version = 1;

    //define o construtor padrão da classe
    public ConexaoDB(Context context){
        super(context,name, null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table rede(" +
                "id integer primary key autoincrement," +
                "cliente varchar(50)," +
                "tensao varchar(10),"+
                "corrente varchar(10),"+
                "distancia varchar(10),"+
                "cabo varchar(10)," +
                "potencia varchar(10),"+
                "queda varchar(10),"+
                "disjuntor varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
