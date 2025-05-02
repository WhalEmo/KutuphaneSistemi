import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BildirimServisi {

    public boolean BildirimKayit(Bildirim bildirim){
        HttpClient istemci = HttpClient.newHttpClient();
        HttpRequest istek = HttpRequest.newBuilder()
                .uri(URI.create("http://kullanici-servisi:1453/IDAra?id="+bildirim.getKullaniciID()))
                .GET()
                .build();
        String sql = "INSERT INTO Bildirim (KullaniciID, Mesaj, Tip) VALUES (?, ?, ?)";
        try {
            HttpResponse<String> gelen = istemci.send(istek,HttpResponse.BodyHandlers.ofString());
            String gelenCevap = gelen.body();
            int Kod = gelen.statusCode();
            if(Kod!=200){
                return false;
            }
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sql);
            sorgu.setInt(1,bildirim.getKullaniciID());
            sorgu.setString(2,bildirim.getMesaj());
            sorgu.setString(3,bildirim.getTip());

            int etkilenensatir = sorgu.executeUpdate();

            if(etkilenensatir>0){
                System.out.println(gelenCevap+": ---> Mesaj: "+bildirim.getMesaj());
                System.out.println("Bildirim kaydi veritabanina yapıldı");
                baglanti.close();
                return true;
            }
            else {
                baglanti.close();
                return false;
            }
        }catch (Exception e){
            System.out.println("Hata: "+e.getMessage());
        }
        return false;
    }

    public void BildirimHatirlatma(Bildirim bildirim,String mesaj){
        bildirim.setMesaj(mesaj);
        BildirimKayit(bildirim);
    }
    public void BildirimHatirlatma(){
        HttpClient istemci = HttpClient.newHttpClient();
        HttpRequest istek = HttpRequest.newBuilder()
                .uri(URI.create("http://odunc-servisi:1808/kullanicikayitlari"))
                .GET()
                .build();
        HttpResponse<String> gelen = null;
        try {
            gelen = istemci.send(istek, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Hata: "+e.getMessage());
        }
        String gelenCevap = gelen.body();
        int Kod = gelen.statusCode();
        if (Kod!=200){
            return;
        }
        String[] KitaplarVeTarihler = gelenCevap.split(",");
        for (String veri : KitaplarVeTarihler){
            int KitapID = Integer.valueOf(veri.split(":")[2]);
            int KID = Integer.valueOf(veri.split(":")[0]);
            String Tarih = veri.split(":")[1];
            LocalDate trh = LocalDate.parse(Tarih);
            LocalDate simdi = LocalDate.now();
            long kalangun = ChronoUnit.DAYS.between(simdi,trh);
            Bildirim bildirim = new Bildirim(KID,Mesaj(kalangun,KitapID),"email");
            BildirimKayit(bildirim);
        }
    }

    private String Mesaj(long gun, int KitapID){
        if(gun<0){
            return KitapID + "-ID'li kitabin teslim tarihi " + Math.abs(gun) + "gecikmiş acilen kitabi geri teslim edin!!?!?!??!";
        }
        return KitapID + "-ID'li kitabi teslim etmeniz için " + gun + " kaldi!!!";
    }

}
