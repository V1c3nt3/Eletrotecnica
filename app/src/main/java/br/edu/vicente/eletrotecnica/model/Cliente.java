package br.edu.vicente.eletrotecnica.model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int id;
    private String cliente;
    private String tensao;
    private String corrente;
    private String distancia;
    private String cabo;
    //dados que serão obtidos a partir dos anteriores
    private String potencia;
    private String queda;
    private String disjuntor;

    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTensao() {
        return tensao;
    }

    public void setTensao(String tensao) {
        this.tensao = tensao;
    }

    public String getCorrente() {
        return corrente;
    }

    public void setCorrente(String corrente) {
        this.corrente = corrente;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getCabo() {
        return cabo;
    }

    public void setCabo(String cabo) {
        this.cabo = cabo;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getQueda() {
        return queda;
    }

    public void setQueda(String queda) {
        this.queda = queda;
    }

    public String getDisjuntor() {
        return disjuntor;
    }

    public void setDisjuntor(String disjuntor) {
        this.disjuntor = disjuntor;
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente;
    }
}
