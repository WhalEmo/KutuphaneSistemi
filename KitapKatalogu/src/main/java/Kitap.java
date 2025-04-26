public class Kitap {
    private int ID;
    private String Baslik;
    private String Yazar;
    private int YayinYili;
    private String ISBN;
    private int Adet;

    public Kitap(int ID, String baslik, String yazar, int yayinYili, String ISBN, int adet) {
        this.ID = ID;
        Baslik = BasHarfDzn(baslik);
        Yazar = BasHarfDzn(yazar);
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
        Baslik = BasHarfDzn(baslik);
    }

    public String getYazar() {
        return Yazar;
    }

    public void setYazar(String yazar) {
        Yazar = BasHarfDzn(yazar);
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

    public static String BasHarfDzn(String veri){
        veri = veri.toLowerCase();
        veri = veri.substring(0,1).toUpperCase()+veri.substring(1).toLowerCase();
        return veri;
    }
}
