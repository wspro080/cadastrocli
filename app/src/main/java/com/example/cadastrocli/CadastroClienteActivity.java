package com.example.cadastrocli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText nome;
    private EditText cpf;
    private EditText endereco;
    private EditText telefone;
    private ClienteDAO dao;
    private Cliente cliente = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        nome = findViewById(R.id.editNome);
        cpf = findViewById(R.id.editCpf);
        endereco = findViewById(R.id.editEndereco);
        telefone = findViewById(R.id.editTelefone);
        dao = new ClienteDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("cliente")){
            cliente = (Cliente) it.getSerializableExtra("cliente");
            nome.setText(cliente.getNome());
            cpf.setText(cliente.getCpf());
            endereco.setText(cliente.getEndereco());
            telefone.setText(cliente.getTelefone());

        }

    }

    public void Cadastrar(View view) {

        if (cliente == null){
        Cliente cliente = new Cliente();
        cliente.setNome(nome.getText().toString());
        cliente.setCpf(cpf.getText().toString());
        cliente.setEndereco(endereco.getText().toString());
        cliente.setTelefone(telefone.getText().toString());

        long id = dao.inserir(cliente);
        Toast.makeText(this,"Cliente inserido com cliente id" + id, Toast.LENGTH_LONG).show();
    }else{
            cliente.setNome(nome.getText().toString());
            cliente.setCpf(cpf.getText().toString());
            cliente.setEndereco(endereco.getText().toString());
            cliente.setTelefone(telefone.getText().toString());
            dao.atualizar(cliente);
            Toast.makeText(this,"Cliente foi atualizado" , Toast.LENGTH_LONG).show();
        }
    }
}