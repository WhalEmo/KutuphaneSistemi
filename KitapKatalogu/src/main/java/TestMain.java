import java.util.ArrayList;

public class TestMain {
    public void main(String[] args) {
        KitapServisi servis = new KitapServisi();
        Kitap k1 = new Kitap(0,"Vertherin AcilAri","Bilinmeyen",2005,"11234",5);
        Kitap k2 = new Kitap(0,"Vertherin AgriLaRi","Bebegimm",2005,"11254",10);
        Kitap k3 = new Kitap(0,"Vertherin Sizilari","Yavrumm",2005,"12234",58);

        servis.KitapEkle(k1);
        servis.KitapEkle(k2);
        servis.KitapEkle(k3);

        Kitap k4 = servis.KitapAra(1);
        System.out.println("::"+k4.getBaslik());

        ArrayList<Kitap> kitaplar = servis.TumKitaplar();
        for (Kitap kitap: kitaplar){
            System.out.println(kitap.getBaslik());
        }
    }
}
