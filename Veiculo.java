package projeto;

// Classe abstrata que representa um veículo genérico
public abstract class Veiculo {
    private String modelo;
    private String marca;
    private int ano;
    private double precoBase;
    private String veiculoTipo;

    public Veiculo(String modelo, String marca, int ano, double precoBase) {
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.precoBase = precoBase;
    }

    // Getters e Setters
    public String getModelo(){
    	return modelo;
    }
    public String getMarca(){
    	return marca; 
    }
    public int getAno(){
    	return ano;
    }
    public double getPrecoBase(){
    	return precoBase;
    }
    public String getVeiculoTipo(){
    	return veiculoTipo; 
    }
    public void setVeiculoTipo(String veiculoTipo){
    	this.veiculoTipo = veiculoTipo; 
    }
    @Override
    public String toString() {
        return String.format("Modelo: %s\nMarca: %s\nAno: %d\nPreço Base: R$%.2f", modelo, marca, ano, precoBase);
    }
}
