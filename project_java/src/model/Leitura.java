package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Leitura {

    // variaveis de instancia
    private List<String> linhas;

    /**
     * Construtor por omissão do tipo Leitura.
     */
    public Leitura() {
        this.linhas = new ArrayList<>();
    }

    /**
     * Método que devolve a lista de linhas.
     * @return Linhas
     */
    public List<String> getLinhas(){
        return this.linhas;
    }

    /**
     * Método que lê um ficheiro em modo texto e guarda todas as linhas.
     * @param file Path do ficheiro
     */
    public void loadFile(String file) throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader("db/" + file));
        String linha;
        while ( (linha = buffer.readLine()) != null) this.linhas.add(linha);
        this.linhas.remove(0);
        buffer.close();
    }
}
