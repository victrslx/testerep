/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author jvslx
 */
import java.util.Scanner;

class Cliente {
    String nomeCompleto;
    String dataNasc;
    String telefone;
    String email;
    String endereco;
    String cpf;

    public Cliente(String nomeCompleto, String dataNasc, String telefone, String email, String endereco, String cpf) {
        this.nomeCompleto = nomeCompleto;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.cpf = cpf;
    }
}

class AVLNode {
    Cliente cliente;
    AVLNode left, right;
    int height;

    public AVLNode(Cliente cliente) {
        this.cliente = cliente;
        this.height = 1;
    }
}

class AVLTree {
    AVLNode root;

    int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    int balanceFactor(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, Cliente cliente) {
        if (node == null)
            return (new AVLNode(cliente));

        if (cliente.cpf.compareTo(node.cliente.cpf) < 0)
            node.left = insert(node.left, cliente);
        else if (cliente.cpf.compareTo(node.cliente.cpf) > 0)
            node.right = insert(node.right, cliente);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balanceFactor(node);

        if (balance > 1 && cliente.cpf.compareTo(node.left.cliente.cpf) < 0)
            return rightRotate(node);

        if (balance < -1 && cliente.cpf.compareTo(node.right.cliente.cpf) > 0)
            return leftRotate(node);

        if (balance > 1 && cliente.cpf.compareTo(node.left.cliente.cpf) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && cliente.cpf.compareTo(node.right.cliente.cpf) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void printInOrder(AVLNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.println("Nome: " + node.cliente.nomeCompleto + ", CPF: " + node.cliente.cpf);
            printInOrder(node.right);
        }
    }

    AVLNode searchByCPF(AVLNode node, String cpf) {
        if (node == null || node.cliente.cpf.equals(cpf))
            return node;

        if (cpf.compareTo(node.cliente.cpf) < 0)
            return searchByCPF(node.left, cpf);

        return searchByCPF(node.right, cpf);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AVLTree avlTree = new AVLTree();

        int option;

        do {
            System.out.println("----------------Cadastro de clientes----------------");
            System.out.println("");
            System.out.println("                       MENU");
            System.out.println("");
            System.out.println("            1 - Inserir o cliente");
            System.out.println("            2 - Pesquisar cliente");
            System.out.println("            3 - Imprimir a árvore pré-ordem");
            System.out.println("            4 - Sair");
            System.out.println("");
            System.out.print("Digite a opção desejada: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer de entrada

            switch (option) {
                case 1:
                    System.out.print("Digite o nome completo: ");
                    String nomeCompleto = scanner.nextLine();
                    System.out.print("Digite a data de nascimento: ");
                    String dataNasc = scanner.nextLine();
                    System.out.print("Digite o telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Digite o email: ");
                    String email = scanner.nextLine();
                    System.out.print("Digite o endereço: ");
                    String endereco = scanner.nextLine();
                    System.out.print("Digite o CPF: ");
                    String cpf = scanner.nextLine();

                    Cliente novoCliente = new Cliente(nomeCompleto, dataNasc, telefone, email, endereco, cpf);
                    avlTree.root = avlTree.insert(avlTree.root, novoCliente);
                    break;

                case 2:
                    System.out.print("Digite o CPF para pesquisa: ");
                    String cpfPesquisa = scanner.nextLine();
                    AVLNode encontrado = avlTree.searchByCPF(avlTree.root, cpfPesquisa);
                    if (encontrado != null)
                        System.out.println("Cliente encontrado: " + encontrado.cliente.nomeCompleto);
                    else
                        System.out.println("Cliente não encontrado.");
                    break;

                case 3:
                    System.out.println("Clientes em ordem alfabética:");
                    avlTree.printInOrder(avlTree.root);
                    break;

                case 4:
                    System.out.println("Encerrando o programa.");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (option != 4);

        scanner.close();
    }
}
