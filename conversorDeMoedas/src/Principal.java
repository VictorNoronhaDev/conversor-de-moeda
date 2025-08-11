
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        var servico = new ConsultaCambio();
        var leitura = new Scanner(System.in);


        boolean loop = true;
        while (loop) {
            System.out.println("*************************************************************");
            System.out.println("Seja Bem vindo(a) ao Conversor de Moeda");
            System.out.println("1) De Real (BRL) => Dolar (USD)");
            System.out.println("2) De dolar (USD) => real (BRL)");
            System.out.println("3) De peso Argentino (ARS) => Dolar (USD)");
            System.out.println("4) De dolar (USD) => Peso Argentino (ARS)");
            System.out.println("5) De Peso Colombiano (COP) => Dolar (USD)");
            System.out.println("6) Dolar (USD) => Peso colombiano (COP)");
            System.out.println("7) Sair");
            System.out.println("************************************************************");
            System.out.print("Escolha uma opção valida: ");
            String opcao = leitura.nextLine().trim();
            if (opcao.equals("7")) {
                loop = false;
                break;
            }

            String de, para;
            switch (opcao) {
                case "1" -> {
                    de = "BRL";
                    para = "USD";
                }
                case "2" -> {
                    de = "USD";
                    para = "BRL";
                }
                case "3" -> {
                    de = "ARS";
                    para = "USD";
                }
                case "4" -> {
                    de = "USD";
                    para = "ARS";
                }
                case "5" -> {
                    de = "COP";
                    para = "USD";
                }
                case "6" -> {
                    de = "USD";
                    para = "COP";
                }
                default -> {
                    System.out.println("Opção inválida. ");
                    continue;
                }
            }

            System.out.print("Valor: ");
            String valorString = leitura.nextLine().trim().replace(',', '.');
            double valor;
            try {
                valor = Double.parseDouble(valorString);
                if (Double.isNaN(valor) || valor < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. ");
                continue;
            }

            try {
                Conversao conversao = servico.buscarConversao(de, para, valor);

                String nomeDe = switch (conversao.baseCode()) {
                    case "BRL" -> "real brasileiro (BRL)";
                    case "USD" -> "dólar (USD)";
                    case "ARS" -> "peso argentino (ARS)";
                    case "COP" -> "peso colombiano (COP)";
                    default -> conversao.baseCode();
                };
                String nomePara = switch (conversao.targetCode()) {
                    case "BRL" -> "real brasileiro (BRL)";
                    case "USD" -> "dólar (USD)";
                    case "ARS" -> "peso argentino (ARS)";
                    case "COP" -> "peso colombiano (COP)";
                    default -> conversao.targetCode();
                };


                System.out.println(String.format("Valor %.4f %s corresponde ao valor final de  %.4f %s", valor,nomeDe, conversao.conversionResult(),nomePara));
                System.out.println(String.format("Taxa: 1 %s = %.4f %s", nomeDe, conversao.conversionRate(), nomePara));
                System.out.println(String.format("Atualizado (UTC): %s\n", conversao.atualizadoUtc()));

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage() + " ");
            }
        }
        System.out.println("Conversões finalizadas");
    }
}