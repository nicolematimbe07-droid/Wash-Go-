package model;

import java.util.Scanner;

//Menu principal do sistema. Lê os dados do teclado, valida e chama o CarWashSystem.
public class Menu {

 private CarWashSystem sistema;
 private FicheiroManager ficheiro;
 private Scanner teclado;

 public Menu() {
     sistema = new CarWashSystem();
     ficheiro = new FicheiroManager();
     teclado = new Scanner(System.in);
 }

 // Repete o menu até o utilizador escolher 0 (Sair).
 public void iniciar() {
     int opcao = -1;

     while (opcao != 0) {
         mostraMenu();
         opcao = lerInteiro("Escolha: ");
         System.out.println();

         switch (opcao) {
             case 1:
                 opcaoRegistar();
                 break;
             case 2:
                 opcaoPesquisarCodigo();
                 break;
             case 3:
                 opcaoPesquisarMatricula();
                 break;
             case 4:
                 opcaoPesquisarCliente();
                 break;
             case 5:
                 opcaoPesquisaCombinada();
                 break;
             case 6:
                 opcaoAlterar();
                 break;
             case 7:
                 opcaoRemoverPorCodigo();
                 break;
             case 8:
                 opcaoRemoverPorPosicao();
                 break;
             case 9:
                 sistema.listarOrdens();
                 break;
             case 10:
                 sistema.listarOrdensInverso();
                 break;
             case 11:
                 opcaoFiltrar();
                 break;
             case 12:
                 opcaoInserirOrdenado();
                 break;
             case 13:
                 opcaoOrdenar();
                 break;
             case 14:
                 ficheiro.guardar(sistema.getLista());
                 break;
             case 15:
                 opcaoCarregar();
                 break;
             case 16:
                 System.out.println("A lista tem " + sistema.tamanho() + " ordem(ns).");
                 break;
             case 17:
                 if (sistema.estaVazia()) {
                     System.out.println("Sim, a lista está vazia.");
                 } else {
                     System.out.println("Não, a lista tem elementos.");
                 }
                 break;
             case 0:
                 System.out.println("A sair do sistema. Até breve!");
                 break;
             default:
                 System.out.println("Opção inválida. Escolha um número entre 0 e 17.");
         }

         System.out.println();
     }

     teclado.close();
 }

 private void mostraMenu() {
     System.out.println("==========================================");
     System.out.println("        SISTEMA DE GESTÃO - CAR WASH");
     System.out.println("==========================================");
     System.out.println();
     System.out.println("1. Registar ordem de serviço");
     System.out.println("2. Pesquisar por código");
     System.out.println("3. Pesquisar por matrícula");
     System.out.println("4. Pesquisar por cliente");
     System.out.println("5. Pesquisa combinada");
     System.out.println("6. Alterar ordem");
     System.out.println("7. Remover por código");
     System.out.println("8. Remover por posição");
     System.out.println("9. Listar ordens");
     System.out.println("10. Listar ordens em ordem inversa");
     System.out.println("11. Filtrar ordens");
     System.out.println("12. Inserir ordem de forma ordenada");
     System.out.println("13. Ordenar com MergeSort");
     System.out.println("14. Guardar dados");
     System.out.println("15. Carregar dados");
     System.out.println("16. Ver tamanho da lista");
     System.out.println("17. Verificar se a lista está vazia");
     System.out.println("0. Sair");
     System.out.println();
 }

 // ------------------------------------------------------------
 // OPÇÕES DO MENU
 // ------------------------------------------------------------

 // Opção 1: regista uma nova ordem no fim da lista.
 private void opcaoRegistar() {
     OrdemServico nova = lerNovaOrdem();

     if (nova != null) {
         if (sistema.registar(nova)) {
             System.out.println("Ordem registada com sucesso.");
         } else {
             System.out.println("Não foi possível registar: o código já existe.");
         }
     }
 }

 // Opção 12: insere a ordem na posição certa, por código.
 private void opcaoInserirOrdenado() {
     System.out.println("Nota: para o resultado ser correto, a lista deve estar");
     System.out.println("ordenada por código (opção 13, critério 1).");
     System.out.println();

     OrdemServico nova = lerNovaOrdem();

     if (nova != null) {
         if (sistema.insereOrdenado(nova)) {
             System.out.println("Ordem inserida de forma ordenada.");
         } else {
             System.out.println("Não foi possível inserir: o código já existe.");
         }
     }
 }

 // Pede os dados de uma nova ordem. Devolve null se o código já existir.
 private OrdemServico lerNovaOrdem() {
     int codigo = lerCodigo("Código: ");

     if (sistema.existeCodigo(codigo)) {
         System.out.println("O código " + codigo + " já está cadastrado.");
         return null;
     }

     String nome = lerTexto("Nome do cliente: ");
     String matricula = lerTexto("Matrícula: ");
     String marca = lerTexto("Marca: ");
     String modelo = lerTexto("Modelo: ");
     String servico = lerTexto("Tipo de serviço: ");
     double preco = lerPreco("Preço (MT): ");
     String data = lerTexto("Data (dd/mm/aaaa): ");
     String hora = lerTexto("Hora (hh:mm): ");
     String estado = lerEstado(false);

     return new OrdemServico(codigo, nome, matricula, marca, modelo,
                             servico, preco, data, hora, estado);
 }

 // Opção 2
 private void opcaoPesquisarCodigo() {
     int codigo = lerInteiro("Digite o código: ");
     System.out.println();
     sistema.pesquisarPorCodigo(codigo);
 }

 // Opção 3
 private void opcaoPesquisarMatricula() {
     String matricula = lerTexto("Digite a matrícula: ");
     System.out.println();
     sistema.pesquisarPorMatricula(matricula);
 }

 // Opção 4
 private void opcaoPesquisarCliente() {
     String nome = lerTexto("Digite o nome do cliente: ");
     System.out.println();
     sistema.pesquisarPorCliente(nome);
 }

 // Opção 5: os critérios que ficarem em branco são ignorados.
 private void opcaoPesquisaCombinada() {
     System.out.println("Pesquisa combinada (deixe em branco o que não quiser usar).");
     System.out.println();

     String estado = lerEstado(true);
     String servico = lerTextoOpcional("Tipo de serviço (ENTER para ignorar): ");
     double precoMin = lerPrecoOpcional("Preço mínimo (ENTER = sem mínimo): ", 0);
     double precoMax = lerPrecoOpcional("Preço máximo (ENTER = sem máximo): ", -1);

     if (precoMax >= 0 && precoMax < precoMin) {
         System.out.println("O preço máximo não pode ser menor que o mínimo.");
         return;
     }

     System.out.println();
     sistema.pesquisaCombinada(estado, servico, precoMin, precoMax);
 }

 // Opção 6: escolhe a ordem pelo código e depois o campo a alterar.
 private void opcaoAlterar() {
     int codigo = lerInteiro("Digite o código da ordem: ");

     OrdemServico ordem = sistema.buscaPorCodigo(codigo);
     if (ordem == null) {
         System.out.println("Ordem não encontrada.");
         return;
     }

     int opcao = -1;
     while (opcao != 0) {
         System.out.println();
         System.out.println(ordem);
         System.out.println();
         System.out.println("1 - Alterar cliente");
         System.out.println("2 - Alterar matrícula");
         System.out.println("3 - Alterar veículo");
         System.out.println("4 - Alterar serviço");
         System.out.println("5 - Alterar preço");
         System.out.println("6 - Alterar estado");
         System.out.println("0 - Voltar");
         opcao = lerInteiro("Escolha: ");

         switch (opcao) {
             case 1:
                 sistema.alterarCliente(codigo, lerTexto("Novo nome do cliente: "));
                 System.out.println("Cliente alterado.");
                 break;
             case 2:
                 sistema.alterarMatricula(codigo, lerTexto("Nova matrícula: "));
                 System.out.println("Matrícula alterada.");
                 break;
             case 3:
                 String marca = lerTexto("Nova marca: ");
                 String modelo = lerTexto("Novo modelo: ");
                 sistema.alterarVeiculo(codigo, marca, modelo);
                 System.out.println("Veículo alterado.");
                 break;
             case 4:
                 sistema.alterarServico(codigo, lerTexto("Novo tipo de serviço: "));
                 System.out.println("Serviço alterado.");
                 break;
             case 5:
                 sistema.alterarPreco(codigo, lerPreco("Novo preço (MT): "));
                 System.out.println("Preço alterado.");
                 break;
             case 6:
                 sistema.alterarEstado(codigo, lerEstado(false));
                 System.out.println("Estado alterado.");
                 break;
             case 0:
                 break;
             default:
                 System.out.println("Opção inválida.");
         }
     }
 }

 // Opção 7
 private void opcaoRemoverPorCodigo() {
     if (sistema.estaVazia()) {
         System.out.println("A lista está vazia.");
         return;
     }

     int codigo = lerInteiro("Digite o código da ordem a remover: ");

     if (sistema.removerPorCodigo(codigo)) {
         System.out.println("Ordem removida com sucesso.");
     } else {
         System.out.println("Ordem não encontrada.");
     }
 }

 // Opção 8: o utilizador vê as posições a partir de 1; a lista usa a partir de 0.
 private void opcaoRemoverPorPosicao() {
     if (sistema.estaVazia()) {
         System.out.println("A lista está vazia.");
         return;
     }

     int tamanho = sistema.tamanho();
     int posicao = lerInteiro("Posição a remover (1 a " + tamanho + "): ");

     if (posicao < 1 || posicao > tamanho) {
         System.out.println("Posição inválida.");
         return;
     }

     sistema.removerPorPosicao(posicao - 1);
     System.out.println("Ordem da posição " + posicao + " removida.");
 }

 // Opção 11
 private void opcaoFiltrar() {
     System.out.println("1 - Mostrar EM ESPERA");
     System.out.println("2 - Mostrar EM LAVAGEM");
     System.out.println("3 - Mostrar CONCLUÍDOS");
     System.out.println("4 - Mostrar CANCELADOS");
     System.out.println("5 - Mostrar por tipo de serviço");
     System.out.println("6 - Mostrar por intervalo de preço");
     System.out.println("0 - Voltar");
     int opcao = lerInteiro("Escolha: ");
     System.out.println();

     switch (opcao) {
         case 1:
             sistema.filtrarPorEstado(CarWashSystem.EM_ESPERA);
             break;
         case 2:
             sistema.filtrarPorEstado(CarWashSystem.EM_LAVAGEM);
             break;
         case 3:
             sistema.filtrarPorEstado(CarWashSystem.CONCLUIDO);
             break;
         case 4:
             sistema.filtrarPorEstado(CarWashSystem.CANCELADO);
             break;
         case 5:
             String servico = lerTexto("Tipo de serviço: ");
             System.out.println();
             sistema.filtrarPorTipoServico(servico);
             break;
         case 6:
             double min = lerPrecoOpcional("Preço mínimo (ENTER = 0): ", 0);
             double max = lerPrecoOpcional("Preço máximo (ENTER = sem máximo): ", -1);
             if (max >= 0 && max < min) {
                 System.out.println("O preço máximo não pode ser menor que o mínimo.");
             } else {
                 System.out.println();
                 sistema.filtrarPorPreco(min, max);
             }
             break;
         case 0:
             break;
         default:
             System.out.println("Opção inválida.");
     }
 }

 // Opção 13
 private void opcaoOrdenar() {
     if (sistema.estaVazia()) {
         System.out.println("A lista está vazia, não há nada para ordenar.");
         return;
     }

     System.out.println("1 - Ordenar por código");
     System.out.println("2 - Ordenar por nome do cliente");
     System.out.println("3 - Ordenar por preço");
     System.out.println("0 - Voltar");
     int opcao = lerInteiro("Escolha: ");

     if (opcao >= 1 && opcao <= 3) {
         sistema.ordenar(opcao);
         System.out.println("Lista ordenada com MergeSort. Use a opção 9 para ver o resultado.");
     } else if (opcao != 0) {
         System.out.println("Opção inválida.");
     }
 }

 // Opção 15: avisa antes de substituir os dados que estão na memória.
 private void opcaoCarregar() {
     if (!sistema.estaVazia()) {
         System.out.println("Atenção: os dados atuais serão substituídos pelos do ficheiro.");
         System.out.print("Continuar? (s/n): ");
         String resposta = teclado.nextLine().trim();
         if (!resposta.equalsIgnoreCase("s")) {
             System.out.println("Operação cancelada.");
             return;
         }
     }

     ficheiro.carregar(sistema.getLista());
 }

 // ------------------------------------------------------------
 // LEITURA E VALIDAÇÃO DO TECLADO
 // Repetem a pergunta até o utilizador escrever um valor válido.
 // ------------------------------------------------------------

 // Lê um número inteiro.
 private int lerInteiro(String mensagem) {
     while (true) {
         System.out.print(mensagem);
         String texto = teclado.nextLine().trim();

         try {
             return Integer.parseInt(texto);
         } catch (NumberFormatException e) {
             System.out.println("Valor inválido. Digite um número inteiro.");
         }
     }
 }

 // Lê um código (número inteiro maior que zero).
 private int lerCodigo(String mensagem) {
     while (true) {
         int codigo = lerInteiro(mensagem);

         if (codigo > 0) {
             return codigo;
         }
         System.out.println("O código deve ser maior que zero.");
     }
 }

 // Lê um texto obrigatório (não pode estar vazio nem ter ';').
 private String lerTexto(String mensagem) {
     while (true) {
         System.out.print(mensagem);
         String texto = teclado.nextLine().trim();

         if (texto.equals("")) {
             System.out.println("Este campo é obrigatório.");
         } else if (texto.contains(";")) {
             System.out.println("Não use o caracter ';' (é usado no ficheiro).");
         } else {
             return texto;
         }
     }
 }

 // Lê um texto que pode ficar vazio.
 private String lerTextoOpcional(String mensagem) {
     System.out.print(mensagem);
     return teclado.nextLine().trim();
 }

 // Lê um preço obrigatório (maior que zero).
 private double lerPreco(String mensagem) {
     while (true) {
         System.out.print(mensagem);
         String texto = teclado.nextLine().trim().replace(",", ".");

         try {
             double preco = Double.parseDouble(texto);
             if (preco > 0) {
                 return preco;
             }
             System.out.println("O preço deve ser maior que zero.");
         } catch (NumberFormatException e) {
             System.out.println("Preço inválido. Exemplo: 500.00");
         }
     }
 }

 // Lê um preço que pode ficar vazio (nesse caso devolve o valor padrão).
 private double lerPrecoOpcional(String mensagem, double valorPadrao) {
     while (true) {
         System.out.print(mensagem);
         String texto = teclado.nextLine().trim().replace(",", ".");

         if (texto.equals("")) {
             return valorPadrao;
         }

         try {
             double preco = Double.parseDouble(texto);
             if (preco >= 0) {
                 return preco;
             }
             System.out.println("O preço não pode ser negativo.");
         } catch (NumberFormatException e) {
             System.out.println("Preço inválido. Exemplo: 500.00");
         }
     }
 }

 // Mostra os estados e devolve o escolhido.
 // Se "opcional" for true, a opção 0 devolve "" (qualquer estado).
 private String lerEstado(boolean opcional) {
     while (true) {
         System.out.println("Estado:");
         if (opcional) {
             System.out.println("  0 - Qualquer estado");
         }
         System.out.println("  1 - EM ESPERA");
         System.out.println("  2 - EM LAVAGEM");
         System.out.println("  3 - CONCLUÍDO");
         System.out.println("  4 - CANCELADO");
         int opcao = lerInteiro("Escolha o estado: ");

         if (opcao == 0 && opcional) {
             return "";
         } else if (opcao == 1) {
             return CarWashSystem.EM_ESPERA;
         } else if (opcao == 2) {
             return CarWashSystem.EM_LAVAGEM;
         } else if (opcao == 3) {
             return CarWashSystem.CONCLUIDO;
         } else if (opcao == 4) {
             return CarWashSystem.CANCELADO;
         }
         System.out.println("Estado inválido.");
     }
 }
}