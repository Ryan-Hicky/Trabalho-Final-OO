package br.edu.cafeteria.modelo;

import br.edu.cafeteria.servico.Promocional;

public class Bebida extends Produto implements Promocional {

    private String tamanho;
    private int cafeinaMg;

    public Bebida(String codigo, String nome, double precoBase, int quantidadeEstoque, String tamanho, int cafeinaMg) {
        super(codigo, nome, precoBase, quantidadeEstoque);
        this.tamanho = tamanho;
        this.cafeinaMg = cafeinaMg;
    }

    @Override
    public double aplicarDesconto(double valorOriginal) {
        return valorOriginal * 0.90;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public int getCafeinaMg() {
        return cafeinaMg;
    }

    public void setCafeinaMg(int cafeinaMg) {
        this.cafeinaMg = cafeinaMg;
    }
}
