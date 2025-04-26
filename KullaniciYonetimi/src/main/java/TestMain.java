import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {
        KullaniciServisi servis = new KullaniciServisi();
        String email = "beyza66uygun@gmail.com";
        String sifre = "145316";
        String ad = "Beyza";
        String soyad = "Uygun";
        char cinsiyet = 'E';
        String dogumTarihi = "2005-02-24";
        Kullanici yeniKullanici = new Kullanici(null, dogumTarihi, cinsiyet, sifre, email, soyad, ad, 0);
        servis.KullaniciKaydi(yeniKullanici);
        servis.KullaniciGiris(email, sifre);
    }
}
