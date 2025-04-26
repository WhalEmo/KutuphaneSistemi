public class Kitap {
    private int ID;
    private String Baslik;
    private String Yazar;
    private int YayinYili;
    private String ISBN;
    private int Adet;

    public Kitap(int ID, String baslik, String yazar, int yayinYili, String ISBN, int adet) {
        this.ID = ID;
        Baslik = baslik;
        Yazar = yazar;
        YayinYili = yayinYili;
        this.ISBN = ISBN;
        Adet = adet;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBaslik() {
        return Baslik;
    }

    public void setBaslik(String baslik) {
        Baslik = baslik;
    }

    public String getYazar() {
        return Yazar;
    }

    public void setYazar(String yazar) {
        Yazar = yazar;
    }

    public int getYayinYili() {
        return YayinYili;
    }

    public void setYayinYili(int yayinYili) {
        YayinYili = yayinYili;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getAdet() {
        return Adet;
    }

    public void setAdet(int adet) {
        Adet = adet;
    }
}
