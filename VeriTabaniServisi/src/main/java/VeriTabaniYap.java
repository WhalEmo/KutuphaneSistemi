import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class VeriTabaniYap {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://veritabani:5432/kutuphane";
        String kullanici = "admin";
        String sifre = "admin123";

        try {
            Connection baglanti = DriverManager.getConnection(url, kullanici, sifre);
            Statement stmt = baglanti.createStatement();

            String KullaniciTablosu = "CREATE TABLE IF NOT EXISTS Kullanici (" +
                    "    ID SERIAL PRIMARY KEY," +
                    "    Ad VARCHAR(100) NOT NULL," +
                    "    Soyad VARCHAR(100) NOT NULL," +
                    "    Email VARCHAR(100) UNIQUE NOT NULL," +
                    "    Sifre VARCHAR(100) NOT NULL," +
                    "    Cinsiyet CHAR(1)," +
                    "    DogumTarihi DATE," +
                    "    KayitTarihi DATE DEFAULT CURRENT_DATE" +
                    ");";
            String KitapTablosu = "CREATE TABLE IF NOT EXISTS Kitap (" +
                    "    ID SERIAL PRIMARY KEY," +
                    "    Baslik VARCHAR(255) NOT NULL," +
                    "    Yazar VARCHAR(255)," +
                    "    YayinYili INT," +
                    "    ISBN VARCHAR(20) UNIQUE," +
                    "    Adet INT DEFAULT 1" +
                    ");";
            String OduncTablosu = "CREATE TABLE IF NOT EXISTS Odunc (" +
                    "    ID SERIAL PRIMARY KEY," +
                    "    KullaniciID INT REFERENCES kullanici(ID) ON DELETE CASCADE," +
                    "    KitapID INT REFERENCES kitap(ID) ON DELETE CASCADE," +
                    "    OduncTarihi DATE NOT NULL," +
                    "    IadeTarihi DATE," +
                    "    TeslimEdildi BOOLEAN DEFAULT FALSE" +
                    ");";
            String BildirimTablosu = "CREATE TABLE IF NOT EXISTS Bildirim (" +
                    "    ID SERIAL PRIMARY KEY," +
                    "    KullaniciID INT REFERENCES kullanici(ID) ON DELETE CASCADE," +
                    "    Mesaj TEXT NOT NULL," +
                    "    GonderimTarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "    Tip VARCHAR(50)" +
                    ");";

            stmt.executeUpdate(KullaniciTablosu);
            stmt.executeUpdate(KitapTablosu);
            stmt.executeUpdate(OduncTablosu);
            stmt.executeUpdate(BildirimTablosu);

            System.out.println("Tablolar başarıyla oluşturuldu!");
            baglanti.close();

        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
}
