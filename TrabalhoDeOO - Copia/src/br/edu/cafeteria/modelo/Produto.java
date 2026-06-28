package br.edu.cafeteria.modelo;

import br.edu.cafeteria.excecao.EstoqueInsuficienteException;

public abstract class Produto {
    private String codigo;
    private String nome;
    private double precoBase;
    private int quantidadeEstoque;

    public Produto(String codigo, String nome, double precoBase, int quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.precoBase = precoBase;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() { return nome; }
    public double getPrecoBase() { return precoBase; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }

    public void verificarDisponibilidade(int quantidadeDesejada) throws EstoqueInsuficienteException {
        if (this.quantidadeEstoque < quantidadeDesejada) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + this.nome + ". Disponível: " + this.quantidadeEstoque);
        }
    }

    public void reduzirEstoque(int quantidade) {
        this.quantidadeEstoque -= quantidade;
    }
}