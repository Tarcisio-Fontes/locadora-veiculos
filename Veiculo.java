package locadora_veiculos;

public abstract class Veiculo {
	
	protected String modelo;
	protected String marca;
	protected int ano;
	protected double precoBase;
	protected String veiculoTipo = "";
	
	public Veiculo(String modelo, String marca, int ano, double precoBase) {
		super();
		this.modelo = modelo;
		this.marca = marca;
		this.ano = ano;
		this.precoBase = precoBase;
	}

	public String getModelo() {
		return modelo;
	}

	public String getMarca() {
		return marca;
	}

	public int getAno() {
		return ano;
	}

	public double getPrecoBase() {
		return precoBase;
	}
	
	public String getVeiculoTipo() {
		return veiculoTipo;
	}
	
	@Override
	public String toString() {
		return "Modelo: " + modelo + "\nMarca: " + marca + "\nAno: " + ano + "\nPreço Base: " + precoBase;
	}
	
	protected void setVeiculoTipo(String veiculoTipo) {
		this.veiculoTipo = veiculoTipo;
	}
}
