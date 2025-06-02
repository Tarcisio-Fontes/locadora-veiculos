package locadora_veiculos;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
import java.time.Year;

public class Loja {
    // Listas para armazenar os veículos por categoria
    private ArrayList<Veiculo> motos = new ArrayList<Veiculo>();
    private ArrayList<Veiculo> carros = new ArrayList<Veiculo>();
    private ArrayList<Veiculo> bicicletas = new ArrayList<Veiculo>();
    
    // Scanner para entrada de dados do usuário
    private Scanner sc = new Scanner(System.in);  
    
    // Método principal que exibe o menu inicial
    public void menu() {
    	Scanner sc = new Scanner(System.in);
    	int opcao = -1;

    	while (opcao != 0) {
    		System.out.println("\n=== LOCADORA DE VEÍCULOS ===");
    	    System.out.println("1 - Cadastrar moto");
    	    System.out.println("2 - Cadastrar carro");
    	    System.out.println("3 - Cadastrar bicicleta");
    	    System.out.println("4 - Alugar veículo");
    	    System.out.println("5 - Listar disponíveis");
    	    System.out.println("6 - Calcular serviço extra");
    	    System.out.println("7 - Teste Drive");
    	    System.out.println("0 - Sair");
    	    System.out.print("Escolha: ");

    	    try{
    	    	opcao = Integer.parseInt(sc.nextLine());
    	        switch (opcao) {
    	        	case 1:
    	        		cadastrarMoto();
    	                break;
    	        	case 2:
    	                cadastrarCarro();
    	                break;
    	        	case 3:
    	                cadastrarBicicleta();
    	                break;
    	            case 4:
    	            	menuAlugar();
    	                break;
    	            case 5:
    	                listarVeiculos();
    	                break;
    	            case 6:
    	                System.out.print("Informe o serviço (seguro/tanque cheio/condutor adicional): ");
    	                String servico = sc.nextLine();
    	                double valor = calcularServicoExtra(servico);
    	                if (valor > 0) {
    	                        System.out.printf("Valor: R$ %.2f\n", valor);
    	                    }
    	                    break; 
    	            case 7:
    	            	testeDrive();
    	            	break;
    	            case 0:
    	                System.out.println("Saindo...");
    	                break;
    	            default:
    	                System.out.println("Opção inválida!");
    	            }
    	        } catch (NumberFormatException e) {
    	            System.out.println("Erro: Digite apenas números!");
    	        }
    	    }

    	    sc.close();
    	}
    
    // Menu para selecionar tipo de veículo para alugar
    public void menuAlugar() {
        try {
            int op;
            System.out.printf("========== ALUGAR ==========\n\n1. Carro\n2. Moto\n3. Bicicleta\n0. Sair\n\n-->");    
            op = sc.nextInt();
            sc.nextLine();
            
            switch(op) {
                case 1: 
                    alugarVeiculo(carros); // Aluga carro
                    break;
                case 2:
                    alugarVeiculo(motos); // Aluga moto
                    break;
                case 3:
                    alugarVeiculo(bicicletas); // Aluga bicicleta
                    break;
                case 0: 
                    menu(); // Volta ao menu principal
                    break;
                default:
                    menuAlugar(); // Repete menu se opção inválida
                    break;
            }
        } catch(java.util.InputMismatchException e) {
            System.out.println("Foi digitado algo inesperado");
            sc.nextLine(); // Limpa buffer do scanner
        }
    }
    
    // Método para processar o aluguel de veículos
    public void alugarVeiculo(ArrayList<Veiculo> veiculos) {
        ArrayList<Integer> indicesRepetidos = new ArrayList<>(); // Armazena índices de veículos com mesmo modelo
        ArrayList<Veiculo> repetidos = new ArrayList<>(); // Armazena veículos com mesmo modelo
        int count = 0;
        String modelo;

        // Lista todos os veículos disponíveis
        listarVeiculos();

        System.out.println("Digite o nome do veículo que será alugado: ");
        modelo = sc.nextLine();

        // Verifica quantos veículos têm o modelo informado
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getModelo().equalsIgnoreCase(modelo)) {
                indicesRepetidos.add(i);
                count++;
            }
        }

        // Tratamento para modelos não encontrados
        if (count == 0) {
            System.out.println("Modelo não encontrado!");
            return;
        }
        // Tratamento para quando há apenas um veículo com o modelo
        else if (count == 1) {
            Veiculo veiculo = veiculos.get(indicesRepetidos.get(0));
            System.out.println(veiculo);
            System.out.println("Veículo alugado com sucesso!");
            System.out.println("Preço Atual: " + Math.round(calcularPreco(veiculo)));
            veiculos.remove(veiculo); // Remove o veículo alugado da lista
        }
        // Tratamento para quando há vários veículos com mesmo modelo
        else {
            System.out.println("========== MODELOS REPETIDOS ==========");
            for (int i : indicesRepetidos) {
                Veiculo veiculo = veiculos.get(i);
                repetidos.add(veiculo);
                System.out.println(veiculo + "\n");
            }

            System.out.println("Digite o ano do modelo desejado: ");
            int ano = sc.nextInt();
            sc.nextLine();

            boolean encontrado = false;
            // Procura o veículo com o ano especificado
            for (Veiculo veiculo : repetidos) {
                if (veiculo.getAno() == ano) {
                    System.out.println(veiculo);
                    System.out.println("Veículo alugado com sucesso!");
                    System.out.println("Preço Atual: " + Math.round(calcularPreco(veiculo)));
                    veiculos.remove(veiculo); // Remove o veículo alugado da lista
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Ano não encontrado para o modelo digitado!");
            }
        }
    }
    
    // Calcula o preço atualizado do veículo com base no ano e tipo
    public double calcularPreco(Veiculo veiculo) {
        String veiculoTipo = veiculo.getVeiculoTipo(); // Obtém o tipo do veículo
        double ano = veiculo.getAno();
        double precoBase = veiculo.getPrecoBase();

        int taxa = 0;
        
        // Define a taxa de depreciação conforme o tipo de veículo
        if(veiculoTipo.equals("moto")) {
            taxa = 15;
        } else if(veiculoTipo.equals("carro")) {
            taxa = 10;
        } else if(veiculoTipo.equals("bicicleta")) {
            taxa = 5;
        }
        
        // Calcula o preço atual com depreciação anual
        return precoBase * Math.pow((1 - (taxa/100.0)), Year.now().getValue() - ano);
    }
    
    // Calcula o valor de serviços extras
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
            System.out.println("Serviço invalido ou inexistente.");
            return 0;
        }
    }
    
    public void testeDrive() {
    	ArrayList<Integer> indicesRepetidos = new ArrayList<>();
    	ArrayList<Carro> repetidosC = new ArrayList<>();
    	ArrayList<Moto> repetidosM = new ArrayList<>();
    	String modelo;
    	double velocidade = 0;
    	int count = 0;
    	int op;
    	int i;
    	int j;
    	
    	for(Veiculo carro : carros) {
    		System.out.println(carro);
    	}
    	
    	for(Veiculo moto : motos) {
    		System.out.println(moto);
    	}
    	
    	System.out.println("Deseja fazer o teste drive com qual tipo de veiculo? ");
    	System.out.println("1 - Carro");
    	System.out.println("2 - Moto");
    	op = sc.nextInt();
    	sc.nextLine();
    	
    	if(op==1) {
    		if(!carros.isEmpty()) {
    			for(Veiculo carro : carros) {
    				System.out.println(carro);
    			}
    			System.out.println("Qual modelo? ");
    			modelo = sc.nextLine();
    			
    			for (i = 0; i < carros.size(); i++) {
    	            if (carros.get(i).getModelo().equalsIgnoreCase(modelo)) {
    	                indicesRepetidos.add(i);
    	                count++;
    	            }
    	        }

    	        // Tratamento para modelos não encontrados
    	        if (count == 0) {
    	            System.out.println("Modelo não encontrado!");
    	            return;
    	        }
    	        // Tratamento para quando há apenas um veículo com o modelo
    	        else if (count == 1) {
    	            Carro carro = (Carro) carros.get(indicesRepetidos.get(0));
    	            System.out.println(carro);
    	            for(i = 0; i < 10; i++) {
    	            	System.out.println("1 - Acelerar");
    	            	System.out.println("2 - Frear");
    	            	j = sc.nextInt();
    	            	sc.nextLine();
    	            	if(j==1) {
    	            		velocidade += carro.acelerar();
    	            	}
    	            	else {
    	            		velocidade -= carro.frear();
    	            	}
    	            	carro.painel(velocidade);
    	            }
    	        }
    	        // Tratamento para quando há vários veículos com mesmo modelo
    	        else {
    	            System.out.println("========== MODELOS REPETIDOS ==========");
    	            for (int z : indicesRepetidos) {
    	                Carro carro = (Carro) carros.get(z);
    	                repetidosC.add(carro);
    	                System.out.println(carro + "\n");
    	            }

    	            System.out.println("Digite o ano do modelo desejado: ");
    	            int ano = sc.nextInt();
    	            sc.nextLine();

    	            boolean encontrado = false;
    	            // Procura o veículo com o ano especificado
    	            for (Carro carro : repetidosC) {
    	                if (carro.getAno() == ano) {
    	                    System.out.println(carro);
    	                    for(i = 0; i < 10; i++) {
    	    	            	System.out.println("1 - Acelerar");
    	    	            	System.out.println("2 - Frear");
    	    	            	j = sc.nextInt();
    	    	            	sc.nextLine();
    	    	            	if(j==1) {
    	    	            		velocidade += carro.acelerar();
    	    	            	}
    	    	            	else {
    	    	            		velocidade -= carro.frear();
    	    	            	}
    	    	            	carro.painel(velocidade);
    	    	            }
    	                    encontrado = true;
    	                    break;
    	                }
    	            }

    	            if (!encontrado) {
    	                System.out.println("Ano não encontrado para o modelo digitado!");
    	            }
    	        }
    		}
    	}
    	else {
    		if(!motos.isEmpty()) {
    			for(Veiculo moto : motos) {
    				System.out.println(moto);
    			}
    			System.out.println("Qual modelo? ");
    			modelo = sc.nextLine();
    			
    			for (i = 0; i < motos.size(); i++) {
    	            if (motos.get(i).getModelo().equalsIgnoreCase(modelo)) {
    	                indicesRepetidos.add(i);
    	                count++;
    	            }
    	        }

    	        // Tratamento para modelos não encontrados
    	        if (count == 0) {
    	            System.out.println("Modelo não encontrado!");
    	            return;
    	        }
    	        // Tratamento para quando há apenas um veículo com o modelo
    	        else if (count == 1) {
    	            Moto moto = (Moto) motos.get(indicesRepetidos.get(0));
    	            System.out.println(moto);
    	            for(i = 0; i < 10; i++) {
    	            	System.out.println("1 - Acelerar");
    	            	System.out.println("2 - Frear");
    	            	j = sc.nextInt();
    	            	sc.nextLine();
    	            	if(j==1) {
    	            		velocidade += moto.acelerar();
    	            	}
    	            	else {
    	            		velocidade -= moto.frear();
    	            	}
    	            	moto.painel(velocidade);
    	            }
    	        }
    	        // Tratamento para quando há vários veículos com mesmo modelo
    	        else {
    	            System.out.println("========== MODELOS REPETIDOS ==========");
    	            for (int z : indicesRepetidos) {
    	                Moto moto = (Moto) motos.get(z);
    	                repetidosM.add(moto);
    	                System.out.println(moto + "\n");
    	            }

    	            System.out.println("Digite o ano do modelo desejado: ");
    	            int ano = sc.nextInt();
    	            sc.nextLine();

    	            boolean encontrado = false;
    	            // Procura o veículo com o ano especificado
    	            for (Moto moto : repetidosM) {
    	                if (moto.getAno() == ano) {
    	                    System.out.println(moto);
    	                    for(i = 0; i < 10; i++) {
    	    	            	System.out.println("1 - Acelerar");
    	    	            	System.out.println("2 - Frear");
    	    	            	j = sc.nextInt();
    	    	            	sc.nextLine();
    	    	            	if(j==1) {
    	    	            		velocidade += moto.acelerar();
    	    	            	}
    	    	            	else {
    	    	            		velocidade -= moto.frear();
    	    	            	}
    	    	            	moto.painel(velocidade);
    	    	            }
    	                    encontrado = true;
    	                    break;
    	                }
    	            }

    	            if (!encontrado) {
    	                System.out.println("Ano não encontrado para o modelo digitado!");
    	            }
    	        }
    		}
    	}
    }
    
    // Método para cadastrar uma nova moto
    public void cadastrarMoto() {
        System.out.println("Modelo: ");
        String modelo = sc.nextLine();
            
        System.out.println("Marca: ");
        String marca = sc.nextLine();
            
        System.out.println("Ano: ");
        int ano = sc.nextInt();
        sc.nextLine(); // Limpa buffer
            
        System.out.println("Preço: ");
        double preco = sc.nextDouble();
        sc.nextLine(); // Limpa buffer
            
        System.out.println("Cilindradas: ");
        int cc = sc.nextInt();
        sc.nextLine(); // Limpa buffer
            
        System.out.println("Tipo: ");
        String tipo = sc.nextLine();
            
        Moto moto = new Moto(modelo, marca, ano, preco, cc, tipo);
        moto.setVeiculoTipo("moto"); // Define o tipo do veículo
        motos.add(moto); // Adiciona à lista de motos
    }
    
    // Método para cadastrar um novo carro
    public void cadastrarCarro() {
        System.out.println("Modelo: ");
        String modelo = sc.nextLine();
        
        System.out.println("Marca: ");
        String marca = sc.nextLine();
        
        System.out.println("Ano: ");
        int ano = sc.nextInt();
        sc.nextLine(); // Limpa buffer
        
        System.out.println("Preço: ");
        double preco = sc.nextDouble();
        sc.nextLine(); // Limpa buffer
        
        System.out.println("Quantidade de portas: ");
        int portas = sc.nextInt();
        sc.nextLine(); // Limpa buffer
        
        System.out.println("É conversível? (true/false): ");
        boolean conversivel = sc.nextBoolean();
        sc.nextLine(); // Limpa buffer
        
        System.out.println("Tipo de combustível: ");
        String combustivel = sc.nextLine();
        
        Carro carro = new Carro(modelo, marca, ano, preco, portas, conversivel, combustivel);
        carro.setVeiculoTipo("carro"); // Define o tipo do veículo
        carros.add(carro); // Adiciona à lista de carros
    }
    
    // Método para cadastrar uma nova bicicleta
    public void cadastrarBicicleta() {
        System.out.println("Modelo: ");
        String modelo = sc.nextLine();
        
        System.out.println("Marca: ");
        String marca = sc.nextLine();
        
        System.out.println("Ano: ");
        int ano = sc.nextInt();
        sc.nextLine(); // Limpa buffer
        
        System.out.println("Preço: ");
        double preco = sc.nextDouble();
        sc.nextLine(); // Limpa buffer
        
        System.out.println("Número de marchas: ");
        int numMarchas = sc.nextInt();
        sc.nextLine(); // Limpa buffer
        
        System.out.println("Tipo de freio: ");
        String tipoFreio = sc.nextLine();
        
        System.out.println("Tem suspensão? (true/false): ");
        boolean temSuspensao = sc.nextBoolean();
        sc.nextLine(); // Limpa buffer
        
        Bicicleta bicicleta = new Bicicleta(modelo, marca, ano, preco, 
                                          numMarchas, tipoFreio, temSuspensao);
        bicicleta.setVeiculoTipo("bicicleta"); // Define o tipo do veículo
        bicicletas.add(bicicleta); // Adiciona à lista de bicicletas
    }
    
    // Lista todos os veículos disponíveis por categoria
    public void listarVeiculos() {
        System.out.println("\n========== VEÍCULOS DISPONÍVEIS ==========");
        
        // Lista carros disponíveis
        if (!carros.isEmpty()) {
            System.out.println("\n----- CARROS -> " + motos.size() + "-----");
            for (Veiculo carro : carros) {
                System.out.println(carro);
                System.out.printf("Preço Atual: R$ %.2f\n\n", calcularPreco(carro));
            }
        } else {
            System.out.println("\nNenhum carro disponível.");
        }

        // Lista motos disponíveis
        if (!motos.isEmpty()) {
            System.out.println("\n----- MOTOS -> " + motos.size() + "-----");
            for (Veiculo moto : motos) {
                System.out.println(moto);
                System.out.printf("Preço Atual: R$ %.2f\n\n", calcularPreco(moto));
            }
        } else {
            System.out.println("\nNenhuma moto disponível.");
        }

        // Lista bicicletas disponíveis
        if (!bicicletas.isEmpty()) {
            System.out.println("\n----- BICICLETA -> " + motos.size() + "-----");
            for (Veiculo bicicleta : bicicletas) {
                System.out.println(bicicleta);
                System.out.printf("Preço Atual: R$ %.2f\n\n", calcularPreco(bicicleta));
            }
        } else {
            System.out.println("\nNenhuma bicicleta disponível.");
        }
    }
    
    // Cadastra veículos iniciais na loja
    public void cadastroInicial() {
        // Cadastra carros iniciais
        Carro corolla2025 = new Carro("Corolla", "Toyota", 2025, 164.590, 4, false, "Flex");
        corolla2025.setVeiculoTipo("carro");
        carros.add(corolla2025);

        Carro corolla2024 = new Carro("Corolla", "Toyota", 2024, 160590, 4, false, "Flex");
        corolla2024.setVeiculoTipo("carro");
        carros.add(corolla2024);

        // Cadastra motos iniciais
        Moto cgTitan = new Moto("CG 160 Titan", "Honda", 2024, 25520, 163, "OHC");
        cgTitan.setVeiculoTipo("moto");
        motos.add(cgTitan);

        Moto biz = new Moto("BIZ 125 ES", "Honda", 2025, 12500, 124, "Motoneta");
        biz.setVeiculoTipo("moto");
        motos.add(biz);

        // Cadastra bicicletas iniciais
        Bicicleta speed = new Bicicleta("Trilha", "Kaloi", 2024, 3500, 18, "V-Brake", false);
        speed.setVeiculoTipo("bicicleta");
        bicicletas.add(speed);

        Bicicleta urbana = new Bicicleta("Urbana", "Oggi", 2025, 1200, 7, "Tambor", false);
        urbana.setVeiculoTipo("bicicleta");
        bicicletas.add(urbana);
    }
    
    // Inicia a aplicação
    public void iniciar() {
        cadastroInicial(); // Cadastra veículos iniciais
        menu(); // Mostra o menu principal
    }
}