package br.edu.vicente.eletrotecnica.view;

import androidx.appcompat.app.AppCompatActivity;
import br.edu.vicente.eletrotecnica.R;
import br.edu.vicente.eletrotecnica.dao.ClienteDAO;
import br.edu.vicente.eletrotecnica.model.Cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class ResultadoActivity extends AppCompatActivity {
    private EditText servico;
    private EditText volt;
    private EditText amper;
    private EditText metro;
    private EditText cabo;
    private EditText potencia;
    private EditText queda;
    private EditText disjuntor;
    private RadioButton radCU;
    private RadioButton radAl;

    private ClienteDAO clienteDAO;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        setTitle("RESULTADO DOS DADOS");

        servico = findViewById(R.id.edt_cliente);
        volt = findViewById(R.id.edt_tensao);
        amper = findViewById(R.id.edt_corrente);
        metro = findViewById(R.id.edt_distancia);
        cabo = findViewById(R.id.edt_fio);
        potencia = findViewById(R.id.edt_potencia);
        queda = findViewById(R.id.edt_queda);
        disjuntor = findViewById(R.id.edt_disj);
        radCU = (RadioButton)findViewById(R.id.rad_cobre);
        radAl = (RadioButton)findViewById(R.id.rad_alum);

        clienteDAO = new ClienteDAO(this);

        Intent intent = getIntent();
        if (intent.hasExtra("rede")){
            cliente = (Cliente) intent.getSerializableExtra("rede");
            servico.setText(cliente.getCliente());
            volt.setText(cliente.getTensao());
            amper.setText(cliente.getCorrente());
            metro.setText(cliente.getDistancia());
            cabo.setText(cliente.getCabo());
            potencia.setText(cliente.getPotencia());
            queda.setText(cliente.getQueda());
            disjuntor.setText(cliente.getDisjuntor());
        }
    }

    public void voltarInicio(View view){
        startActivity(new Intent(this, InicioActivity.class));
    }

    public void inserirDados(View view) {
        if (volt != null && amper != null && metro != null && cabo != null && radCU.isChecked() || radAl.isChecked()){
            if (cliente == null) {
                cliente = new Cliente();

                cliente.setCliente(servico.getText().toString().trim());
                cliente.setTensao(volt.getText().toString().trim());
                cliente.setCorrente(amper.getText().toString().trim());
                cliente.setDistancia(metro.getText().toString().trim());
                cliente.setCabo(cabo.getText().toString().trim());

                //calculo da potencia do circito
                double v = Double.parseDouble(volt.getText().toString());
                double i = Double.parseDouble(amper.getText().toString());
                ;
                double p = (v * i) / 1000;
                cliente.setPotencia(String.valueOf(p));

                //calculo de queda de tensão
                final double cobre = 0.0172;
                final double alu = 0.0282;
                final double fp = 0.92;
                double r, q = 0;
                double e = Double.parseDouble(volt.getText().toString());
                double a = Double.parseDouble(amper.getText().toString());
                double m = Double.parseDouble(metro.getText().toString());
                double fio = Double.parseDouble(cabo.getText().toString());
                if (radCU.isChecked()) {
                    r = cobre * m / fio;
                    if (e > 0) {
                        q = 100 * (((2 * r) * a * fp) / e);
                    } else {
                        Toast.makeText(this, "Informe valor maior que zero", Toast.LENGTH_LONG).show();
                    }
                } else if (radAl.isChecked()) {
                    r = alu * m / fio;
                    if (e > 0) {
                        q = 100 * (((2 * r) * a * fp) / e);
                    } else {
                        Toast.makeText(this, "Informe valor maior que zero", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Selecione o material", Toast.LENGTH_LONG).show();
                }
                cliente.setQueda(String.valueOf(q));

                //define o tipo do disjuntor
                if (e > 0 && e < 150) {
                    disjuntor.setText("UNIPOLAR");
                } else if (e > 150 && e < 380) {
                    disjuntor.setText("Bipolar ou Tripolar");
                } else {
                    Toast.makeText(this, "REDIMENSIONE O CIRCUITO", Toast.LENGTH_LONG).show();
                }
            } else {
                cliente.setCliente(servico.getText().toString().trim());
                cliente.setTensao(volt.getText().toString().trim());
                cliente.setCorrente(amper.getText().toString().trim());
                cliente.setDistancia(metro.getText().toString().trim());
                cliente.setCabo(cabo.getText().toString().trim());

                //calculo da potencia do circito
                double v = Double.parseDouble(volt.getText().toString());
                double i = Double.parseDouble(amper.getText().toString());
                ;
                double p = (v * i) / 1000;
                cliente.setPotencia(String.valueOf(p));

                //calculo de queda de tensão
                final double cobre = 0.0172;
                final double alu = 0.0282;
                final double fp = 0.92;
                double r, q = 0;
                double e = Double.parseDouble(volt.getText().toString());
                double a = Double.parseDouble(amper.getText().toString());
                double m = Double.parseDouble(metro.getText().toString());
                double fio = Double.parseDouble(cabo.getText().toString());
                if (radCU.isChecked()) {
                    r = cobre * m / fio;
                    if (e > 0) {
                        q = 100 * (((2 * r) * a * fp) / e);
                    } else {
                        Toast.makeText(this, "Informe valor maior que zero", Toast.LENGTH_LONG).show();
                    }
                } else if (radAl.isChecked()) {
                    r = alu * m / fio;
                    if (e > 0) {
                        q = 100 * (((2 * r) * a * fp) / e);
                    } else {
                        Toast.makeText(this, "Informe valor maior que zero", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Selecione o material", Toast.LENGTH_LONG).show();
                }
                cliente.setQueda(String.valueOf(q));

                //define o tipo do disjuntor
                if (e > 0 && e < 150) {
                    disjuntor.setText("UNIPOLAR");
                } else if (e > 150 && e < 380) {
                    disjuntor.setText("Bipolar ou Tripolar");
                } else {
                    Toast.makeText(this, "REDIMENSIONE O CIRCUITO", Toast.LENGTH_LONG).show();
                }
                clienteDAO.atualizarDados(cliente);
            }
        } else {
            Toast.makeText(this, "Informe todos os valores", Toast.LENGTH_LONG).show();
        }
    }
}
