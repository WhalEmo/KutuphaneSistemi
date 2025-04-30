import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class KullaniciAPI {

    public static KullaniciServisi kullaniciServisi = new KullaniciServisi();

    public static void main(String[] args) throws IOException {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(1453), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        server.createContext("/giris", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                String email = params[0].split("=")[1];
                email = URLDecoder.decode(email, StandardCharsets.UTF_8);
                String sifre = params[1].split("=")[1];
                sifre = URLDecoder.decode(sifre,StandardCharsets.UTF_8);

                int ID = kullaniciServisi.KullaniciGiris(email, sifre);

                String response;
                if (ID != -1) {
                    response = "Giriş başarılı! Kullanıcı: " + ID;
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                }
                else {
                    response = "E-posta veya şifre hatalı!";
                    exchange.sendResponseHeaders(500, response.getBytes().length);
                }
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        server.createContext("/kayit", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String requestBody = sb.toString();
                String[] fields = requestBody.split("&");

                String ad = fields[0].split("=")[1];
                ad = URLDecoder.decode(ad,StandardCharsets.UTF_8);
                String soyad = fields[1].split("=")[1];
                soyad = URLDecoder.decode(soyad,StandardCharsets.UTF_8);
                String email = fields[2].split("=")[1];
                email = URLDecoder.decode(email,StandardCharsets.UTF_8);
                String sifre = fields[3].split("=")[1];
                sifre = URLDecoder.decode(sifre,StandardCharsets.UTF_8);
                String dogumTarihi = fields[5].split("=")[1];
                String cinsiyetStr = fields[6].split("=")[1];
                char cinsiyet = cinsiyetStr.charAt(0);
                int id = 0;
                Kullanici kullanici = new Kullanici(
                        null,
                        dogumTarihi,
                        cinsiyet,
                        sifre,
                        email,
                        soyad,
                        ad,
                        id
                );
                boolean basariliMi = kullaniciServisi.KullaniciKaydi(kullanici);
                String response;
                if(basariliMi){
                    response = "Kayıt başarılı!";
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                }
                else{
                    response = "Kayıt başarısız!!";
                    exchange.sendResponseHeaders(500, response.getBytes().length);
                }
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        server.start();
        System.out.println("Kullanici API server'ı http://localhost:8080 adresinde çalışıyor...");
    }
}
