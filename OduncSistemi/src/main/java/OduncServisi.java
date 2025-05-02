import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OduncServisi {

    public boolean KitapOduncAl(Odunc odunc){
        String sqlSorgusu = "INSERT INTO Odunc (KullaniciID, KitapID, OduncTarihi, IadeTarihi, TeslimEdildi) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgusu);

            sorgu.setInt(1,odunc.getKullaniciID());
            sorgu.setInt(2,odunc.getKitapID());
            sorgu.setDate(3, Date.valueOf(odunc.getOduncTarihi()));
            sorgu.setDate(4, Date.valueOf(odunc.getIadeTarihi()));
            sorgu.setBoolean(5,odunc.isTeslimEdildi());

            int etkilenensatir = sorgu.executeUpdate();
            if (etkilenensatir>0){
                System.out.println("Ödünç kaydı yapıldı!");
                baglanti.close();
                return true;
            }
            else {
                System.out.println("Ödünç kaydı yapılamadı!!");
                baglanti.close();
                return false;
            }

        }catch (Exception e){
            System.out.println("---***--");
            System.out.println("Hata: " + e.getMessage());
        }
        return false;
    }
    public int IadeIslemi(int ID){
        String sqlSorgu = "SELECT * FROM Odunc WHERE ID = ?";
        try {
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgu);
            sorgu.setInt(1,ID);
            ResultSet sonuc = sorgu.executeQuery();
            if(!sonuc.next()){
                System.out.println("Ödünç sisteminde böyle bir kayıt yok!!");
                return -1;
            }
            sqlSorgu = "UPDATE Odunc SET TeslimEdildi = ? WHERE ID = ? AND TeslimEdildi = ?";
            PreparedStatement sorgu2 = baglanti.prepareStatement(sqlSorgu);
            sorgu2.setBoolean(1,true);
            sorgu2.setInt(2,ID);
         //   sorgu2.setBoolean();
            int etkilenensatir = sorgu2.executeUpdate();
            if(etkilenensatir>0){
                System.out.println("İade işlemi başarılı!!");
                baglanti.close();
                return sonuc.getInt("KitapID");
            }
            else {
                System.out.println("İade işlemi başarısız!!");
                baglanti.close();
                return -1;
            }
        }catch (Exception e){
            System.out.println("Hata: "+e.getMessage());
        }
        return -1;
    }

    public String KullaniciOduncKayitlari(){
        String sqlSorgu = "SELECT * FROM Odunc WHERE TeslimEdildi = ?";
        try {
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgu);
            sorgu.setBoolean(1,false);
            ResultSet sonuc = sorgu.executeQuery();
            String veriler = "";
            while (sonuc.next()){
                veriler += sonuc.getInt("KullaniciID") + ":" + sonuc.getString("IadeTarihi") + ":" + sonuc.getInt("KitapID") + ",";
            }
            return veriler;
        }catch (Exception e){
            System.out.println("Hata: "+e.getMessage());
        }
        return "";
    }

    public String UTF8(String metin){
        return URLDecoder.decode(metin, StandardCharsets.UTF_8);
    }
}
