import java.sql.Timestamp;

public class Bildirim {
    private int ID;
    private int KullaniciID;
    private String Mesaj;
    private Timestamp GonderimTarihi;
    private String Tip;

    public Bildirim(int kullaniciID, String mesaj, String Tip) {
        KullaniciID = kullaniciID;
        Mesaj = mesaj;
        this.Tip = Tip;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getKullaniciID() {
        return KullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        KullaniciID = kullaniciID;
    }

    public String getMesaj() {
        return Mesaj;
    }

    public void setMesaj(String mesaj) {
        Mesaj = mesaj;
    }

    public Timestamp getGonderimTarihi() {
        return GonderimTarihi;
    }

    public void setGonderimTarihi(Timestamp gonderimTarihi) {
        GonderimTarihi = gonderimTarihi;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String tip) {
        Tip = tip;
    }
}
