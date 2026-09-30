package model;

//Classe com as operações do Car Wash.
//Guarda as ordens de serviço numa lista duplamente encadeada feita por nós.
//Esta classe não lê dados do teclado: o Menu é que pergunta e chama estes métodos.
public class CarWashSystem {

 // Estados possíveis de uma ordem de serviço
 public static final String EM_ESPERA = "EM ESPERA";
 public static final String EM_LAVAGEM = "EM LAVAGEM";
 public static final String CONCLUIDO = "CONCLUÍDO";
 public static final String CANCELADO = "CANCELADO";

 private ListaDuplamenteEncadeada lista;

 public CarWashSystem() {
     lista = new ListaDuplamenteEncadeada();
 }

 // Devolve a lista (usado pelo FicheiroManager para guardar e carregar).
 public ListaDuplamenteEncadeada getLista() {
     return lista;
 }

 // ------------------------------------------------------------
 // CADASTRO
 // ------------------------------------------------------------

 // Regista uma nova ordem no fim da lista.
 // Devolve false se já existir uma ordem com o mesmo código.
 public boolean registar(OrdemServico ordem) {
     if (existeCodigo(ordem.getCodigo())) {
         return false;
     }
     lista.adicionaFim(ordem);
     return true;
 }

 // Verifica se já existe uma ordem com este código.
 public boolean existeCodigo(int codigo) {
     return procuraNoPorCodigo(codigo) != null;
 }

 // ------------------------------------------------------------
 // PESQUISAS
 // ------------------------------------------------------------

 // Percorre a lista e devolve o nó que tem o código pedido (ou null).
 private No procuraNoPorCodigo(int codigo) {
     No atual = lista.getInicio();

     while (atual != null) {
         OrdemServico ordem = (OrdemServico) atual.getElemento();
         if (ordem.getCodigo() == codigo) {
             return atual;
         }
         atual = atual.getProximo();
     }

     return null;
 }

 // Devolve a ordem com o código pedido (ou null se não existir).
 public OrdemServico buscaPorCodigo(int codigo) {
     No no = procuraNoPorCodigo(codigo);
     if (no == null) {
         return null;
     }
     return (OrdemServico) no.getElemento();
 }

 // Pesquisa por código e mostra o resultado.
 public void pesquisarPorCodigo(int codigo) {
     OrdemServico ordem = buscaPorCodigo(codigo);

     if (ordem == null) {
         System.out.println("Ordem não encontrada.");
     } else {
         System.out.println("Ordem encontrada:");
         mostraOrdem(ordem);
     }
 }

 // Pesquisa por matrícula. Pode haver várias ordens do mesmo carro.
 public void pesquisarPorMatricula(String matricula) {
     int encontradas = 0;
     No atual = lista.getInicio();

     while (atual != null) {
         OrdemServico ordem = (OrdemServico) atual.getElemento();
         if (ordem.getMatricula().equalsIgnoreCase(matricula)) {
             if (encontradas == 0) {
                 System.out.println("Ordem encontrada:");
             }
             mostraOrdem(ordem);
             encontradas++;
         }
         atual = atual.getProximo();
     }

     if (encontradas == 0) {
         System.out.println("Ordem não encontrada.");
     }
 }

 // Pesquisa por nome do cliente (basta o nome conter o texto digitado).
 public void pesquisarPorCliente(String nome) {
     String procurado = nome.toLowerCase();
     int encontradas = 0;
     No atual = lista.getInicio();

     while (atual != null) {
         OrdemServico ordem = (OrdemServico) atual.getElemento();
         String nomeCliente = ordem.getNomeCliente().toLowerCase();
         if (nomeCliente.contains(procurado)) {
             if (encontradas == 0) {
                 System.out.println("Ordem encontrada:");
             }
             mostraOrdem(ordem);
             encontradas++;
         }
         atual = atual.getProximo();
     }

     if (encontradas == 0) {
         System.out.println("Ordem não encontrada.");
     }
 }

 // Pesquisa combinada: só mostra as ordens que cumprem todos os critérios.
 // estado = "" e tipoServico = "" significam "não filtrar por isto".
 // precoMin = 0 significa "sem mínimo" e precoMax = -1 significa "sem máximo".
 public void pesquisaCombinada(String estado, String tipoServico,
                               double precoMin, double precoMax) {
     int encontradas = 0;
     No atual = lista.getInicio();

     while (atual != null) {
         OrdemServico ordem = (OrdemServico) atual.getElemento();
         if (cumpreCriterios(ordem, estado, tipoServico, precoMin, precoMax)) {
             mostraOrdem(ordem);
             encontradas++;
         }
         atual = atual.getProximo();
     }

     if (encontradas == 0) {
         System.out.println("Nenhuma ordem encontrada com esses critérios.");
     } else {
         System.out.println("Total encontrado: " + encontradas);
     }
 }

 // Verifica se uma ordem cumpre os critérios da pesquisa combinada.
 private boolean cumpreCriterios(OrdemServico ordem, String estado,
                                 String tipoServico, double precoMin, double precoMax) {
     if (!estado.equals("") && !ordem.getEstado().equalsIgnoreCase(estado)) {
         return false;
     }
     if (!tipoServico.equals("") && !ordem.getTipoServico().equalsIgnoreCase(tipoServico)) {
         return false;
     }
     if (ordem.getPreco() < precoMin) {
         return false;
     }
     if (precoMax >= 0 && ordem.getPreco() > precoMax) {
         return false;
     }
     return true;
 }

 // ------------------------------------------------------------
 // FILTROS (usam a pesquisa combinada com um só critério)
 // ------------------------------------------------------------

 public void filtrarPorEstado(String estado) {
     pesquisaCombinada(estado, "", 0, -1);
 }

 public void filtrarPorTipoServico(String tipoServico) {
     pesquisaCombinada("", tipoServico, 0, -1);
 }

 public void filtrarPorPreco(double precoMin, double precoMax) {
     pesquisaCombinada("", "", precoMin, precoMax);
 }

 // ------------------------------------------------------------
 // ALTERAÇÃO
 // Alteram diretamente o objeto OrdemServico que está dentro do nó.
 // Devolvem false se o código não existir.
 // ------------------------------------------------------------

 public boolean alterarCliente(int codigo, String novoNome) {
     OrdemServico ordem = buscaPorCodigo(codigo);
     if (ordem == null) {
         return false;
     }
     ordem.setNomeCliente(novoNome);
     return true;
 }

 public boolean alterarMatricula(int codigo, String novaMatricula) {
     OrdemServico ordem = buscaPorCodigo(codigo);
     if (ordem == null) {
         return false;
     }
     ordem.setMatricula(novaMatricula);
     return true;
 }

 public boolean alterarVeiculo(int codigo, String novaMarca, String novoModelo) {
     OrdemServico ordem = buscaPorCodigo(codigo);
     if (ordem == null) {
         return false;
     }
     ordem.setMarca(novaMarca);
     ordem.setModelo(novoModelo);
     return true;
 }

 public boolean alterarServico(int codigo, String novoServico) {
     OrdemServico ordem = buscaPorCodigo(codigo);
     if (ordem == null) {
         return false;
     }
     ordem.setTipoServico(novoServico);
     return true;
 }

 public boolean alterarPreco(int codigo, double novoPreco) {
     OrdemServico ordem = buscaPorCodigo(codigo);
     if (ordem == null) {
         return false;
     }
     ordem.setPreco(novoPreco);
     return true;
 }

 public boolean alterarEstado(int codigo, String novoEstado) {
     OrdemServico ordem = buscaPorCodigo(codigo);
     if (ordem == null) {
         return false;
     }
     ordem.setEstado(novoEstado);
     return true;
 }

 // ------------------------------------------------------------
 // REMOÇÃO
 // ------------------------------------------------------------

 // Remove a ordem com o código pedido.
 // Primeiro procura o nó (O(N)) e depois remove-o (O(1)).
 public boolean removerPorCodigo(int codigo) {
     No no = procuraNoPorCodigo(codigo);

     if (no == null) {
         return false;
     }

     lista.removeNo(no);
     return true;
 }

 // Remove a ordem que está numa posição da lista (a primeira posição é a 0).
 public void removerPorPosicao(int posicao) {
     lista.removePosicao(posicao);
 }

 // ------------------------------------------------------------
 // LISTAGEM
 // ------------------------------------------------------------

 // Lista do início até ao fim, usando getProximo().
 public void listarOrdens() {
     if (lista.isEmpty()) {
         System.out.println("Não há ordens registadas (a lista está vazia).");
         return;
     }

     // primeiro mostra só os códigos ligados por setas: 001 → 002 → 003
     System.out.print("Códigos: ");
     No atual = lista.getInicio();
     while (atual != null) {
         OrdemServico ordem = (OrdemServico) atual.getElemento();
         System.out.print(formataCodigo(ordem.getCodigo()));
         if (atual.getProximo() != null) {
             System.out.print(" → ");
         }
         atual = atual.getProximo();
     }
     System.out.println();
     System.out.println();

     // depois mostra os dados completos de cada ordem
     atual = lista.getInicio();
     while (atual != null) {
         mostraOrdem((OrdemServico) atual.getElemento());
         atual = atual.getProximo();
     }

     System.out.println("Total: " + lista.tamanho() + " ordem(ns).");
 }

 // Lista do fim até ao início, usando getAnterior().
 public void listarOrdensInverso() {
     if (lista.isEmpty()) {
         System.out.println("Não há ordens registadas (a lista está vazia).");
         return;
     }

     // códigos ligados por setas, do último para o primeiro: 004 → 003 → 002
     System.out.print("Códigos: ");
     No atual = lista.getFim();
     while (atual != null) {
         OrdemServico ordem = (OrdemServico) atual.getElemento();
         System.out.print(formataCodigo(ordem.getCodigo()));
         if (atual.getAnterior() != null) {
             System.out.print(" → ");
         }
         atual = atual.getAnterior();
     }
     System.out.println();
     System.out.println();

     atual = lista.getFim();
     while (atual != null) {
         mostraOrdem((OrdemServico) atual.getElemento());
         atual = atual.getAnterior();
     }

     System.out.println("Total: " + lista.tamanho() + " ordem(ns).");
 }

 // Escreve o código com 3 dígitos (1 passa a 001).
 private String formataCodigo(int codigo) {
     return String.format("%03d", codigo);
 }

 // Mostra uma ordem e uma linha de separação.
 private void mostraOrdem(OrdemServico ordem) {
     System.out.println(ordem);
     System.out.println("------------------------------");
 }

 // ------------------------------------------------------------
 // INSERÇÃO ORDENADA (por código)
 // ------------------------------------------------------------

 // Insere a ordem na posição certa para a lista continuar ordenada por código.
 // Só funciona bem se a lista já estiver ordenada por código.
 // Devolve false se o código já existir.
 public boolean insereOrdenado(OrdemServico nova) {
     if (existeCodigo(nova.getCodigo())) {
         return false;
     }

     No atual = lista.getInicio();

     // procura o primeiro nó com código maior que o da nova ordem
     while (atual != null) {
         OrdemServico ordem = (OrdemServico) atual.getElemento();
         if (ordem.getCodigo() > nova.getCodigo()) {
             lista.adicionaAntes(atual, nova);   // entra antes desse nó
             return true;
         }
         atual = atual.getProximo();
     }

     // não há nenhum maior (ou a lista está vazia): entra no fim
     lista.adicionaFim(nova);
     return true;
 }

 // ------------------------------------------------------------
 // MERGESORT
 // criterio: 1 = código, 2 = nome do cliente, 3 = preço
 //
 // Ideia do MergeSort:
 //  1) dividir a lista ao meio;
 //  2) ordenar cada metade (chamando o mesmo método);
 //  3) juntar as duas metades já ordenadas.
 // Não se copia nada para arrays: só se mudam os ponteiros dos nós.
 // Complexidade: O(N log N).
 // ------------------------------------------------------------

 // Ordena a lista toda pelo critério escolhido.
 public void ordenar(int criterio) {
     if (lista.isEmpty()) {
         System.out.println("A lista está vazia, não há nada para ordenar.");
         return;
     }

     No novoInicio = mergeSort(lista.getInicio(), criterio);
     lista.setInicio(novoInicio);
     lista.atualizaFim();   // depois de ordenar, o último nó pode ser outro
 }

 // Ordena a (sub)lista que começa em "cabeca" e devolve o novo primeiro nó.
 private No mergeSort(No cabeca, int criterio) {
     // caso base: lista com 0 ou 1 nó já está ordenada
     if (cabeca == null || cabeca.getProximo() == null) {
         return cabeca;
     }

     // 1) dividir: corta a lista a seguir ao nó do meio
     No meio = encontraMeio(cabeca);
     No segundaMetade = meio.getProximo();
     meio.setProximo(null);
     segundaMetade.setAnterior(null);

     // 2) ordenar cada metade
     No esquerda = mergeSort(cabeca, criterio);
     No direita = mergeSort(segundaMetade, criterio);

     // 3) juntar as duas metades ordenadas
     return junta(esquerda, direita, criterio);
 }

 // Encontra o nó do meio com dois apontadores:
 // "rapido" anda 2 nós de cada vez e "lento" anda 1.
 // Quando o rápido chega ao fim, o lento está no meio.
 private No encontraMeio(No cabeca) {
     No lento = cabeca;
     No rapido = cabeca.getProximo();

     while (rapido != null && rapido.getProximo() != null) {
         lento = lento.getProximo();
         rapido = rapido.getProximo().getProximo();
     }

     return lento;
 }

 // Junta duas listas já ordenadas numa só, religando anterior e proximo.
 private No junta(No esquerda, No direita, int criterio) {
     No cabeca = null;   // primeiro nó da lista resultado
     No cauda = null;    // último nó já colocado no resultado

     while (esquerda != null && direita != null) {
         No escolhido;

         // escolhe o menor dos dois primeiros nós
         if (primeiroVemAntes(esquerda, direita, criterio)) {
             escolhido = esquerda;
             esquerda = esquerda.getProximo();
         } else {
             escolhido = direita;
             direita = direita.getProximo();
         }

         // liga o nó escolhido ao fim do resultado
         if (cabeca == null) {
             cabeca = escolhido;
             escolhido.setAnterior(null);
         } else {
             cauda.setProximo(escolhido);
             escolhido.setAnterior(cauda);
         }
         cauda = escolhido;
     }

     // uma das listas acabou: o que sobrou da outra já está ordenado
     No resto;
     if (esquerda != null) {
         resto = esquerda;
     } else {
         resto = direita;
     }

     if (resto != null) {
         if (cauda == null) {
             cabeca = resto;
         } else {
             cauda.setProximo(resto);
             resto.setAnterior(cauda);
         }
     }

     return cabeca;
 }

 // Diz se o nó a deve ficar antes (ou na mesma posição) do nó b.
 // Usar "menor ou igual" mantém a ordem original quando há empates.
 private boolean primeiroVemAntes(No a, No b, int criterio) {
     OrdemServico ordemA = (OrdemServico) a.getElemento();
     OrdemServico ordemB = (OrdemServico) b.getElemento();

     if (criterio == 2) {
         return ordemA.getNomeCliente().compareToIgnoreCase(ordemB.getNomeCliente()) <= 0;
     } else if (criterio == 3) {
         return ordemA.getPreco() <= ordemB.getPreco();
     } else {
         return ordemA.getCodigo() <= ordemB.getCodigo();
     }
 }

 // ------------------------------------------------------------
 // TAMANHO E LISTA VAZIA
 // ------------------------------------------------------------

 public int tamanho() {
     return lista.tamanho();
 }

 public boolean estaVazia() {
     return lista.isEmpty();
 }
}