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
            System.out.println("Hata: " + e.getMessage());
        }
        return false;
    }
    public boolean IadeIslemi(int ID){
        String sqlSorgu = "SELECT * FROM kullanici WHERE ID = ?";
        try {
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgu);
            sorgu.setInt(1,ID);
            ResultSet sonuc = sorgu.executeQuery();
            if(!sonuc.next()){
                System.out.println("Ödünç sisteminde böyle bir kayıt yok!!");
                return false;
            }
            sqlSorgu = "UPDATE Kitap SET TeslimEdildi = ? WHERE ID = ?";
            PreparedStatement sorgu2 = baglanti.prepareStatement(sqlSorgu);
            sorgu2.setBoolean(1,true);
            sorgu2.setInt(2,ID);
            int etkilenensatir = sorgu2.executeUpdate();
            if(etkilenensatir>0){
                System.out.println("İade işlemi başarılı!!");
                baglanti.close();
                return true;
            }
            else {
                System.out.println("İade işlemi başarısız!!");
                baglanti.close();
                return false;
            }
        }catch (Exception e){
            System.out.println("Hata: "+e.getMessage());
        }
        return false;
    }
    public String UTF8(String metin){
        return URLDecoder.decode(metin, StandardCharsets.UTF_8);
    }
}
