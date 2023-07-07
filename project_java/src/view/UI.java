package view;

import java.util.Scanner;

public class UI {

    public static int Menu() {
        StringBuilder sb = new StringBuilder("\nConsultas estatísticas.\n");
        sb.append(" 1) Dados referentes aos ficheiros\n");
        sb.append(" 2) Dados referentes ás estruturas\n");
        sb.append("Consultas interativas (queries).\n");
        sb.append(" 3) Query 1\n");
        sb.append(" 4) Query 2\n");
        sb.append(" 5) Query 3\n");
        sb.append(" 6) Query 4\n");
        sb.append(" 7) Query 5\n");
        sb.append(" 8) Query 6\n");
        sb.append(" 9) Query 7\n");
        sb.append("10) Query 8\n");
        sb.append("11) Query 9\n");
        sb.append("12) Query 10\n");
        sb.append("Ficheiros.\n");
        sb.append("13) Carregar ficheiros\n");
        sb.append("14) Gravar em ficheiro\n");
        sb.append("Sair do programa.\n");
        sb.append(" 0) Sair\n\n");
        sb.append("Escolha uma opção: ");
        System.out.print(sb);
        Scanner scanner = new Scanner(System.in);
        int param = scanner.nextInt();
        while(param < 0 || param > 14) {
            ErrorMsg(1);
            param = Menu();
        }
        return param;
    }

    public static int AskFileNamePref() {
        StringBuilder sb = new StringBuilder("\nOpções de nome do ficheiro.\n");
        sb.append("1) Usar ficheiros padrão.\n");
        sb.append("2) Usar ficheiros personalizados.\n");
        sb.append("Escolha uma opção: ");
        System.out.print(sb);
        Scanner scanner = new Scanner(System.in);
        int param = scanner.nextInt();
        while(param < 1 || param > 2) {
            ErrorMsg(1);
            param = AskFileNamePref();
        }
        return param;
    }

    public static String[] indicarCaminho(){
        StringBuilder sb = new StringBuilder("\nCarregamento de ficheiros.\n");
        sb.append("Certifique-se que os ficheiros se encontram na diretoria db.\n");
        sb.append("Indique o nome dos ficheiros, respeitando o seguinte formato:\n");
        sb.append("users.txt businesses.txt reviews.txt\n");
        System.out.println(sb);
        Scanner scanner = new Scanner(System.in);
        String inputsJuntos = scanner.nextLine();
        String[] inputs = inputsJuntos.split(" ");
        while(inputs.length < 3) {
            ErrorMsg(2);
            inputs = indicarCaminho();
        }
        return inputs;
    }

    public static int AskFileMode(){
        StringBuilder sb = new StringBuilder("\nCarregamento de ficheiros.\n");
        sb.append("1) Ficheiro de texto.\n");
        sb.append("2) Ficheiro de objetos.\n");
        sb.append("Escolha uma opção: ");
        System.out.print(sb);
        Scanner scanner = new Scanner(System.in);
        int param = scanner.nextInt();
        while(param < 1 || param > 2) {
            ErrorMsg(1);
            param = AskFileMode();
        }
        return param;
    }

    public static String AskFileName(){
        System.out.print("\nInsira o nome do ficheiro: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int AskMes(){
        System.out.print("Insira um número entre 1 e 12 de forma a representar o mês: ");
        Scanner scanner = new Scanner(System.in);
        int param = scanner.nextInt();
        while(param < 1 || param > 12) {
            ErrorMsg(1);
            param = AskMes();
        }
        return param;
    }

    public static int AskAno(){
        System.out.print("Insira um ano: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static String AskUserId(){
        System.out.print("Insira um user id: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String AskBusinessId(){
        System.out.print("Insira um business id: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static Integer AskNum(){
        System.out.print("Insira número: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void ErrorMsg(int error){
        switch (error) {
            case 1 -> System.out.println("Opção indisponível, tente novamente.\n");
            case 2 -> System.out.println("Formato desconhecido, tente novamente.\n");
        }
    }

    public static void SuccessMsg(int msg){
        switch (msg){
            case 1 -> System.out.println("Ficheiros carregados com sucesso.\n");
            case 2 -> System.out.println("Estrutura gravada com sucesso.\n");
        }
    }
}
