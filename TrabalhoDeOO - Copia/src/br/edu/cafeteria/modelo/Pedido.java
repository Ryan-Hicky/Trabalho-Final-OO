package br.edu.cafeteria.modelo;

import java.util.ArrayList;
import java.util.List;
import br.edu.cafeteria.excecao.EstoqueInsuficienteException;
import br.edu.cafeteria.servico.Promocional;

public class Pedido {

    private static int contadorId = 1;

    private int idPedido;
    private String atendente;
    private Cliente cliente;
    private List<ItemPedido> itens;

    public Pedido(String atendente, Cliente cliente) {
        this.idPedido = contadorId++;
        this.atendente = atendente;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Produto p) throws EstoqueInsuficienteException {
        adicionarItem(p, 1);
    }

    public void adicionarItem(Produto p, int quantidade) throws EstoqueInsuficienteException {
        // ERRO 1 CORRIGIDO: era p.QuantidadeEstoque(), agora usa o getter correto
        if (p.getQuantidadeEstoque() < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + p.getNome());
        }
        ItemPedido novoItem = new ItemPedido(p, quantidade);
        itens.add(novoItem);
    }

    public double calcularTotal(boolean diaDeEventoGeek) {
        double total = 0.0;

        for (ItemPedido item : itens) {
            Produto produto = item.getProduto();
            double precoItem = produto.getPrecoBase();
            if (diaDeEventoGeek && produto instanceof Promocional) {
                Promocional promocional = (Promocional) produto;
                precoItem = promocional.aplicarDesconto(precoItem);
            }
            total += precoItem * item.getQuantidade();
        }
        return total;
    }

    public void finalizarPedido(boolean diaDeEventoGeek, boolean resgatarXP) {
        double totalFinal = calcularTotal(diaDeEventoGeek);

        // Reduz o estoque de cada item do pedido
        for (ItemPedido item : itens) {
            Produto p = item.getProduto();
            p.reduzirEstoque(item.getQuantidade());
        }

        // ERRO 3 CORRIGIDO: lógica de XP implementada
        if (cliente != null) {
            if (resgatarXP && cliente instanceof ClienteVip) {
                // Se for VIP e quiser resgatar, paga com XP
                ClienteVip vip = (ClienteVip) cliente;
                vip.pagarComXP(totalFinal);
            } else {
                // Caso contrário, acumula XP normalmente (Standard ou VIP sem resgatar)
                cliente.acumularXP(totalFinal);
            }
        }
    }

    public int getIdPedido() { return idPedido; }

    public String getAtendente() { return atendente; }
    public void setAtendente(String atendente) { this.atendente = atendente; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<ItemPedido> getItens() { return itens; }
}