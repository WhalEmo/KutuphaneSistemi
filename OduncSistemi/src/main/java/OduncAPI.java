import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class OduncAPI {
    public static OduncServisi oduncServisi = new OduncServisi();
    public static void main(String[] args) {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(1808),0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.createContext("/oduncal",(islem->{
            if("POST".equals(islem.getRequestMethod())){
                InputStreamReader isr = new InputStreamReader(islem.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                StringBuilder veriler = new StringBuilder();
                String satir = br.readLine();
                while(satir != null){
                    veriler.append(satir);
                    satir = br.readLine();
                }
                String gelenVeriler = veriler.toString();
                String[] parcalar = gelenVeriler.split("&");
                Odunc odunc = new Odunc(
                        0,
                        Integer.valueOf(oduncServisi.UTF8(parcalar[0].split("=")[1])),
                        Integer.valueOf(oduncServisi.UTF8(parcalar[1].split("=")[1])),
                        null,
                        null,
                        false
                );
                try {
                    HttpClient istemci = HttpClient.newHttpClient();
                    HttpRequest istek = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:1453/giris?email="+odunc.getKullaniciID()+"sifre=145316"))
                            .GET()
                            .build();
                }catch (Exception e){
                    System.out.println("Hata: "+e.getMessage());
                }
            }
        }));
    }
}
