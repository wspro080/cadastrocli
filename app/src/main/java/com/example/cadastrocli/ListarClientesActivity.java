package com.example.cadastrocli;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ListarClientesActivity extends AppCompatActivity {



    private ListView listView;
    private ClienteDAO dao;
    private List<Cliente> clientes;
    private List<Cliente> clientesFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_clientes);


        // Configura a Toolbar como a ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.cliente_lista);
        dao = new ClienteDAO(this);
        clientes = dao.obterTodos();
        clientesFiltrados.addAll(clientes);
        //ArrayAdapter<Cliente> adaptador = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientesFiltrados);
        ClienteAdapter adaptador = new ClienteAdapter(this,clientesFiltrados);
        listView.setAdapter(adaptador);

        registerForContextMenu(listView);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView)  menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procurarCliente(s);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto,menu);

    }

    public void procurarCliente(String nome){
        clientesFiltrados.clear();
        for (Cliente a : clientes){
            if (a.getNome().toLowerCase().contains(nome.toLowerCase())){
                clientesFiltrados.add(a);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
             (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cliente clienteExcluir = clientesFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir seu cliente ?")
                .setNegativeButton("NÃO",null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clientesFiltrados.remove(clienteExcluir);
                        clientes.remove(clienteExcluir);
                        dao.excluir(clienteExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void cadastrar(MenuItem item){
        Intent it = new Intent(this, CadastroClienteActivity.class);
         startActivity(it);
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Cliente clienteAtualizar = clientesFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, CadastroClienteActivity.class);
        it.putExtra("cliente", clienteAtualizar);
        startActivity(it);

    }
    @Override

    public void onResume(){
        super.onResume();
        clientes = dao.obterTodos();
        clientesFiltrados.clear();
        clientesFiltrados.addAll(clientes);
        listView.invalidateViews();
    }

}