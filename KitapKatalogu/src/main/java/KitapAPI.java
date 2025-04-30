import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class KitapAPI {
    public static KitapServisi kitapServisi = new KitapServisi();
    public static void main(String[] args) {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(2402),0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        server.createContext("/kitapAra",(islem->{
            if("GET".equals(islem.getRequestMethod())){
                String Gelen = islem.getRequestURI().getQuery();
                int ID = Integer.valueOf(Gelen.split("=")[1]);
                Kitap kitap = kitapServisi.KitapAra(ID);
                String mesaj;
                if(kitap != null){
                    mesaj = "Kitap bulundu: " + kitap.getBaslik();
                    islem.sendResponseHeaders(200,mesaj.getBytes().length);
                }
                else {
                    mesaj = "Kitap bulunamadı";
                    islem.sendResponseHeaders(500,mesaj.getBytes().length);
                }
                OutputStream gonder = islem.getResponseBody();
                gonder.write(mesaj.getBytes());
                gonder.close();
            }
        }));

        server.createContext("/kitapGuncelle",(islem->{
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
                Kitap kitap = kitapServisi.GelenVeriKitapOltr(gelenVeriler);
                String mesaj;
                if(kitapServisi.KitapGuncelle(kitap)){
                    mesaj = "Kitap güncellendi: " + kitap.getBaslik();
                    islem.sendResponseHeaders(200,mesaj.getBytes().length);
                }
                else {
                    mesaj = "Kitap güncellerken bir hata oluştu: " + kitap.getBaslik();
                    islem.sendResponseHeaders(500,mesaj.getBytes().length);
                }
                OutputStream gonder = islem.getResponseBody();
                gonder.write(mesaj.getBytes());
                gonder.close();
            }
        }));
        server.createContext("/kitapEkle",(islem->{
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
                Kitap kitap = kitapServisi.GelenVeriKitapOltr(gelenVeriler);
                String mesaj;
                if(kitapServisi.KitapEkle(kitap)){
                    mesaj = "Kitap başarılı bir şekilde eklendi: " + kitap.getBaslik();
                    islem.sendResponseHeaders(200,mesaj.getBytes().length);
                }
                else{
                    mesaj = "Kitap eklerken bir hata oluştu!!";
                    islem.sendResponseHeaders(500,mesaj.getBytes().length);
                }
                OutputStream gonder = islem.getResponseBody();
                gonder.write(mesaj.getBytes());
                gonder.close();
            }
        }));
        server.createContext("/kitapTarih",(islem->{
            if("GET".equals(islem.getRequestMethod())){
                String gelen = islem.getRequestURI().getQuery();
                String[] parcaciklar = gelen.split("&");
                int Tarih = Integer.valueOf(parcaciklar[0].split("=")[1]);
                boolean BuyukMu = Boolean.valueOf(parcaciklar[1].split("=")[1]);
                ArrayList<Kitap> kitaplar = kitapServisi.TariheGoreKitaplar(Tarih,BuyukMu);
                String mesaj = kitapServisi.KitaplarText(kitaplar);
                if(!mesaj.isEmpty()){
                    islem.sendResponseHeaders(200,mesaj.getBytes().length);
                }
                else{
                    mesaj += "Aramada bir hata oluştu?!?!?!";
                    islem.sendResponseHeaders(500,mesaj.getBytes().length);
                }
                OutputStream gonder = islem.getResponseBody();
                gonder.write(mesaj.getBytes());
                gonder.close();
            }
        }));
        server.createContext("/tumKitaplar",(islem->{
            if("GET".equals(islem.getRequestMethod())){
                ArrayList<Kitap> kitaplar = kitapServisi.TumKitaplar();
                String mesaj = kitapServisi.KitaplarText(kitaplar);
                if(!mesaj.isEmpty()){
                    islem.sendResponseHeaders(200,mesaj.getBytes().length);
                }
                else{
                    mesaj += "Bir hata oluştu?!?!!?!?";
                    islem.sendResponseHeaders(500,mesaj.getBytes().length);
                }
                OutputStream gonder = islem.getResponseBody();
                gonder.write(mesaj.getBytes());
                gonder.close();
            }
        }));

        server.createContext("/kitapec",(islem->{
            if("GET".equals(islem.getRequestMethod())){
                String gelen = islem.getRequestURI().getQuery();
                String[] parcaciklar = gelen.split("&");
                int ID = Integer.valueOf(parcaciklar[0].split("=")[1]);
                boolean eklemi = Boolean.valueOf(parcaciklar[1].split("=")[1]);
                String mesaj = kitapServisi.KitapAdetCikartEkle(ID,eklemi);
                if(!mesaj.isEmpty()){
                    islem.sendResponseHeaders(200,mesaj.getBytes().length);
                }
                else{
                    mesaj += "Bir hata oluştu?!?!!?!?";
                    islem.sendResponseHeaders(500,mesaj.getBytes().length);
                }
                OutputStream gonder = islem.getResponseBody();
                gonder.write(mesaj.getBytes());
                gonder.close();
            }
        }));

        server.start();
    }
}
