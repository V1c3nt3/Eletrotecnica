package br.edu.vicente.eletrotecnica.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.vicente.eletrotecnica.R;
import br.edu.vicente.eletrotecnica.dao.ClienteDAO;
import br.edu.vicente.eletrotecnica.model.Cliente;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class InicioActivity extends AppCompatActivity {

    ListView listaDados;
    ClienteDAO clienteDAO;
    List<Cliente> dadosClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        setTitle("DADOS DA REDE");
        listaDados = (ListView) findViewById(R.id.lista_clientes);
        clienteDAO = new ClienteDAO(this);
        dadosClientes = clienteDAO.obterDados();
        ArrayAdapter adapter = new ArrayAdapter<Cliente>(
                this, android.R.layout.simple_list_item_1,
                dadosClientes);
        listaDados.setAdapter(adapter);
        registerForContextMenu(listaDados);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter adapter = new ArrayAdapter<Cliente>(
                this, android.R.layout.simple_list_item_1,
                dadosClientes);
        listaDados.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contexto, menu);
    }

    public void editarDados(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Cliente dadosEditar = dadosClientes.get(menuInfo.position);
        Intent intent = new Intent(this, ResultadoActivity.class);
        intent.putExtra("rede", dadosEditar);
        startActivity(intent);
    }

    public void excluirDados(MenuItem item){
        //criar um adaptar view para ver qual item foi clicado
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //transformar num obj do tipo tarefa o item clicado
        final Cliente dadosExcluir = dadosClientes.get(menuInfo.position);

        //criar uma caixa de dialogo para confirmar a exclusão
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("DESEJA REALMENTE EXCLUIR O CADASTRO")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //remover da ListView ou lista de tarefas
                        dadosClientes.remove(dadosExcluir);
                        //remover do BD
                        clienteDAO.excluirDados(dadosExcluir);
                        //atualizar a listView, reovendo o item selecionado para organizar
                        listaDados.invalidateViews();
                    }
                }).create();
        //mostrar a caixa de dialogo ao usuario
        dialog.show();
    }

    public void inserirCliente(View view) {
        startActivity(new Intent(this, ResultadoActivity.class));
    }
}
