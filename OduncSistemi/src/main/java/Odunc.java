import java.time.LocalDate;

public class Odunc {
    private int ID;
    private int KullaniciID;
    private int KitapID;
    private LocalDate OduncTarihi;
    private LocalDate IadeTarihi;
    private boolean TeslimEdildi;

    public Odunc(int ID, int kullaniciID, int kitapID, LocalDate oduncTarihi, LocalDate iadeTarihi, boolean teslimEdildi) {
        this.ID = ID;
        KullaniciID = kullaniciID;
        KitapID = kitapID;
        OduncTarihi = oduncTarihi;
        IadeTarihi = iadeTarihi;
        TeslimEdildi = teslimEdildi;
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

    public int getKitapID() {
        return KitapID;
    }

    public void setKitapID(int kitapID) {
        KitapID = kitapID;
    }

    public LocalDate getOduncTarihi() {
        return OduncTarihi;
    }

    public void setOduncTarihi(LocalDate oduncTarihi) {
        OduncTarihi = oduncTarihi;
    }

    public LocalDate getIadeTarihi() {
        return IadeTarihi;
    }

    public void setIadeTarihi(LocalDate iadeTarihi) {
        IadeTarihi = iadeTarihi;
    }

    public boolean isTeslimEdildi() {
        return TeslimEdildi;
    }

    public void setTeslimEdildi(boolean teslimEdildi) {
        TeslimEdildi = teslimEdildi;
    }
}
