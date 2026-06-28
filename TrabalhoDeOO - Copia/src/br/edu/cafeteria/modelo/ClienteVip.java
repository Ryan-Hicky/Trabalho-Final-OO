package br.edu.cafeteria.modelo;

public class ClienteVip extends Cliente {

    public static final double TAXA_CONVERSAO_XP = 10.0;

    public ClienteVip(String nome, String cpf) {
        super(nome, cpf);
    }

    public void acumularXP(double valorGasto) {
        double novoSaldo = getSaldoXP() + (valorGasto * 2); //2 de xp pra cada R$1,00 gasto
        setSaldoXP(novoSaldo);
    }

    public void pagarComXP(double valorTotal) {
        double xpNecessario = valorTotal * TAXA_CONVERSAO_XP;

        if (getSaldoXP() < xpNecessario) {
            throw new IllegalStateException("Saldo de XP insuficiente para pagar este pedido.");
        /*tem que trocar essa excecao por PontosInsuficientesException como consta no enunciado, nao coloquei porque
ainda nao fizemos o pacote de excecoes */
        }
        double novoSaldo = getSaldoXP() - xpNecessario;
        setSaldoXP(novoSaldo);
    }
}