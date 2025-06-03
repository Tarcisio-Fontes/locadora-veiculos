package projeto; 

import java.util.ArrayList; // Para usar listas dinâmicas (ArrayLists).
import java.util.Scanner; // Para ler a entrada do usuário a partir do console.
import java.lang.Math; // Para usar funções matemáticas como Math.pow() e Math.round().
import java.time.Year; // Para obter o ano atual (usado no cálculo de depreciação).

public class Loja {
    // Listas para armazenar os veículos por categoria.
    private ArrayList<Veiculo> motos = new ArrayList<Veiculo>();
    private ArrayList<Veiculo> carros = new ArrayList<Veiculo>();
    private ArrayList<Veiculo> bicicletas = new ArrayList<Veiculo>();
    
    // Scanner para entrada de dados do usuário.
    private Scanner sc = new Scanner(System.in);  
    
    // Método principal que exibe o menu inicial e controla o fluxo da aplicação.
    public void menu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== LOCADORA DE VEÍCULOS ==="); // Título do menu.
            System.out.println("1 - Cadastrar moto");
            System.out.println("2 - Cadastrar carro");
            System.out.println("3 - Cadastrar bicicleta");
            System.out.println("4 - Comprar veículo");
            System.out.println("5 - Listar disponíveis");
            System.out.println("6 - Calcular serviço extra");
            System.out.println("7 - Teste Drive");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            
            try {
                // Tenta converter a linha lida (String) para um inteiro.
                opcao = Integer.parseInt(sc.nextLine()); 
                switch (opcao) {
                    case 1:
                        cadastrarMoto(); break;
                    case 2:
                        cadastrarCarro(); break;
                    case 3:
                        cadastrarBicicleta(); break;
                    case 4:
                        menuComprar(); break;
                    case 5:
                        listarVeiculos(); break;
                    case 6:
                        System.out.print("Informe o serviço (seguro/tanque cheio/condutor adicional): ");
                        String servico = sc.nextLine();
                        double valor = calcularServicoExtra(servico); // Calcula o valor do serviço.
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
                // Captura a exceção se o usuário digitar algo que não pode ser convertido para número.
                System.out.println("Erro: Digite apenas números!");
            }
        }

        sc.close(); // Fecha o Scanner para liberar os recursos quando o loop do menu termina.
    }
    
    // Menu para selecionar tipo de veículo para alugar.
    public void menuComprar() {
        try {
            int op; 
            System.out.printf("========== COMPRAR ==========\n\n1. Carro\n2. Moto\n3. Bicicleta\n0. Sair\n\n-->");    
            op = sc.nextInt();
            sc.nextLine(); 
            
            switch(op) {
                case 1:  
                	comprarVeiculo(carros);
                    break;
                case 2:
                	comprarVeiculo(motos); 
                    break;
                case 3:
                	comprarVeiculo(bicicletas); 
                    break;
                case 0:  
                    menu();
                default:
                    System.out.println("Opção inválida!");
                    menuComprar();
                    break;
            }
        } catch(java.util.InputMismatchException e) {
            // Captura exceção se o usuário digitar algo que não é um inteiro.
            System.out.println("Foi digitado algo inesperado");
            sc.nextLine(); 
        }
    }
    
    // Método para processar o aluguel de veículos de uma lista específica.
    public void comprarVeiculo(ArrayList<Veiculo> veiculos) {

        ArrayList<Integer> indicesRepetidos = new ArrayList<>(); 
        ArrayList<Veiculo> repetidos = new ArrayList<>(); 
        int count = 0;
        String modelo; 

        listarVeiculos();

        System.out.println("Digite o nome do veículo que deseja comprar: ");
        modelo = sc.nextLine();

        // Procura na lista 'veiculos' por veículos com o modelo informado.
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getModelo().equalsIgnoreCase(modelo)) { // Compara ignorando maiúsculas/minúsculas.
                indicesRepetidos.add(i); // Adiciona o índice do veículo encontrado.
                count++;
            }
        }

        // Tratamento com base em quantos veículos foram encontrados.
        if (count == 0) {
            System.out.println("Modelo não encontrado!");
            return;
        }
        else if (count == 1) { // Se apenas um veículo com o modelo foi encontrado.
            Veiculo veiculo = veiculos.get(indicesRepetidos.get(0)); // Pega o único veículo.
            System.out.println(veiculo); 
            System.out.println("Veículo comprado com sucesso!");
            System.out.println("Preço Atual: " + Math.round(calcularPreco(veiculo))); 
            veiculos.remove(veiculo);
        }
        else { // Se múltiplos veículos com o mesmo modelo foram encontrados.
            System.out.println("========== MODELOS REPETIDOS ==========");
            for (int i : indicesRepetidos) { // Itera sobre os índices dos veículos repetidos.
                Veiculo veiculo = veiculos.get(i); // Pega o veículo pelo índice.
                repetidos.add(veiculo); // Adiciona à lista de 'repetidos' para facilitar a busca pelo ano.
                System.out.println(veiculo + "\n"); 
            }

            System.out.println("Digite o ano do modelo desejado: ");
            int ano = sc.nextInt(); 
            sc.nextLine();

            boolean encontrado = false; 
            
            for (Veiculo veiculo : repetidos) {
                if (veiculo.getAno() == ano) {
                    System.out.println(veiculo);
                    System.out.println("Veículo comprado com sucesso!");
                    System.out.println("Preço Atual: " + Math.round(calcularPreco(veiculo)));
                    veiculos.remove(veiculo); // Remove o veículo dos disponíveis
                    encontrado = true; // Muda pra true, pois o modelo foi encontrado
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Ano não encontrado para o modelo digitado!");
            }
        }
    }
    
    public double calcularPreco(Veiculo veiculo) {
        String veiculoTipo = veiculo.getVeiculoTipo();
        double ano = veiculo.getAno(); 
        double precoBase = veiculo.getPrecoBase(); 

        int taxa = 0; 

        if(veiculoTipo.equals("moto")) {
            taxa = 15; 
        } else if(veiculoTipo.equals("carro")) {
            taxa = 10; 
        } else if(veiculoTipo.equals("bicicleta")) {
            taxa = 5;
        }
        
        // Fórmula: PrecoAtual = PrecoBase * (1 - taxa/100) ^ (AnoAtual - AnoFabricacao)
        return precoBase * Math.pow((1 - (taxa/100.0)), Year.now().getValue() - ano);
    }
    
    // Calcula o valor de serviços extras.
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
            return 0; // Retorna 0 se o serviço não for reconhecido.
        }
    }
    
    // Método para simular um test drive com carros ou motos.
    public void testeDrive() {
        // Listas para lidar com modelos repetidos, específicas para Carro e Moto para usar seus métodos.
        ArrayList<Integer> indicesRepetidos = new ArrayList<>();
        ArrayList<Carro> repetidosC = new ArrayList<>(); 
        ArrayList<Moto> repetidosM = new ArrayList<>(); 
        String modelo; 
        double velocidade = 0; // Velocidade atual do veículo no test drive.
        int count = 0; // Contador de modelos encontrados.
        int op; // Opção do usuário (tipo de veículo para test drive).
        int i; 
        int j = -1; 

        System.out.println("Deseja fazer o teste drive com qual tipo de veiculo? ");
        System.out.println("1 - Carro");
        System.out.println("2 - Moto");
        System.out.println("0 - Encerrar test drive");
        op = sc.nextInt();
        sc.nextLine(); // Consome a quebra de linha.

        if(op == 0) {
            System.out.println("Encerrando teste drive.");
            return;
        }

        // Lógica para TEST DRIVE DE CARRO
        if(op == 1) {
            if(!carros.isEmpty()) { // Verifica se há carros cadastrados.
                for(Veiculo carro : carros) { // Lista todos os carros.
                    System.out.println(carro);
                }
                System.out.println("Qual modelo? ");
                modelo = sc.nextLine(); 

                // Verifica se existe o modelo digitado e conta quantos existem.
                for (i = 0; i < carros.size(); i++) { 
                    if (carros.get(i).getModelo().equalsIgnoreCase(modelo)) {
                        indicesRepetidos.add(i);
                        count++;
                    }
                }

                if (count == 0) { // Nenhum modelo encontrado.
                    System.out.println("Modelo não encontrado!");
                    return;
                }
                else if (count == 1) { // Apenas um carro com esse modelo.
                    Carro carro = (Carro) carros.get(indicesRepetidos.get(0)); 
                    System.out.println(carro);
                    velocidade = 0;
                    j = -1;
                    
                    while (j != 0) { 
                        System.out.println("1 - Acelerar");
                        System.out.println("2 - Frear");
                        System.out.println("0 - Sair do test drive");
                        j = sc.nextInt();
                        sc.nextLine(); 
                        
                        if(j == 0) {
                            System.out.println("Saindo do test drive.");
                            break; 
                        } else if(j == 1) {
                            velocidade += carro.acelerar(); 
                        } else if(j == 2) {
                            velocidade -= carro.frear();
                            if (velocidade < 0) velocidade = 0; // Velocidade não pode ser negativa.
                        } else {
                            System.out.println("Opção inválida!");
                            continue; // Volta para o início do loop de test drive.
                        }
                        carro.painel(velocidade); // Mostra o painel do carro com a velocidade atual.
                    }
                }
                else { // Múltiplos carros com o mesmo modelo.
                    System.out.println("========== MODELOS REPETIDOS ==========");
                    for (int z : indicesRepetidos) { // Itera sobre os índices dos carros repetidos.
                        Carro carro = (Carro)carros.get(z);
                        repetidosC.add(carro);
                        System.out.println(carro + "\n");
                    }

                    System.out.println("Digite o ano do modelo desejado: ");
                    int ano = sc.nextInt();
                    sc.nextLine(); // Consome a quebra de linha.

                    boolean encontrado = false;
                    for (Carro carro : repetidosC) { //Procura o carro pelo ano na lista de repetidos.
                        if (carro.getAno() == ano) {
                            System.out.println(carro);
                            velocidade = 0;
                            j = -1; 
                            while (j != 0) {
                                System.out.println("1 - Acelerar");
                                System.out.println("2 - Frear");
                                System.out.println("0 - Sair do test drive");
                                j = sc.nextInt();
                                sc.nextLine();
                                if(j == 0) {
                                    System.out.println("Saindo do test drive.");
                                    break;
                                } else if(j == 1) {
                                    velocidade += carro.acelerar();
                                } else if(j == 2) {
                                    velocidade -= carro.frear();
                                    if (velocidade < 0) velocidade = 0;
                                } else {
                                    System.out.println("Opção inválida!");
                                    continue;
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
            } else {
                System.out.println("Nenhum carro cadastrado.");
            }
        }
        // Lógica para TEST DRIVE DE MOTO (muito similar à de Carro)
        else if(op == 2) { //Moto
            if(!motos.isEmpty()) { // Verifica se há motos cadastradas.
                for(Veiculo moto : motos) { // Lista todas as motos.
                    System.out.println(moto);
                }
                System.out.println("Qual modelo? ");
                modelo = sc.nextLine(); // Lê o modelo da moto.

                // Limpa as listas e reseta o contador para a nova busca
                indicesRepetidos.clear();
                repetidosM.clear(); // Usa a lista específica para motos
                count = 0;

                for (i = 0; i < motos.size(); i++) { // Verifica se existe o modelo e conta.
                    if (motos.get(i).getModelo().equalsIgnoreCase(modelo)) {
                        indicesRepetidos.add(i);
                        count++;
                    }
                }

                if (count == 0) {
                    System.out.println("Modelo não encontrado!");
                    return;
                } else if (count == 1) { // Apenas uma moto com esse modelo.
                    Moto moto = (Moto) motos.get(indicesRepetidos.get(0)); // Cast para Moto.
                    System.out.println(moto);
                    velocidade = 0;
                    j = -1;
                    while (j != 0) { // Loop de interação do test drive.
                        System.out.println("1 - Acelerar");
                        System.out.println("2 - Frear");
                        System.out.println("0 - Sair do test drive");
                        j = sc.nextInt();
                        sc.nextLine();
                        if(j == 0) {
                            System.out.println("Saindo do test drive.");
                            break;
                        } else if(j == 1) {
                            velocidade += moto.acelerar(); // Chama acelerar() da Moto.
                        } else if(j == 2) {
                            velocidade -= moto.frear(); // Chama frear() da Moto.
                            if (velocidade < 0) velocidade = 0;
                        } else {
                            System.out.println("Opção inválida!");
                            continue;
                        }
                        moto.painel(velocidade); // Mostra painel da Moto.
                    }
                } else { // Múltiplas motos com o mesmo modelo.
                    System.out.println("========== MODELOS REPETIDOS ==========");
                    for (int z : indicesRepetidos) {
                        Moto moto = (Moto) motos.get(z); // Cast para Moto.
                        repetidosM.add(moto); // Adiciona à lista de motos repetidas.
                        System.out.println(moto + "\n");
                    }

                    System.out.println("Digite o ano do modelo desejado: ");
                    int ano = sc.nextInt();
                    sc.nextLine();

                    boolean encontrado = false;
                    for (Moto moto : repetidosM) { // Procura pelo ano na lista de motos repetidas.
                        if (moto.getAno() == ano) {
                            System.out.println(moto);
                            velocidade = 0;
                            j = -1;
                            while (j != 0) { // Loop de interação do test drive (similar ao anterior).
                                System.out.println("1 - Acelerar");
                                System.out.println("2 - Frear");
                                System.out.println("0 - Sair do test drive");
                                j = sc.nextInt();
                                sc.nextLine();
                                if(j == 0) {
                                    System.out.println("Saindo do test drive.");
                                    break;
                                } else if(j == 1) {
                                    velocidade += moto.acelerar();
                                } else if(j == 2) {
                                    velocidade -= moto.frear();
                                    if (velocidade < 0) velocidade = 0;
                                } else {
                                    System.out.println("Opção inválida!");
                                    continue;
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
            } else {
                System.out.println("Nenhuma moto cadastrada.");
            }
        } else {
            System.out.println("Opção inválida!"); // Se a opção inicial do test drive não for 0, 1 ou 2.
        }
    }

    // Método para cadastrar uma nova moto.
    public void cadastrarMoto() {
        System.out.println("Modelo: ");
        String modelo = sc.nextLine();
            
        System.out.println("Marca: ");
        String marca = sc.nextLine();
            
        System.out.println("Ano: ");
        int ano = sc.nextInt();
        sc.nextLine(); 
            
        System.out.println("Preço: ");
        double preco = sc.nextDouble();
        sc.nextLine(); 
            
        System.out.println("Cilindradas: ");
        int cc = sc.nextInt();
        sc.nextLine();
            
        System.out.println("Tipo (ex: Esportiva, Custom): "); 
        String tipo = sc.nextLine();
            
        Moto moto = new Moto(modelo, marca, ano, preco, cc, tipo);
        moto.setVeiculoTipo("moto");
        motos.add(moto); 
        System.out.println("Moto cadastrada com sucesso!");
    }
    
    // Método para cadastrar um novo carro.
    public void cadastrarCarro() {
        System.out.println("Modelo: ");
        String modelo = sc.nextLine();
        
        System.out.println("Marca: ");
        String marca = sc.nextLine();
        
        System.out.println("Ano: ");
        int ano = sc.nextInt();
        sc.nextLine(); // Limpa buffer.
        
        System.out.println("Preço: ");
        double preco = sc.nextDouble();
        sc.nextLine(); // Limpa buffer.
        
        System.out.println("Quantidade de portas: ");
        int portas = sc.nextInt();
        sc.nextLine(); // Limpa buffer.
        
        System.out.println("É conversível? (true/false): ");
        boolean conversivel = sc.nextBoolean();
        sc.nextLine(); // Limpa buffer.
        
        System.out.println("Tipo de combustível: ");
        String combustivel = sc.nextLine();
        
        // Cria um novo objeto Carro.
        Carro carro = new Carro(modelo, marca, ano, preco, portas, conversivel, combustivel);
        carro.setVeiculoTipo("carro"); // Define o tipo para cálculo de preço.
        carros.add(carro); // Adiciona à lista de carros.
        System.out.println("Carro cadastrado com sucesso!");
    }
    
    // Método para cadastrar uma nova bicicleta.
    public void cadastrarBicicleta() {
        System.out.println("Modelo: ");
        String modelo = sc.nextLine();
        
        System.out.println("Marca: ");
        String marca = sc.nextLine();
        
        System.out.println("Ano: ");
        int ano = sc.nextInt();
        sc.nextLine(); // Limpa buffer.
        
        System.out.println("Preço: ");
        double preco = sc.nextDouble();
        sc.nextLine(); // Limpa buffer.
        
        System.out.println("Número de marchas: ");
        int numMarchas = sc.nextInt();
        sc.nextLine(); // Limpa buffer.
        
        System.out.println("Tipo de freio: ");
        String tipoFreio = sc.nextLine();
        
        System.out.println("Tem suspensão? (true/false): ");
        boolean temSuspensao = sc.nextBoolean();
        sc.nextLine(); // Limpa buffer.
        
        // Cria um novo objeto Bicicleta.
        Bicicleta bicicleta = new Bicicleta(modelo, marca, ano, preco, 
                                            numMarchas, tipoFreio, temSuspensao);
        bicicleta.setVeiculoTipo("bicicleta"); 
        bicicletas.add(bicicleta); 
        System.out.println("Bicicleta cadastrada com sucesso!");
    }
    
    // Lista todos os veículos disponíveis por categoria, mostrando o preço atual calculado.
    public void listarVeiculos() {
        System.out.println("\n========== VEÍCULOS DISPONÍVEIS ==========");
        
        // Lista carros disponíveis.
        if (!carros.isEmpty()) {
            // ERRO SUTIL: Aqui deveria ser carros.size() e não motos.size()
            System.out.println("\n----- CARROS -> " + carros.size() + "-----"); 
            for (Veiculo carro : carros) {
                System.out.println(carro); // Usa o toString() do objeto Carro.
                System.out.printf("Preço Atual: R$ %.2f\n\n", calcularPreco(carro));
            }
        } else {
            System.out.println("\nNenhum carro disponível.");
        }

        // Lista motos disponíveis.
        if (!motos.isEmpty()) {
            System.out.println("\n----- MOTOS -> " + motos.size() + "-----");
            for (Veiculo moto : motos) {
                System.out.println(moto); // Usa o toString() do objeto Moto.
                System.out.printf("Preço Atual: R$ %.2f\n\n", calcularPreco(moto));
            }
        } else {
            System.out.println("\nNenhuma moto disponível.");
        }

        // Lista bicicletas disponíveis.
        if (!bicicletas.isEmpty()) {
            // ERRO SUTIL: Aqui deveria ser bicicletas.size() e não motos.size()
            System.out.println("\n----- BICICLETA -> " + bicicletas.size() + "-----"); 
            for (Veiculo bicicleta : bicicletas) {
                System.out.println(bicicleta); // Usa o toString() do objeto Bicicleta.
                System.out.printf("Preço Atual: R$ %.2f\n\n", calcularPreco(bicicleta));
            }
        } else {
            System.out.println("\nNenhuma bicicleta disponível.");
        }
    }
    
    // Cadastra veículos iniciais na loja para demonstração ou testes.
    public void cadastroInicial() {
        // Cadastra carros iniciais
        Carro corolla2025 = new Carro("Corolla", "Toyota", 2025, 164590, 4, false, "Flex");
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
        
        System.out.println("Cadastro inicial de veículos realizado."); // Feedback
    }
    
    // Método para iniciar a aplicação.
    // Este seria o método chamado a partir de um `main` em outra classe, por exemplo.
    public void iniciar() {
        cadastroInicial(); // Carrega os veículos iniciais.
        menu(); // Mostra o menu principal para o usuário interagir.
    }
}
