package com.example.cadastrocli;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ClienteAdapter extends BaseAdapter {

    private List<Cliente> clientes;
    private Activity activity;

    public ClienteAdapter(Activity activity,List<Cliente> clientes) {
        this.activity =  activity;
        this.clientes = clientes;
    }
    @Override
    public int getCount() {
        return clientes.size();}

    @Override
    public Object getItem(int i){
        return clientes.get(i);}

    @Override
    public long getItemId(int i)
    { return clientes.get(i).getId() ;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = activity.getLayoutInflater().inflate(R.layout.item, viewGroup,false);

        TextView nome = v.findViewById(R.id.txt_nome);
        TextView cpf = v.findViewById(R.id.txt_cpf);
        TextView telefone = v.findViewById(R.id.txt_telefone);

        Cliente c = clientes.get(i);
        nome.setText(c.getNome());
        cpf.setText(c.getCpf());
        telefone.setText(c.getTelefone());

        return v;

    }
}


