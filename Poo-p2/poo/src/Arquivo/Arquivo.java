package Arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
    public static List<String> ler(String caminho) throws IOException {
        File arquivo = new File(caminho);
        BufferedReader buffRead;
        List<String> linhas = new ArrayList<>();
        String linha = "";

        if (!arquivo.exists() || arquivo.isDirectory()) {
            return linhas;
        }

        buffRead = new BufferedReader(new FileReader(caminho));
        while (true) {
            if (linha != null) {
                if (!linha.equals("")) {
                    linhas.add(linha);
                }
            } else {
                break;
            }

            linha = buffRead.readLine();
        }

        buffRead.close();
        return linhas;
    }

    public static void escrever(String caminho, List<String> linhas) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(caminho));

        for (String linha : linhas) {
            buffWrite.append(linha + "\n");
        }

        buffWrite.close();
    }
}
