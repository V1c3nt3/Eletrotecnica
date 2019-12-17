package br.edu.vicente.eletrotecnica.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.vicente.eletrotecnica.model.Cliente;

public class ClienteDAO {
    private ConexaoDB conexaoDB;
    //obj de conexão do SQLite
    private SQLiteDatabase banco;

    //cosntrutor da classe
    public ClienteDAO(Context context) {
        this.conexaoDB = new ConexaoDB(context);
        this.banco = conexaoDB.getWritableDatabase();
    }

    //metodo insert do banco de dados
    public long inserirDados(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("cliente", cliente.getCliente());
        values.put("tensao", cliente.getTensao());
        values.put("corrente", cliente.getCorrente());
        values.put("distancia", cliente.getDistancia());
        values.put("cabo", cliente.getCabo());
        values.put("potencia", cliente.getPotencia());
        values.put("queda", cliente.getQueda());
        values.put("disjuntor", cliente.getDisjuntor());
        return banco.insert("rede",null,values);
    }

    //retorna a lista cadastrada no bd
    public List<Cliente> obterDados(){
        List<Cliente> dados = new ArrayList<>();

        Cursor cursor = banco.query(
                "rede",
                new String[]{"id","cliente","tensao","corrente","distancia","cabo",
                        "potencia","queda","disjuntor"},
                null,null,null,null,
                null,null
        );
        while (cursor.moveToNext()){
            Cliente cliente = new Cliente();

            cliente.setId(cursor.getInt(0));
            cliente.setCliente(cursor.getString(1));
            cliente.setTensao(cursor.getString(2));
            cliente.setCorrente(cursor.getString(3));
            cliente.setDistancia(cursor.getString(4));
            cliente.setCabo(cursor.getString(5));
            cliente.setPotencia(cursor.getString(6));
            cliente.setQueda(cursor.getString(7));
            cliente.setDisjuntor(cursor.getString(8));

            dados.add(cliente);
        }
        return dados;
    }

    //metodo delete
    public void excluirDados(Cliente cliente){
        banco.delete("rede","id==?",
                new String[]{String.valueOf(cliente.getId())});
    }

    //metodo update
    public void atualizarDados(Cliente cliente){
        ContentValues values = new ContentValues();

        //valores a serem atuallizados
        values.put("cliente", cliente.getCliente());
        values.put("tensao", cliente.getTensao());
        values.put("corrente",cliente.getCorrente());
        values.put("distancia",cliente.getDistancia());
        values.put("cabo",cliente.getCabo());
        values.put("potencia",cliente.getPotencia());
        values.put("queda", cliente.getQueda());
        values.put("disjuntor", cliente.getDisjuntor());

        //após alterações atualiza os valores no bd
        banco.update("rede", values,
                "id = ?",
                new String[]{String.valueOf(cliente.getId())});
    }
}
