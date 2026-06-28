package br.edu.cafeteria.modelo;

import java.util.ArrayList;
import java.util.List;

public class CadastroClientes {

    private List<Cliente> clientes;

    public CadastroClientes() {
        this.clientes = new ArrayList<>();
    }

    // ==========================================
    // MÉTODOS DE CRUD
    // ==========================================

    // CREATE (Cadastrar)
    public void cadastrarCliente(Cliente c) {
        clientes.add(c);
    }

    // READ (Pesquisar por CPF)
    public Cliente pesquisarCliente(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c; // Retorna o cliente se encontrar o CPF
            }
        }
        return null; // Retorna nulo se não encontrar
    }

    // READ (Listar todos)
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes); // Retorna cópia para proteger a lista interna
    }

    // UPDATE (Atualizar)
    public boolean atualizarCliente(String cpf, Cliente novoCliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cpf)) {
                clientes.set(i, novoCliente);
                return true; // Sucesso na atualização
            }
        }
        return false; // Falha (CPF não encontrado)
    }

    // DELETE (Remover)
    public boolean removerCliente(String cpf) {
        Cliente c = pesquisarCliente(cpf);
        if (c != null) {
            clientes.remove(c);
            return true; // Removido com sucesso
        }
        return false; // Falha (CPF não encontrado)
    }
}