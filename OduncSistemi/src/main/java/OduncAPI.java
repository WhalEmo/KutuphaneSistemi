import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
                        0,
                        Integer.valueOf(oduncServisi.UTF8(parcalar[0].split("=")[1])),
                        null,
                        null,
                        false
                );
                System.out.println("Y: "+odunc.getKitapID());
                try {
                    String email = oduncServisi.UTF8(parcalar[1].split("=")[1]);
                    String sifre = oduncServisi.UTF8(parcalar[2].split("=")[1]);
                    String url = "http://kullanici-servisi:1453/giris?email=" + email + "&sifre=" + sifre;

                    HttpClient istemci = HttpClient.newHttpClient();
                    HttpRequest istek = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .GET()
                            .build();
                    System.out.println("000");
                    HttpResponse<String> cevap = istemci.send(istek,HttpResponse.BodyHandlers.ofString());
                    String gelenCevap = cevap.body();
                    int gelenKod = cevap.statusCode();
                    System.out.println("111");
                    String mesaj = "";
                    OutputStream gonder = islem.getResponseBody();
                    System.out.println("Gelen: "+gelenCevap);
                    if(gelenKod==200){
                        int ID = Integer.valueOf(gelenCevap.split(":")[1].trim());
                        odunc.setKullaniciID(ID);
                    }
                    else{
                        mesaj += "Giris işlemi başarısız";
                        islem.sendResponseHeaders(500,mesaj.getBytes().length);
                        gonder.write(mesaj.getBytes());
                        gonder.close();
                        return;
                    }
                    HttpRequest istek1 = HttpRequest.newBuilder()
                            .uri(URI.create("http://kitap-servisi:2402/kitapec?id="+odunc.getKitapID()+"&soru=false"))
                            .GET()
                            .build();
                    HttpResponse<String> cevap1 = istemci.send(istek1,HttpResponse.BodyHandlers.ofString());
                    gelenCevap = cevap1.body();
                    gelenKod = cevap1.statusCode();
                    int Karar = Integer.valueOf(gelenCevap.split(":")[1]);
                    System.out.println(Karar);
                    System.out.println(gelenCevap.split(":")[0]);
                    if(Karar!=2 || gelenKod==500){
                        islem.sendResponseHeaders(500,gelenCevap.split(":")[0].getBytes().length);
                        gonder.write(gelenCevap.split(":")[0].getBytes());
                        gonder.close();
                        return;
                    }
                    if(oduncServisi.KitapOduncAl(odunc)){
                        mesaj = "Kitap başarılı şekilde ödünç alındı!!";
                        islem.sendResponseHeaders(200,mesaj.getBytes().length);
                    }
                    else {
                        mesaj = "Kitap ödünç alma işlemi başarısız!!";
                        islem.sendResponseHeaders(500,mesaj.getBytes().length);
                    }
                    gonder.write(mesaj.getBytes());
                    gonder.close();
                }catch (Exception e){
                    System.out.println("***&&&&*****");
                    System.out.println("Hata: "+e.getMessage());
                }
            }
        }));
        server.start();
    }
}
