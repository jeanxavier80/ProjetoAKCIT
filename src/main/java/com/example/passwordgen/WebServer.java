package com.example.passwordgen;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class WebServer {
    @SuppressWarnings("unused")
    private static final int DEFAULT_PORT = 4567;

    public static void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/generate", new GenerateHandler());
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        System.out.println("Servidor iniciado em http://localhost:" + port);
    }

    private static final class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] response = page("", "").getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        }
    }

    private static final class GenerateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> queryParams = parseQuery(exchange.getRequestURI());
            String message;
            String password = "";
            int length = 12;
            boolean includeUpper = "on".equals(queryParams.get("upper"));
            boolean includeLower = "on".equals(queryParams.get("lower"));
            boolean includeDigits = "on".equals(queryParams.get("digits"));
            boolean includeSymbols = "on".equals(queryParams.get("symbols"));

            try {
                if (queryParams.containsKey("length")) {
                    length = Integer.parseInt(queryParams.get("length"));
                }
                password = PasswordGenerator.generate(length, includeUpper, includeLower, includeDigits, includeSymbols);
                message = "Senha gerada com sucesso:";
            } catch (Exception e) {
                message = "Erro: " + e.getMessage();
            }

            byte[] response = page(message, password).getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        }

        private Map<String, String> parseQuery(URI uri) {
            Map<String, String> queryParams = new HashMap<>();
            String query = uri.getRawQuery();
            if (query == null || query.isBlank()) {
                return queryParams;
            }

            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] parts = pair.split("=", 2);
                if (parts.length == 2) {
                    queryParams.put(parts[0], parts[1]);
                }
            }
            return queryParams;
        }
    }

    private static String page(String message, String password) {
        return "<!DOCTYPE html>"
                + "<html lang=\"pt-BR\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Gerador de Senhas Seguras</title>"
                + "<style>body{font-family:Arial,sans-serif;background:#f4f7fb;color:#333;margin:0;padding:0}"
                + ".container{max-width:520px;margin:48px auto;padding:24px;background:#fff;border-radius:12px;box-shadow:0 16px 40px rgba(0,0,0,0.08)}"
                + "h1{margin-top:0;font-size:1.9rem;color:#1d2c4d}label{display:block;margin:.75rem 0 .25rem;font-weight:600}input[type=number],button{width:100%;padding:12px 14px;margin:.25rem 0 1rem;border:1px solid #ccd6e0;border-radius:8px;font-size:1rem}"
                + "button{background:#3b82f6;color:#fff;border:none;cursor:pointer}button:hover{background:#2563eb}.checkbox-group{display:grid;grid-template-columns:repeat(2,minmax(120px,1fr));gap:12px}.message{padding:14px 16px;border-radius:10px;background:#eef2ff;color:#1e40af;margin-bottom:1rem;word-break:break-all}.footer{margin-top:1.5rem;font-size:.92rem;color:#64748b;text-align:center}</style>"
                + "</head><body><div class=\"container\"><h1>Gerador de Senhas Seguras</h1>"
                + (message.isBlank() ? "" : "<div class=\"message\"><strong>" + escapeHtml(message) + "</strong><br>" + escapeHtml(password) + "</div>")
                + "<form action=\"/generate\" method=\"get\">"
                + "<label for=\"length\">Tamanho da senha</label><input type=\"number\" id=\"length\" name=\"length\" value=\"12\" min=\"4\" max=\"64\" required>"
                + "<div class=\"checkbox-group\">"
                + "<label><input type=\"checkbox\" name=\"upper\" checked> Letras maiúsculas</label>"
                + "<label><input type=\"checkbox\" name=\"lower\" checked> Letras minúsculas</label>"
                + "<label><input type=\"checkbox\" name=\"digits\" checked> Dígitos</label>"
                + "<label><input type=\"checkbox\" name=\"symbols\" checked> Símbolos</label>"
                + "</div><button type=\"submit\">Gerar senha</button></form>"
                + "<p class=\"footer\">Use o formulário para ajustar o comprimento e selecionar os tipos de caractere.</p>"
                + "</div></body></html>";
    }

    private static String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
}
