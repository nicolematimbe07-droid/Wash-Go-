package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Guarda e carrega as ordens de serviço no ficheiro carwash.txt.
// Cada linha do ficheiro é uma ordem:
// codigo;cliente;matricula;marca;modelo;servico;preco;data;hora;estado
public class FicheiroManager {

    private String nomeFicheiro;

    public FicheiroManager() {
        this.nomeFicheiro = "carwash.txt";
    }

    // Guarda todas as ordens da lista no ficheiro (apaga o conteúdo anterior).
    // Devolve true se correu bem.
    public boolean guardar(ListaDuplamenteEncadeada lista) {
        PrintWriter escritor = null;

        try {
            escritor = new PrintWriter(new FileWriter(nomeFicheiro));

            // percorre a lista do início ao fim e escreve uma linha por ordem
            No atual = lista.getInicio();
            while (atual != null) {
                OrdemServico ordem = (OrdemServico) atual.getElemento();
                escritor.println(converteParaLinha(ordem));
                atual = atual.getProximo();
            }

            System.out.println("Dados guardados em " + nomeFicheiro
                    + " (" + lista.tamanho() + " ordem(ns)).");
            return true;

        } catch (IOException e) {
            System.out.println("Erro ao guardar o ficheiro: " + e.getMessage());
            return false;

        } finally {
            if (escritor != null) {
                escritor.close();
            }
        }
    }

    // Lê o ficheiro e reconstrói a lista duplamente encadeada.
    // A lista atual é esvaziada antes de começar a carregar.
    // Devolve true se correu bem.
    public boolean carregar(ListaDuplamenteEncadeada lista) {
        BufferedReader leitor = null;

        try {
            // se o ficheiro não existir, dá erro aqui e a lista atual não é apagada
            leitor = new BufferedReader(new FileReader(nomeFicheiro));

            lista.limpa();

            int carregadas = 0;
            int ignoradas = 0;
            String linha = leitor.readLine();

            while (linha != null) {
                if (!linha.trim().equals("")) {
                    OrdemServico ordem = converteParaOrdem(linha);

                    if (ordem == null) {
                        ignoradas++;
                    } else {
                        lista.adicionaFim(ordem);   // cria um novo nó no fim
                        carregadas++;
                    }
                }
                linha = leitor.readLine();
            }

            System.out.println("Dados carregados: " + carregadas + " ordem(ns).");
            if (ignoradas > 0) {
                System.out.println("Linhas ignoradas por estarem mal formadas: " + ignoradas);
            }
            return true;

        } catch (IOException e) {
            System.out.println("Erro ao carregar o ficheiro (" + nomeFicheiro + "): "
                    + e.getMessage());
            return false;

        } finally {
            if (leitor != null) {
                try {
                    leitor.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o ficheiro.");
                }
            }
        }
    }

    // Transforma uma ordem numa linha de texto separada por ponto e vírgula.
    private String converteParaLinha(OrdemServico ordem) {
        return ordem.getCodigo() + ";"
             + ordem.getNomeCliente() + ";"
             + ordem.getMatricula() + ";"
             + ordem.getMarca() + ";"
             + ordem.getModelo() + ";"
             + ordem.getTipoServico() + ";"
             + ordem.getPreco() + ";"
             + ordem.getData() + ";"
             + ordem.getHora() + ";"
             + ordem.getEstado();
    }

    // Transforma uma linha do ficheiro numa ordem.
    // Devolve null se a linha estiver mal formada.
    private OrdemServico converteParaOrdem(String linha) {
        String[] partes = linha.split(";");

        if (partes.length != 10) {
            return null;
        }

        try {
            int codigo = Integer.parseInt(partes[0].trim());
            double preco = Double.parseDouble(partes[6].trim());

            return new OrdemServico(codigo, partes[1], partes[2], partes[3], partes[4],
                                    partes[5], preco, partes[7], partes[8], partes[9]);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}