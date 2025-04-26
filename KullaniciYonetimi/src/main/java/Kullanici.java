public class Kullanici {
    private int ID;
    private String Ad;
    private String Soyad;
    private String email;
    private String Sifre;
    private char Cinsiyet;
    private String DogumTarihi;
    private String KayitTarihi;

    public Kullanici(String kayitTarihi, String dogumTarihi, char cinsiyet, String sifre, String email, String soyad, String ad, int ID) {
        KayitTarihi = kayitTarihi;
        DogumTarihi = dogumTarihi;
        Cinsiyet = cinsiyet;
        Sifre = sifre;
        this.email = email;
        Soyad = soyad;
        Ad = ad;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getSoyad() {
        return Soyad;
    }

    public void setSoyad(String soyad) {
        Soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return Sifre;
    }

    public void setSifre(String sifre) {
        Sifre = sifre;
    }

    public char getCinsiyet() {
        return Cinsiyet;
    }

    public void setCinsiyet(char cinsiyet) {
        Cinsiyet = cinsiyet;
    }

    public String getDogumTarihi() {
        return DogumTarihi;
    }

    public void setDogumTarihi(String dogumTarihi) {
        DogumTarihi = dogumTarihi;
    }

    public String getKayitTarihi() {
        return KayitTarihi;
    }

    public void setKayitTarihi(String kayitTarihi) {
        KayitTarihi = kayitTarihi;
    }
}
