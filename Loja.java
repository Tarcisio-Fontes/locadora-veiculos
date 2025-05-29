package projeto;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
import java.time.Year;
public class Loja {
	
	private ArrayList<Veiculo> motos = new ArrayList<Veiculo>();
	private ArrayList<Veiculo> carros = new ArrayList<Veiculo>();
	
	Scanner sc = new Scanner(System.in);
	
	public void menu() {
		
		try {
			int op;
			System.out.printf("1. Alugar Veículo\n0. Sair\n--> ");
			op = sc.nextInt();
			sc.nextLine();
			switch(op) {
			case 1:
				menuAlugar(); break;
			case 0:
				break;
			default:
				menu(); break;
			}
		}catch(java.util.InputMismatchException e){
			System.out.println("Foi digitado algo inesperado");
			sc.nextLine();
		}
		
	}
	
	public void menuAlugar() {
		
		try {
			int op;
			System.out.printf("========== ALUGAR ==========\n\n1. Carro\n2. Moto\n0. Sair\n\n-->");	
			op = sc.nextInt();
			sc.nextLine();
			
			switch(op) {
			case 1: 
				alugarVeiculo(carros); break;
			case 2:
				alugarVeiculo(motos); break;
			case 0:
				menu(); break;
			default:
				menuAlugar(); break;
			}
		}catch(java.util.InputMismatchException e) {
			System.out.println("Foi digitado algo inesperado");
			sc.nextLine();
		}
	}
	
	public void alugarVeiculo(ArrayList<Veiculo> veiculos) {
		ArrayList<Integer> indicesRepetidos = new ArrayList<>(); // Armazena indices de modelos de veiculos do ArrayList "veiculos" repetidos/iguais
		ArrayList<Veiculo> repetidos = new ArrayList<>(); // Armazena os modelos de veiculos repetidos/iguais
		int indice = 0;
		int count = 0;
		int ano;
		int i;
		String modelo;
		
		for(i = 0; i < veiculos.size(); i = i + 1) {
			System.out.printf(veiculos.get(i).toString());
			System.out.println("\nPreco Atual: " + Math.round(calcularPreco(veiculos.get(i))) + "\n\n");
		}
		
		System.out.println("Digite o nome do veiculo que será alugado: ");
		modelo = sc.nextLine();
		
		for(i = 0;i < veiculos.size();i++) {
			if(veiculos.get(i).getModelo().equalsIgnoreCase(modelo)) {
				indicesRepetidos.add(i);
				count++; // Conta quantos modelos existem detro do ArrayList "veiculos" que sejam iguais ao que foi digitado e adiciona os indices dos modelos repetidos dentro de outro ArrayList
			}
		
		if(count==1) {
			System.out.println(veiculos.get(indicesRepetidos.get(0)));
			System.out.println("Veiculo alugado com sucesso");
			System.out.println("\nPreco Atual: " + Math.round(calcularPreco(veiculos.get(i))) + "\n\n");
		}
		
		else if(count>=2) { // Caso tenha repetição, é armazenado em outro ArrayList os modelos repetidos
			for(i=0;i<indicesRepetidos.size();i++) {
				indice = indicesRepetidos.get(i);
				repetidos.add(veiculos.get(indice));
				System.out.printf(veiculos.get(indice).toString());
				System.out.println("\n");
				
			}
			
			System.out.println("Ano do modelo: ");
			ano = sc.nextInt();
			sc.nextLine();
			i = 0;
			
			for(i=0;i<repetidos.size();i++) {
				
				if(repetidos.get(i).getAno()==ano) {
					System.out.println(repetidos.get(i));
					System.out.println("Veiculo alugado com sucesso");
					System.out.println("\nPreco Atual: " + Math.round(calcularPreco(veiculos.get(i))) + "\n\n");
					return;
				}
				else if(repetidos.get(i).getAno()!=ano) {
					continue;
				}
				else {
					System.out.println("Ano de modelo inesxistente");
				}
				
			}
			
		}
		}
	}
	
	
	
	public double calcularPreco(Veiculo veiculo) {
		String veiculoTipo = veiculo.getVeiculoTipo();
		double ano = veiculo.getAno();
		double precoBase = veiculo.getPrecoBase();

		int taxa = 0;
		if(veiculoTipo.equals("moto") == true) {
			taxa = 15;
		} else if(veiculoTipo.equals("carro") == true) {
			taxa = 10;
		}
		
		return precoBase * Math.pow((1 - (taxa/100.0)), Year.now().getValue() - ano);
	}
	
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
	
	public void cadastrarMoto() {
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
	
	public void cadastrarCarro() {
		Scanner sc = new Scanner(System.in);
		
		String modelo;
		String marca;
		int ano;
		double preco;
		
		System.out.println("Modelo: ");
		modelo = sc.nextLine();
		System.out.println("Marca: ");
		marca = sc.nextLine();
		System.out.println("Ano: ");
		ano = sc.nextInt();
		System.out.println("Preço: ");
		preco = sc.nextDouble();
		
		Carro moto = new Carro(modelo,marca,ano,preco);
		
		carros.add(moto);
		sc.close();
	}
	
	public void cadastroInicial() {
		carros.add(new Carro("Corolla", "Toyota", 2025, 164590));
		carros.add(new Carro("Corolla", "Toyota", 2024, 164590));
		carros.add(new Carro("KING GS", "BYD", 2025, 191900));
		carros.add(new Carro("FUSCA 1600", "Volkswagen", 1984, 3000000));
		
		motos.add(new Moto("CG 170 Titan", "Honda", 2024, 190520, 163, "OHC"));
		motos.add(new Moto("BIZ 125 ES", "Honda", 2025, 12500, 124, "Motoneta"));
		motos.add(new Moto("HAYABUSA", "Suzuki", 2025, 124500, 1340, "Esportiva"));
		
	}
	
	public void iniciar() {
		cadastroInicial();
		menu();
	}
}
