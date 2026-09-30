package model;

//Lista duplamente encadeada implementada manualmente com objetos No.
//As posições começam em 0 (a primeira posição é a 0).
//
//null <- [A] <-> [B] <-> [C] -> null
//        ^                ^
//      inicio            fim
public class ListaDuplamenteEncadeada {

 private No inicio;
 private No fim;
 private int tamanho;

 // Cria uma lista vazia.
 public ListaDuplamenteEncadeada() {
     this.inicio = null;
     this.fim = null;
     this.tamanho = 0;
 }

 // ------------------------------------------------------------
 // INSERÇÃO
 // ------------------------------------------------------------

 // Adiciona um novo elemento no início da lista.
 public void adicionaInicio(Object elemento) {
     No novo = new No(elemento);

     if (inicio == null) {
         // lista vazia: o novo nó é o início e o fim
         inicio = novo;
         fim = novo;
     } else {
         novo.setProximo(inicio);
         inicio.setAnterior(novo);
         inicio = novo;
     }

     tamanho++;
 }

 // Adiciona um novo elemento no fim da lista.
 public void adicionaFim(Object elemento) {
     No novo = new No(elemento);

     if (inicio == null) {
         // lista vazia: o novo nó é o início e o fim
         inicio = novo;
         fim = novo;
     } else {
         novo.setAnterior(fim);
         fim.setProximo(novo);
         fim = novo;
     }

     tamanho++;
 }

 // Adiciona um elemento numa posição (0 até tamanho).
 // Posição 0 = início, posição igual ao tamanho = fim, senão = meio.
 public void adicionaPosicao(int posicao, Object elemento) {
     if (posicao < 0 || posicao > tamanho) {
         System.out.println("Posição inválida.");
         return;
     }

     if (posicao == 0) {
         adicionaInicio(elemento);
     } else if (posicao == tamanho) {
         adicionaFim(elemento);
     } else {
         No atual = pegaNo(posicao);   // nó que está hoje nessa posição
         adicionaAntes(atual, elemento);
     }
 }

 // Insere um novo elemento imediatamente antes do nó "referencia".
 // Antes:  [A] <-> [referencia]
 // Depois: [A] <-> [novo] <-> [referencia]
 public void adicionaAntes(No referencia, Object elemento) {
     No anteriorDaReferencia = referencia.getAnterior();
     No novo = new No(anteriorDaReferencia, elemento, referencia);

     if (anteriorDaReferencia == null) {
         // a referência era o primeiro nó, logo o novo passa a ser o início
         inicio = novo;
     } else {
         anteriorDaReferencia.setProximo(novo);
     }
     referencia.setAnterior(novo);

     tamanho++;
 }

 // ------------------------------------------------------------
 // CONSULTA
 // ------------------------------------------------------------

 // Devolve o elemento que está numa posição (ou null se a posição for inválida).
 public Object pega(int posicao) {
     if (posicao < 0 || posicao >= tamanho) {
         System.out.println("Posição inválida.");
         return null;
     }
     return pegaNo(posicao).getElemento();
 }

 // Devolve o nó que está numa posição (a posição já deve ser válida).
 // Se a posição está na primeira metade, começa pelo início;
 // se está na segunda metade, começa pelo fim e anda para trás.
 private No pegaNo(int posicao) {
     No atual;

     if (posicao < tamanho / 2) {
         atual = inicio;
         for (int i = 0; i < posicao; i++) {
             atual = atual.getProximo();
         }
     } else {
         atual = fim;
         for (int i = tamanho - 1; i > posicao; i--) {
             atual = atual.getAnterior();
         }
     }

     return atual;
 }

 // Verifica se um elemento existe na lista (usa o equals do elemento).
 public boolean contem(Object elemento) {
     if (elemento == null) {
         return false;
     }

     No atual = inicio;
     while (atual != null) {
         if (elemento.equals(atual.getElemento())) {
             return true;
         }
         atual = atual.getProximo();
     }
     return false;
 }

 // Devolve o número de elementos da lista.
 public int tamanho() {
     return tamanho;
 }

 // Verifica se a lista está vazia.
 public boolean isEmpty() {
     return tamanho == 0;
 }

 // ------------------------------------------------------------
 // REMOÇÃO
 // ------------------------------------------------------------

 // Remove o primeiro elemento da lista.
 public void removeInicio() {
     if (inicio == null) {
         System.out.println("A lista está vazia.");
         return;
     }

     if (inicio == fim) {
         // só existe um elemento
         inicio = null;
         fim = null;
     } else {
         inicio = inicio.getProximo();
         inicio.setAnterior(null);
     }

     tamanho--;
 }

 // Remove o último elemento da lista.
 public void removeFim() {
     if (fim == null) {
         System.out.println("A lista está vazia.");
         return;
     }

     if (inicio == fim) {
         // só existe um elemento
         inicio = null;
         fim = null;
     } else {
         fim = fim.getAnterior();
         fim.setProximo(null);
     }

     tamanho--;
 }

 // Remove o elemento que está numa posição.
 public void removePosicao(int posicao) {
     if (isEmpty()) {
         System.out.println("A lista está vazia.");
         return;
     }
     if (posicao < 0 || posicao >= tamanho) {
         System.out.println("Posição inválida.");
         return;
     }

     if (posicao == 0) {
         removeInicio();
     } else if (posicao == tamanho - 1) {
         removeFim();
     } else {
         No temp = pegaNo(posicao);
         removeNo(temp);
     }
 }

 // Remove um nó que já foi encontrado (não precisa de procurar outra vez).
 public void removeNo(No no) {
     if (no == null) {
         return;
     }

     if (no == inicio) {
         removeInicio();
     } else if (no == fim) {
         removeFim();
     } else {
         // nó do meio: o anterior e o próximo passam a apontar um para o outro
         no.getAnterior().setProximo(no.getProximo());
         no.getProximo().setAnterior(no.getAnterior());
         tamanho--;
     }
 }

 // Esvazia a lista (usado ao carregar dados do ficheiro).
 public void limpa() {
     inicio = null;
     fim = null;
     tamanho = 0;
 }

 // ------------------------------------------------------------
 // PERCURSOS
 // ------------------------------------------------------------

 // Imprime os elementos do início até ao fim, usando getProximo().
 public void imprimeNormal() {
     if (isEmpty()) {
         System.out.println("A lista está vazia.");
         return;
     }

     No atual = inicio;
     int posicao = 0;
     while (atual != null) {
         System.out.println("--- Posição " + posicao + " ---");
         System.out.println(atual.getElemento());
         atual = atual.getProximo();
         posicao++;
     }
 }

 // Imprime os elementos do fim até ao início, usando getAnterior().
 public void imprimeInverso() {
     if (isEmpty()) {
         System.out.println("A lista está vazia.");
         return;
     }

     No atual = fim;
     int posicao = tamanho - 1;
     while (atual != null) {
         System.out.println("--- Posição " + posicao + " ---");
         System.out.println(atual.getElemento());
         atual = atual.getAnterior();
         posicao--;
     }
 }

 // ------------------------------------------------------------
 // MÉTODOS AUXILIARES (usados pelo CarWashSystem)
 // ------------------------------------------------------------

 // Devolve o primeiro nó, para se poder percorrer a lista com getProximo().
 public No getInicio() {
     return inicio;
 }

 // Devolve o último nó, para se poder percorrer a lista com getAnterior().
 public No getFim() {
     return fim;
 }

 // Define o novo início (usado no MergeSort depois de religar os nós).
 public void setInicio(No novoInicio) {
     this.inicio = novoInicio;
 }

 // Procura o último nó a partir do início e coloca o fim nele.
 // Usado no MergeSort depois de ordenar.
 public void atualizaFim() {
     if (inicio == null) {
         fim = null;
         return;
     }

     No atual = inicio;
     while (atual.getProximo() != null) {
         atual = atual.getProximo();
     }
     fim = atual;
 }
}