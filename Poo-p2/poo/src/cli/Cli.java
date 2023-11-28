package cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Arquivo.Arquivo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import entidades.evento.Evento;
import entidades.evento.Exposicao;
import entidades.evento.Jogo;
import entidades.evento.Show;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

import entidades.ingresso.Ingresso;
import entidades.ingresso.TipoIngresso;
import entidades.ingresso.IngExposicao;
import entidades.ingresso.IngJogo;
import entidades.ingresso.IngShow;

public class Cli {

    public static List<Evento> eventos = new ArrayList<>();

    

    public static int executar() {
        Evento evento = null;
        Ingresso ingresso = null;
        Scanner leitor = new Scanner(System.in);
        int opcao;


        System.out.println("Seja bem-vindo ao programa de venda de ingressos de eventos!");

        while (true) {
            menu();
            opcao = leitor.nextInt();
            switch (opcao) {
                case 1:
                    evento = cadastrarEvento(leitor);
                    System.out.println("Evento cadastrado com sucesso!");
                    break;
                case 2:
                    exibirEvento(evento);
                    break;
                case 3:
                    exibirIngressosRestantes(evento);
                    break;
                case 4:
                    ingresso = venderIngresso(evento, leitor);
                    break;

                case 5:

                    buscarEvento(eventos);
                    break;
                case 6:
                    buscarTodosEventos(eventos);
                    break;
                case 7:
                    removerEvento(eventos);

                    break;
                case 8:
                    exibirIngresso(ingresso);
                    break;
                case 9:
                    atualizarEvento(evento, leitor);
                    break;
                    
                default:
                    leitor.close();
                    System.out.println("Volte sempre!");
                    return 0;
            }
        }
    }
    
    private static void menu() {
        System.out.println("\nDigite a opção desejada ou qualquer outro valor para sair:");
        System.out.println("1 - Cadastrar um novo evento;");
        System.out.println("2 - Exibir evento cadastrado;");
        System.out.println("3 - Exibir ingressos restantes;");
        System.out.println("4 - Vender um ingresso;");
        System.out.println("5 - Buscar evento pelo nome; ");
        System.out.println("6 - Mostrar todos os eventos cadastrados");
        System.out.println("7 - Remover um evento");
        System.out.println("8 - Exibir último ingresso vendido;");
        System.out.println("9 - Atualizar evento cadastrado");
        
    }
    private static void atualizarEvento(Evento evento, Scanner leitor) {
        if (evento == null) {
            System.out.println("Nenhum evento cadastrado para atualizar!");
            return;
        }
    
        System.out.print("Informe a nova data do evento (dd/MM/yyyy): ");
        String novaDataTexto = leitor.next();
        LocalDate novaData;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            novaData = LocalDate.parse(novaDataTexto, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Por favor, tente novamente com o formato dd/MM/yyyy.");
            return;
        }
        System.out.print("Informe o novo local do evento: ");
        String novoLocal = leitor.next();
    
        
        evento.setData(novaData);
        evento.setlocal(novoLocal);
    
        System.out.println("Evento atualizado com sucesso!");
    }

    private static void exibirIngresso(Ingresso ingresso) {
        if (ingresso == null) {
            System.out.println("Nenhum ingresso foi vendido!");
        } else {
            System.out.println(ingresso);
        }
    }

    private static Ingresso venderIngresso(Evento evento, Scanner leitor) {
        if (evento == null) {
            System.out.println("Evento ainda não foi cadastrado!");
            return null;
        }
        String tipo;
        TipoIngresso tipoIngresso;
        int quantidade;
        Ingresso ingresso;

        System.out.print("Informe o tipo do ingresso (meia ou inteira): ");
        tipo = leitor.next();
        if (!(tipo.equals("meia") || tipo.equals("inteira"))) {
            System.out.println("Tipo selecionado inválido!");
            return null;
        }

        tipoIngresso = tipo.equals("meia") ? TipoIngresso.MEIA : TipoIngresso.INTEIRA;

        System.out.print("Informe quantos ingressos você deseja: ");
        quantidade = leitor.nextInt();

        if (!evento.isIngressoDisponivel(tipoIngresso, quantidade)) {
            System.out.println("Não há ingressos disponíveis desse tipo!");
            return null;
        }

        if (evento instanceof Jogo) {
            int percentual;

            System.out.print("Informe o percentual do desconto de sócio torcedor: ");
            percentual = leitor.nextInt();
            ingresso = new IngJogo(evento, tipoIngresso, percentual);
        } else if (evento instanceof Show) {
            String localizacao;

            System.out.print("Informe a localização do ingresso (pista ou camarote): ");
            localizacao = leitor.next();

            if (!(localizacao.equals("pista") || localizacao.equals("camarote"))) {
                System.out.println("Localização inválida!");
                return null;
            }
            ingresso = new IngShow(evento, tipoIngresso, localizacao);
        } else {
            String desconto;

            System.out.print("Informe se possui desconto social (s/n): ");
            desconto = leitor.next();

            ingresso = new IngExposicao(evento, tipoIngresso, desconto.equals("s"));
        }

        evento.venderIngresso(tipoIngresso, quantidade);
        System.out.println("Ingresso vendido com sucesso!");
        return ingresso;
    }

    private static void exibirIngressosRestantes(Evento evento) {
        if (evento == null) {
            System.out.println("Evento ainda não foi cadastrado!");
        } else {
            System.out.println("Ingressos tipo meia restantes: " + evento.getIngressosMeia());
            System.out.println("Ingressos tipo inteira restantes: " + evento.getIngressosInteira());
        }
    }

    private static void exibirEvento(Evento evento) {
        if (evento == null) {
            System.out.println("Evento ainda não foi cadastrado!");
        } else {
            System.out.println(evento);
        }
    }

    private static void buscarEvento(List<Evento> eventos) {
        Evento eventoBuscado = null;
        Scanner leitor = new Scanner(System.in);
    
        String nomeE;
        System.out.println("Digite o nome do evento que deseja buscar");
        nomeE = leitor.nextLine();
    
        for (Evento evento : eventos) {
            if (evento.getNome().equalsIgnoreCase(nomeE)) {
                eventoBuscado = evento;
                break;
            }
        }
    
        if (eventoBuscado != null) {
            System.out.println("Evento encontrado: " + eventoBuscado);
        } else {
            System.out.println("Nenhum evento encontrado com o nome fornecido.");
        }
    }
    private static void buscarTodosEventos(List<Evento> eventos) {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado!");
        } else {
            System.out.println("Eventos cadastrados:");
            for (Evento evento : eventos) {
                System.out.println(evento);
            }
        }
    }    

    private static void removerEvento(List<Evento> eventos) {
        Scanner leitor = new Scanner(System.in);

        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado para remover!");
            return;
        }
    
        System.out.println("Digite o nome do evento que deseja remover:");
        String nomeE = leitor.nextLine();
    
        Evento eventoRemovido = null;
        for (Evento evento : eventos) {
            if (evento.getNome().equalsIgnoreCase(nomeE)) {
                eventoRemovido = evento;
                break;
            }
        }
    
        if (eventoRemovido != null) {
            eventos.remove(eventoRemovido);
            System.out.println("Evento removido com sucesso!");
        } else {
            System.out.println("Nenhum evento encontrado com o nome fornecido.");
        }
    }
    

    private static Evento cadastrarEvento(Scanner leitor) {
        Evento novoEvento = null;
        String nome, dataTexto, local, tipo;
        LocalDate data;
        int ingMeia, ingInteira;
        double preco;
    
        System.out.print("Informe o nome do evento: ");
        nome = leitor.next();
        System.out.print("Informe a data do evento (dd/MM/yyyy): ");
        dataTexto = leitor.next();
        System.out.print("Informe o local do evento: ");
        local = leitor.next();
        System.out.print("Informe o número de entradas tipo meia: ");
        ingMeia = leitor.nextInt();
        System.out.print("Informe o número de entradas tipo inteira: ");
        ingInteira = leitor.nextInt();
        System.out.print("Informe o preço cheio do evento: ");
        preco = leitor.nextDouble();
        System.out.print("Informe o tipo do evento (show, jogo ou exposição): ");
        tipo = leitor.next();

        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            data = LocalDate.parse(dataTexto, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Por favor, tente novamente com o formato dd/MM/yyyy.");
            return null; 
        }
    

        if (tipo.equals("show")) {
            String artista, genero;

            System.out.print("Informe o nome do artista: ");
            artista = leitor.next();
            System.out.print("Informe o gênero do show: ");
            genero = leitor.next();

            novoEvento = new Show(nome, data, local, ingMeia, ingInteira, preco, artista, genero);

            
        }

        else if (tipo.equals("jogo")) {
            String esporte, casa, adversario;

            System.out.print("Informe o esporte: ");
            esporte = leitor.next();
            System.out.print("Informe a equipe da casa: ");
            casa = leitor.next();
            System.out.print("Informe a equipe adversária: ");
            adversario = leitor.next();

            novoEvento = new Jogo(nome, data, local, ingMeia, ingInteira, preco, esporte, casa, adversario);

            
        }
        else if (tipo.equals("exposicao")) {
            int idadeMin, duracao;
    
            System.out.print("Informe a idade mínima para entrar na exposição: ");
            idadeMin = leitor.nextInt();
            System.out.print("Informe a duração em dias da exposição: ");
            duracao = leitor.nextInt();
    
            novoEvento = new Exposicao(nome, data, local, ingMeia, ingInteira, preco, idadeMin, duracao);
        }
        

        if (novoEvento != null) {
            eventos.add(novoEvento);
            salvarEventos();
            System.out.println("Evento cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao criar o evento.");
        }
        
        return novoEvento;
    }

    public static void salvarEventos() {
        try {
            PrintWriter writer = new PrintWriter(new File("eventos.txt"));
            for (Evento evento : eventos) {
                writer.println(evento.toString());
            }
            writer.close();
            System.out.println("Eventos salvos com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao salvar os eventos: " + e.getMessage());
        }
            
}

}

