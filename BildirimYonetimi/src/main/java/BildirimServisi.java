import java.sql.Connection;
import java.sql.PreparedStatement;

public class BildirimServisi {

    public boolean BildirimKayit(Bildirim bildirim){
        String sql = "INSERT INTO Bildirim (KullaniciID, Mesaj, Tip) VALUES (?, ?, ?)";
        try {
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sql);
            sorgu.setInt(1,bildirim.getKullaniciID());
            sorgu.setString(2,bildirim.getMesaj());
            sorgu.setString(3,bildirim.getTip());
        }catch (Exception e){
            System.out.println("Hata: "+e.getMessage());
        }
        return false;
    }

}
