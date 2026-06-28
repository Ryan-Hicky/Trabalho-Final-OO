package br.edu.cafeteria.modelo;

public class Comida extends Produto {

    private int tempoPreparoMinutos;
    private boolean isVeganoSemGluten;

    public Comida(String codigo, String nome,
    			  double precoBase,
                  int quantidadeEstoque, int tempoPreparoMinutos,
                  boolean isVeganoSemGluten) {

        super(codigo, nome, precoBase, quantidadeEstoque);

        this.tempoPreparoMinutos = tempoPreparoMinutos;
        this.isVeganoSemGluten = isVeganoSemGluten;
    }
}