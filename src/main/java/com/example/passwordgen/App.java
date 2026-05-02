package com.example.passwordgen;

public class App {
    public static void main(String[] args) {
        int port = 4567;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Porta inválida, usando a porta padrão 4567.");
            }
        }

        try {
            WebServer.start(port);
        } catch (Exception e) {
            System.err.println("Não foi possível iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
