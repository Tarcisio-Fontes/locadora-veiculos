
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
import java.time.Year;

public class Loja {

	public double calcularServicoExtra(String tipoServico) {
		if(tipoServico.equalsIgnoreCase("seguro")) {
			return 5000;
		}
		else if(tipoServico.equalsIgnoreCase("tanque cheio")) {
			return 500;
		}
		else if(tipoServico.equalsIgnoreCase("condutor adicional")) {
			return 1000;
		}
		else {
			System.out.println("Serviço invalido ou inesxistente.");
			return 0;
		}
	}

	public void cadastrarMoto(ArrayList<Moto> motos) {
		Scanner sc = new Scanner(System.in);

		String modelo;
		String marca;
		int ano;
		double preco;
		int cc;
		String tipo;

		System.out.println("Modelo: ");
		modelo = sc.nextLine();
		System.out.println("Marca: ");
		marca = sc.nextLine();
		System.out.println("Ano: ");
		ano = sc.nextInt();
		System.out.println("Preço: ");
		preco = sc.nextDouble();
		System.out.println("Cilindradas: ");
		cc = sc.nextInt();
		System.out.println("Tipo: ");
		tipo = sc.nextLine();

		Moto moto = new Moto(modelo,marca,ano,preco,cc,tipo);

		motos.add(moto);
		sc.close();
	}

	public void cadastrarCarro(ArrayList<Carro> carros) {

	}
    private ArrayList<Veiculo> motos = new ArrayList<>();
    private ArrayList<Veiculo> carros = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void menuAlugar() {
        String op;
        System.out.printf("========== ALUGAR ==========\n\n1. Carro\n2. Moto\n0. Voltar\n\n--> ");
        op = sc.nextLine();

        switch (op) {
            case "1":
                alugarVeiculo(carros);
                break;
            case "2":
                alugarVeiculo(motos);
                break;
            case "0":
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public void alugarVeiculo(ArrayList<Veiculo> veiculos) {
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo disponível para aluguel.");
            return;
        }

        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo v = veiculos.get(i);
            System.out.printf("ID: %d\n%s\nPreço Atual: R$%.2f\n\n", i, v.toString(), calcularPreco(v));
        }

        System.out.print("Digite o ID do veículo que deseja alugar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (id >= 0 && id < veiculos.size()) {
                Veiculo veiculoAlugado = veiculos.remove(id);
                System.out.println("Veículo alugado com sucesso:\n" + veiculoAlugado.toString());
            } else {
                System.out.println("ID inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
        }
    }

    public double calcularPreco(Veiculo veiculo) {
        String veiculoTipo = veiculo.getVeiculoTipo();
        int ano = veiculo.getAno();
        double precoBase = veiculo.getPrecoBase();

        int taxa = 0;
        if (veiculoTipo.equals("moto")) {
            taxa = 15;
        } else if (veiculoTipo.equals("carro")) {
            taxa = 10;
        }

        return precoBase * Math.pow((1 - (taxa / 100.0)), Year.now().getValue() - ano);
    }

    public double calcularServicoExtra(String tipoServico) {
        switch (tipoServico.toLowerCase()) {
            case "seguro":
                return 5000;
            case "tanque cheio":
                return 500;
            case "condutor adicional":
                return 1000;
            default:
                System.out.println("Serviço inválido ou inexistente.");
                return 0;
        }
    }

    public void cadastrarMoto() {
        System.out.println("Cadastro de Moto:");
        try {
            System.out.print("Modelo: ");
            String modelo = sc.nextLine();
            System.out.print("Marca: ");
            String marca = sc.nextLine();
            System.out.print("Ano: ");
            int ano = Integer.parseInt(sc.nextLine());
            System.out.print("Preço Base: ");
            double preco = Double.parseDouble(sc.nextLine());
            System.out.print("Cilindradas: ");
            int cc = Integer.parseInt(sc.nextLine());
            System.out.print("Tipo: ");
            String tipo = sc.nextLine();

            Moto moto = new Moto(modelo, marca, ano, preco, cc, tipo);
            motos.add(moto);
            System.out.println("Moto cadastrada com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Certifique-se de inserir números nos campos apropriados.");
        }
    }

    public void listarVeiculos(String tipo) {
        ArrayList<Veiculo> lista = tipo.equals("carro") ? carros : motos;
        if (lista.isEmpty()) {
            System.out.println("Nenhum veículo disponível.");
        } else {
            for (Veiculo v : lista) {
                System.out.println(v);
            }
        }
    }
}

