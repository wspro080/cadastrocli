package com.example.cadastrocli;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private final SQLiteDatabase banco;
    public ClienteDAO(Context context){
        Conexao conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();

    }

    public long inserir(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("cpf", cliente.getCpf());
        values.put("endereco", cliente.getEndereco());
        values.put("telefone", cliente.getTelefone());
        return  banco.insert("cliente",null, values);
    }

    public List<Cliente> obterTodos() {
        List<Cliente> clientes = new ArrayList<>();
        Cursor cursor = banco.query("cliente", new String[]{"id", "nome", "cpf"
                ,"endereco", "telefone"}, null, null, null, null, null);
        while (cursor.moveToNext()){
            Cliente a = new Cliente();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
            a.setEndereco(cursor.getString(3));
            a.setTelefone(cursor.getString(4));

            clientes.add(a);
        }
        return clientes;
    }

    public void excluir(Cliente a){
        banco.delete("cliente","id = ?",new String[]{a.getId().toString()});
    }

    public void atualizar(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("cpf", cliente.getCpf());
        values.put("endereco", cliente.getEndereco());
        values.put("telefone", cliente.getTelefone());
        banco.update("cliente",values, "id = ?",
                new String[]{cliente.getId().toString()});
    }
}
