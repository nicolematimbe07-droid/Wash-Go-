package model;

//Representa um nó da lista duplamente encadeada.
//anterior <- [elemento] -> proximo
public class No {

 private No anterior;
 private No proximo;
 private Object elemento;

 // Cria um nó sem vizinhos (usado quando a lista está vazia ou ao inserir nas pontas).
 public No(Object elemento) {
     this.elemento = elemento;
     this.anterior = null;
     this.proximo = null;
 }

 // Cria um nó já ligado ao anterior e ao próximo.
 public No(No anterior, Object elemento, No proximo) {
     this.anterior = anterior;
     this.elemento = elemento;
     this.proximo = proximo;
 }

 public No getAnterior() {
     return anterior;
 }

 public void setAnterior(No anterior) {
     this.anterior = anterior;
 }

 public No getProximo() {
     return proximo;
 }

 public void setProximo(No proximo) {
     this.proximo = proximo;
 }

 public Object getElemento() {
     return elemento;
 }

 public void setElemento(Object elemento) {
     this.elemento = elemento;
 }
}