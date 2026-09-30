package model;

//Representa uma ordem de serviço (um atendimento) do Car Wash.
public class OrdemServico {

 private int codigo;
 private String nomeCliente;
 private String matricula;
 private String marca;
 private String modelo;
 private String tipoServico;
 private double preco;
 private String data;
 private String hora;
 private String estado;

 public OrdemServico(int codigo, String nomeCliente, String matricula, String marca,
                     String modelo, String tipoServico, double preco,
                     String data, String hora, String estado) {
     this.codigo = codigo;
     this.nomeCliente = nomeCliente;
     this.matricula = matricula;
     this.marca = marca;
     this.modelo = modelo;
     this.tipoServico = tipoServico;
     this.preco = preco;
     this.data = data;
     this.hora = hora;
     this.estado = estado;
 }

 public int getCodigo() {
     return codigo;
 }

 public void setCodigo(int codigo) {
     this.codigo = codigo;
 }

 public String getNomeCliente() {
     return nomeCliente;
 }

 public void setNomeCliente(String nomeCliente) {
     this.nomeCliente = nomeCliente;
 }

 public String getMatricula() {
     return matricula;
 }

 public void setMatricula(String matricula) {
     this.matricula = matricula;
 }

 public String getMarca() {
     return marca;
 }

 public void setMarca(String marca) {
     this.marca = marca;
 }

 public String getModelo() {
     return modelo;
 }

 public void setModelo(String modelo) {
     this.modelo = modelo;
 }

 public String getTipoServico() {
     return tipoServico;
 }

 public void setTipoServico(String tipoServico) {
     this.tipoServico = tipoServico;
 }

 public double getPreco() {
     return preco;
 }

 public void setPreco(double preco) {
     this.preco = preco;
 }

 public String getData() {
     return data;
 }

 public void setData(String data) {
     this.data = data;
 }

 public String getHora() {
     return hora;
 }

 public void setHora(String hora) {
     this.hora = hora;
 }

 public String getEstado() {
     return estado;
 }

 public void setEstado(String estado) {
     this.estado = estado;
 }

 // Duas ordens são iguais se tiverem o mesmo código.
 // Usado pelo método contem() da lista.
 public boolean equals(Object outro) {
     if (outro instanceof OrdemServico) {
         OrdemServico o = (OrdemServico) outro;
         return this.codigo == o.codigo;
     }
     return false;
 }

 // Texto pronto para mostrar na consola.
 public String toString() {
     return "Código: " + codigo + "\n"
          + "Cliente: " + nomeCliente + "\n"
          + "Matrícula: " + matricula + "\n"
          + "Marca: " + marca + "\n"
          + "Modelo: " + modelo + "\n"
          + "Serviço: " + tipoServico + "\n"
          + "Preço: " + String.format("%.2f", preco) + " MT\n"
          + "Data: " + data + "\n"
          + "Hora: " + hora + "\n"
          + "Estado: " + estado;
 }
}