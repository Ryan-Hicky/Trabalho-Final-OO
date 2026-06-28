package br.edu.cafeteria.modelo;

public abstract class Cliente {
    private String nome;
    private String cpf;
    protected double saldoXP;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.saldoXP = 0.0;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() { return nome; }
    public double getSaldoXP() { return saldoXP; }
    protected void setSaldoXP(double saldoXP) { this.saldoXP = saldoXP; }

    // metodo sobrescrito (polimorfismo por sobrescrita)
    public abstract void acumularXP(double valorGasto);
}
