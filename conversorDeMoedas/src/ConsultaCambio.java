import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Locale;

public class ConsultaCambio {
    private final String API_KEY = "${API_KEY_EXCHANGE_RATE}";

    private HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private Gson gson = new GsonBuilder().create();

    public ConsultaCambio() {}

    public Conversao buscarConversao(String de, String para, double valor) {
        try {
            String valorFormatado = String.format(Locale.ROOT, "%.2f", valor); // sempre com ponto
            String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%s", API_KEY, de, para, valorFormatado);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();

            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() / 100 != 2) {
                throw new RuntimeException("HTTP " + response.statusCode());
            }

            Conversao conversao = gson.fromJson(response.body(), Conversao.class);
            if (!"success".equalsIgnoreCase(conversao.result())) {
                String tipo = conversao.errorType() == null ? "desconhecido" : conversao.errorType();
                throw new RuntimeException("Erro ExchangeRate: " + tipo);
            }
            return conversao;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Falha na consulta: " + e.getMessage(), e);
        }
    }
}