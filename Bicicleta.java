package projeto;

public class Bicicleta extends Veiculo implements Automovel{
    private int numMarchas;
    private String tipoFreio;
    private boolean temSuspensao;

    public Bicicleta(String modelo, String marca, int ano, double precoBase, 
                    int numMarchas, String tipoFreio, boolean temSuspensao) {
        super(modelo, marca, ano, precoBase);
        this.numMarchas = numMarchas;
        this.tipoFreio = tipoFreio;
        this.temSuspensao = temSuspensao;
    }

    public int getNumMarchas() {
        return numMarchas;
    }

    public String getTipoFreio() {
        return tipoFreio;
    }

    public boolean temSuspensao() {
        return temSuspensao;
    }

    @Override
    public double acelerar(double velocidade) {
        return velocidade + 5;
    }

    @Override
    public double frear(double velocidade) {
        return velocidade - 5;
    }
    
    @Override
    public boolean temMotor() {
    	return false;
    }

    @Override
    public String toString() {
        return String.format("===== Bicicleta =====\n\nModelo: %s\nMarca: %s\nAno: %d\nPreço: %.4f\nMarchas: %d\nFreio: %s\nSuspensão: %b\n",
                getModelo(), getMarca(), getAno(), getPrecoBase(), numMarchas, tipoFreio, temSuspensao);
    }

}
