package br.edu.cafeteria.modelo;

public class ClienteStandard extends Cliente {

    public ClienteStandard(String nome, String cpf) {
        super(nome, cpf);
    }

public void acumularXP(double valorGasto){
    double novoSaldo = getSaldoXP() + valorGasto; //1 de xp pra cada R$1,00 gasto
    setSaldoXP(novoSaldo);
    }
}
