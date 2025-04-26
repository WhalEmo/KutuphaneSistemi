import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeriTabaniBaglantisi {
    private static final String url = "jdbc:postgresql://veritabani:5432/kutuphane";
    private static final String kullanici = "admin";
    private static final String sifre = "admin123";

    public static Connection Baglanti(){
        try {
            return DriverManager.getConnection(url, kullanici, sifre);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
