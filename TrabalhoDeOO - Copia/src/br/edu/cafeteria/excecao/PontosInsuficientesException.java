package br.edu.cafeteria.excecao;

// exceção checked
public class PontosInsuficientesException extends Exception {
    public PontosInsuficientesException(String mensagem) {
        super(mensagem);
    }
}