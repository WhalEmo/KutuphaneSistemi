import java.sql.*;
import java.time.LocalDate;

public class KullaniciServisi {

    public boolean KullaniciGiris(String email, String sifre){
        String sql = "SELECT * FROM kullanici WHERE Email = ? AND Sifre = ?";

        try (Connection baglanti = VeriTabaniBaglantisi.Baglanti();
             PreparedStatement pstmt = baglanti.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, sifre);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Kullanici kullanici = new Kullanici(rs.getString("KayitTarihi"),rs.getString("DogumTarihi"),rs.getString("Cinsiyet").charAt(0),rs.getString("S" +
                        "ifre"),rs.getString("Email"),rs.getString("Soyad"),rs.getString("Ad"),Integer.valueOf(rs.getString("ID")));
                System.out.println("Kullanıcı başarıyla giriş yaptı!");
                return true;
            } else {
                System.out.println("Hatalı e-posta veya şifre!");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Veritabanı hatası: " + e.getMessage());
            return false;
        }
    }

    public boolean KullaniciKaydi(Kullanici kullanici) {
        String sql = "INSERT INTO Kullanici (Ad, Soyad, Email, Sifre, Cinsiyet, DogumTarihi) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection baglanti = VeriTabaniBaglantisi.Baglanti();
             PreparedStatement pstmt = baglanti.prepareStatement(sql)) {

            pstmt.setString(1, kullanici.getAd());
            pstmt.setString(2, kullanici.getSoyad());
            pstmt.setString(3, kullanici.getEmail());
            pstmt.setString(4, kullanici.getSifre());
            pstmt.setString(5, String.valueOf(kullanici.getCinsiyet()));
            pstmt.setDate(6, Date.valueOf(LocalDate.parse(kullanici.getDogumTarihi())));

            int etkilenensatir = pstmt.executeUpdate();

            if(etkilenensatir>0){
                System.out.println("Kullanıcı başarıyla kaydedildi!");
                return true;
            }
            else{
                System.out.println("Kullanıcı kaydedilemedi!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Veritabanı hatası: " + e.getMessage());
            return false;
        }
    }
}
