package br.edu.cafeteria.app;

import br.edu.cafeteria.modelo.*;
import br.edu.cafeteria.excecao.EstoqueInsuficienteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner          sc       = new Scanner(System.in);
    static CadastroProdutos cardapio = new CadastroProdutos();
    static CadastroClientes clientes = new CadastroClientes();
    static List<Pedido>     pedidos  = new ArrayList<>();

    public static void main(String[] args) {
        int op;
        do {
            limpar();
            System.out.println("=== BYTE & BREW ===");
            System.out.println("1. Novo pedido");
            System.out.println("2. Cardapio");
            System.out.println("3. Clientes");
            System.out.println("4. Pedidos do dia");
            System.out.println("0. Sair");
            op = lerInt();
            switch (op) {
                case 1: novoPedido();    break;
                case 2: menuCardapio();  break;
                case 3: menuClientes();  break;
                case 4: pedidosDoDia();  break;
            }
        } while (op != 0);
        sc.close();
    }

    // ── PEDIDO ────────────────────────────────────────────────────────

    static void novoPedido() {
        limpar();
        System.out.print("Atendente: ");
        String atendente = sc.nextLine();

        System.out.print("CPF do cliente (Enter para pular): ");
        String cpf = sc.nextLine().trim();
        Cliente cliente = cpf.isEmpty() ? null : clientes.pesquisarCliente(cpf);
        if (!cpf.isEmpty() && cliente == null)
            System.out.println("Cliente nao encontrado, pedido sem fidelidade.");

        Pedido pedido = new Pedido(atendente, cliente);

        while (true) {
            limpar();
            imprimirCardapio();
            System.out.print("Codigo do produto (0 para finalizar): ");
            String cod = sc.nextLine().trim();
            if (cod.equals("0")) break;

            Produto p = cardapio.pesquisarProduto(cod);
            if (p == null) { System.out.println("Produto nao encontrado."); pausar(); continue; }

            System.out.print("Quantidade: ");
            int qtd = lerInt();
            try {
                pedido.adicionarItem(p, qtd);
            } catch (EstoqueInsuficienteException e) {
                System.out.println(e.getMessage()); pausar();
            }
        }

        if (pedido.getItens().isEmpty()) { System.out.println("Pedido vazio, cancelado."); pausar(); return; }

        System.out.print("Dia de Evento Geek? (s/n): ");
        boolean geek = sc.nextLine().trim().equalsIgnoreCase("s");

        boolean xp = false;
        if (cliente instanceof ClienteVip) {
            System.out.printf("XP atual: %.1f. Pagar com XP? (s/n): ", cliente.getSaldoXP());
            xp = sc.nextLine().trim().equalsIgnoreCase("s");
        }

        pedido.finalizarPedido(geek, xp);
        pedidos.add(pedido);

        System.out.printf("Pedido #%d finalizado. Total: R$ %.2f%n", pedido.getIdPedido(), pedido.calcularTotal(geek));
        if (cliente != null) System.out.printf("XP atual: %.1f%n", cliente.getSaldoXP());
        pausar();
    }

    // ── CARDAPIO ──────────────────────────────────────────────────────

    static void menuCardapio() {
        int op;
        do {
            limpar();
            System.out.println("=== CARDAPIO ===");
            System.out.println("1. Listar");
            System.out.println("2. Cadastrar produto");
            System.out.println("3. Remover produto");
            System.out.println("0. Voltar");
            op = lerInt();
            switch (op) {
                case 1: limpar(); imprimirCardapio(); pausar(); break;
                case 2: cadastrarProduto(); break;
                case 3: removerProduto();   break;
            }
        } while (op != 0);
    }

    static void cadastrarProduto() {
        limpar();
        System.out.println("1. Comida  2. Bebida");
        int tipo = lerInt();
        System.out.print("Codigo: ");  String cod  = sc.nextLine().trim();
        System.out.print("Nome: ");    String nome = sc.nextLine().trim();
        System.out.print("Preco: ");   double preco = lerDouble();
        System.out.print("Estoque: "); int est = lerInt();

        if (tipo == 1) {
            System.out.print("Tempo de preparo (min): "); int tempo = lerInt();
            System.out.print("Vegano/sem gluten? (s/n): "); boolean v = sc.nextLine().trim().equalsIgnoreCase("s");
            cardapio.cadastrarProduto(new Comida(cod, nome, preco, est, tempo, v));
        } else {
            System.out.print("Tamanho (P/M/G): "); String tam = sc.nextLine().trim();
            System.out.print("Cafeina (mg): "); int caf = lerInt();
            cardapio.cadastrarProduto(new Bebida(cod, nome, preco, est, tam, caf));
        }
        System.out.println("Produto cadastrado!"); pausar();
    }

    static void removerProduto() {
        limpar();
        imprimirCardapio();
        System.out.print("Codigo para remover: ");
        String cod = sc.nextLine().trim();
        System.out.println(cardapio.removerProduto(cod) ? "Removido." : "Nao encontrado.");
        pausar();
    }

    // ── CLIENTES ──────────────────────────────────────────────────────

    static void menuClientes() {
        int op;
        do {
            limpar();
            System.out.println("=== CLIENTES ===");
            System.out.println("1. Listar");
            System.out.println("2. Cadastrar Standard");
            System.out.println("3. Cadastrar VIP");
            System.out.println("4. Remover");
            System.out.println("0. Voltar");
            op = lerInt();
            switch (op) {
                case 1: limpar(); imprimirClientes(); pausar(); break;
                case 2: cadastrarCliente(false); break;
                case 3: cadastrarCliente(true);  break;
                case 4: removerCliente();        break;
            }
        } while (op != 0);
    }

    static void cadastrarCliente(boolean vip) {
        limpar();
        System.out.print("Nome: "); String nome = sc.nextLine().trim();
        System.out.print("CPF: ");  String cpf  = sc.nextLine().trim();
        clientes.cadastrarCliente(vip ? new ClienteVip(nome, cpf) : new ClienteStandard(nome, cpf));
        System.out.println("Cliente cadastrado!"); pausar();
    }

    static void removerCliente() {
        limpar();
        imprimirClientes();
        System.out.print("CPF para remover: ");
        String cpf = sc.nextLine().trim();
        System.out.println(clientes.removerCliente(cpf) ? "Removido." : "Nao encontrado.");
        pausar();
    }

    // ── PEDIDOS DO DIA ────────────────────────────────────────────────

    static void pedidosDoDia() {
        limpar();
        if (pedidos.isEmpty()) { System.out.println("Nenhum pedido hoje."); pausar(); return; }
        double total = 0;
        for (Pedido p : pedidos) {
            double t = p.calcularTotal(false);
            System.out.printf("Pedido #%d | %s | %s | R$ %.2f%n",
                p.getIdPedido(), p.getAtendente(),
                p.getCliente() != null ? p.getCliente().getNome() : "sem cadastro", t);
            total += t;
        }
        System.out.printf("Total do dia: R$ %.2f (%d pedidos)%n", total, pedidos.size());
        pausar();
    }

    // ── UTILITÁRIOS ───────────────────────────────────────────────────

    static void imprimirCardapio() {
        System.out.println("--- Cardapio ---");
        for (Produto p : cardapio.listarProdutos())
            System.out.printf("[%s] %s - R$ %.2f (estoque: %d)%n",
                p.getCodigo(), p.getNome(), p.getPrecoBase(), p.getQuantidadeEstoque());
        System.out.println();
    }

    static void imprimirClientes() {
        System.out.println("--- Clientes ---");
        for (Cliente c : clientes.listarClientes())
            System.out.printf("[%s] %s - CPF: %s - XP: %.1f%n",
                c instanceof ClienteVip ? "VIP" : "STD", c.getNome(), c.getCpf(), c.getSaldoXP());
        System.out.println();
    }

    static void limpar() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausar() {
        System.out.print("\nEnter para continuar...");
        sc.nextLine();
    }

    static int lerInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return 0; }
    }

    static double lerDouble() {
        try { return Double.parseDouble(sc.nextLine().trim().replace(",", ".")); }
        catch (NumberFormatException e) { return 0.0; }
    }
}