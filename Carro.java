package locadora_veiculos;

public class Carro extends Veiculo implements Automovel{
	private int qtdPortas;
	private boolean conversivel;
	private String tipoCombustivel;

	public Carro(String modelo, String marca, int ano, double precoBase, int qtdPortas, boolean conversivel, String tipoCombustivel) {
		super(modelo, marca, ano, precoBase);
		this.qtdPortas = qtdPortas;
		this.conversivel = conversivel;
		this.tipoCombustivel = tipoCombustivel;
	}

	
	public int getQtdPortas() {
		return qtdPortas;
	}


	public boolean isConversivel() {
		return conversivel;
	}

	public String getTipoCombustivel() {
		return tipoCombustivel;
	}

	@Override
	public double acelerar() {
		return 20;
	}

	@Override
	public double frear() {
		return 20;
	}

	@Override
	public void painel(double velocidade) {
		System.out.println("Velocidade: " + velocidade);
	}	

	@Override
	public String toString() {
		return String.format("===== Carro =====\n\nModelo: %s\nMarca: %s\nAno: %d\nPreço: %.4f\nPortas: %d\nConversível: %b\nCombustível: %s\n",
			    getModelo(), getMarca(), getAno(), getPrecoBase(), qtdPortas, conversivel, tipoCombustivel);

	}	
	
}