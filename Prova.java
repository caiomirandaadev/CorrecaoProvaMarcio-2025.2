import java.util.Scanner;

public class Prova {

    public static final int TAM_CONTAS = 50;
    public static final int TAM_OP = 1000;
    public static Scanner input = new Scanner(System.in);

    // Questao 1
    public static double lerNaoNegativo(String texto) {
        double valor;
        do {
            System.out.print(texto);
            valor = input.nextDouble();
        } while (valor < 0);
        return valor;
    }

    public static int cadastrarConta(Conta[] v, int tam) {
        if (tam >= TAM_CONTAS) {
            System.out.println("Vetor cheio");
            return tam;
        }

        Conta c = new Conta();

        System.out.print("ID: ");
        c.id = input.nextInt();
        input.nextLine();

        if (buscaId(v, tam, c.id) != -1) {
            System.out.println("ID ja cadastrado");
            return tam;
        }

        System.out.print("Cliente: ");
        c.cliente = input.nextLine();

        c.saldo = lerNaoNegativo("Saldo: ");
        c.limite = lerNaoNegativo("Limite: ");

        v[tam] = c;
        return tam + 1;
    }

    // Questao 2
    public static int buscaBinariaCliente(Conta[] v, int tam, String x) {
        int inicio = 0;
        int fim = tam - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;

            if (v[meio].cliente.compareTo(x) == 0) {
                return meio;
            } else if (v[meio].cliente.compareTo(x) < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return -1;
    }

    // Questao 3
    public static int insertionSortCliente(Conta[] v, int tam) {
        for (int i = 1; i < tam; i++) {
            Conta aux = v[i];
            int j = i - 1;

            while (j >= 0 && v[j].cliente.compareTo(aux.cliente) > 0) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = aux;
        }
        return tam;
    }

    // Questao 4
    public static int filtraOperacoes(Operacao[] vOp, int tamOp, Operacao[] vOpFiltrado, int x) {
        int k = 0;
        for (int i = 0; i < tamOp; i++) {
            if (vOp[i].idConta == x) {
                vOpFiltrado[k] = vOp[i];
                k++;
            }
        }
        return k;
    }

    // Questao 5
    public static String formataValor(double valor) {
        if (valor >= 0) {
            return String.format("%.2fC", valor);
        } else {
            return String.format("%.2fD", valor * -1);
        }
    }

    public static String nomeTipo(char tipo) {
        if (tipo == 'D') {
            return "Deposito";
        } else if (tipo == 'S') {
            return "Saque";
        } else {
            return "Transferencia";
        }
    }

    public static void extrato(Conta[] vContas, int tamConta, Operacao[] vOp, int tamOp) {
        System.out.print("ID da conta: ");
        int id = input.nextInt();

        int pos = buscaId(vContas, tamConta, id);
        if (pos == -1) {
            System.out.println("Conta nao existe");
            return;
        }

        Operacao[] filtradas = new Operacao[TAM_OP];
        int qtd = filtraOperacoes(vOp, tamOp, filtradas, id);

        int inicio = 0;
        if (qtd > 5) {
            inicio = qtd - 5;
        }

        double saldo = 0;
        for (int i = 0; i < inicio; i++) {
            saldo = saldo + filtradas[i].valor;
        }

        System.out.println("CONTA N. " + vContas[pos].id + " CLIENTE: " + vContas[pos].cliente);
        System.out.println("Saldo Anterior: " + formataValor(saldo));

        for (int i = inicio; i < qtd; i++) {
            saldo = saldo + filtradas[i].valor;
            System.out.println(filtradas[i].id + " | " + nomeTipo(filtradas[i].tipo) + " | " + formataValor(filtradas[i].valor) + " | " + formataValor(saldo));
        }

        System.out.println("Saldo Atual: " + formataValor(saldo));
    }
}