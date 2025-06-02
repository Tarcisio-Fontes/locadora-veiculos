package locadora_veiculos;

// Classe que representa uma moto, estendendo Veiculo e implementando Automovel
public class Moto extends Veiculo implements Automovel {
    private int cc;
    private String tipo;

    public Moto(String modelo, String marca, int ano, double precoBase, int cc, String tipo) {
        super(modelo, marca, ano, precoBase);
        this.cc = cc;
        this.tipo = tipo;
        setVeiculoTipo("moto");
    }

    public int getCc(){
    	return cc; 
    }
    public String getTipo(){
    	return tipo;
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
    public String toString() {
        return String.format("===== Moto =====\n\nModelo: %s\nMarca: %s\nAno: %d\nPreço: %.4f\nCilindradas: %d\nTipo: %s\n",getModelo(), getMarca(), getAno(), getPrecoBase(),cc,tipo);
    }

	@Override
	public void painel(double velocidade) {
		System.out.println("Velocidade: " + velocidade);
	}	
}