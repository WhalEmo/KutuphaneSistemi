import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class KitapServisi {

    private Kitap KitapKarsilastirma(Kitap k1, Kitap k2){
        if(!k2.getBaslik().isEmpty()){
            k1.setBaslik(k2.getBaslik());
        }
        if(!k2.getYazar().isEmpty()){
            k1.setYazar(k2.getYazar());
        }
        if(!(k2.getYayinYili() == 0)){
            k1.setYayinYili(k2.getYayinYili());
        }
        if(!k2.getISBN().isEmpty()){
            k1.setISBN(k2.getISBN());
        }
        if(!(k2.getAdet() == 0)){
            k1.setAdet(k2.getAdet());
        }
        return k1;
    }

    public String KitapAdetCikartEkle(int ID, boolean EkleMi){
        Kitap kitap = KitapAra(ID);
        if(kitap==null){
            return "Kitap bulunamadı!!:0";
        }
        else if(!EkleMi){
            if(kitap.getAdet()==0){
                return "Kitap verilemez kalmamış:1";
            }
            else{
                kitap.setAdet(kitap.getAdet()-1);
                KitapGuncelle(kitap);
                return "Kitap verildi!!:2";
            }
        }
        else{
            kitap.setAdet(kitap.getAdet()+1);
            KitapGuncelle(kitap);
            return "Kitap adeti eklendi!:3";
        }
    }

    public Kitap KitapAra(int ID){
        String sqlSorgusu = "SELECT * FROM Kitap WHERE ID = ?";
        Kitap kitap;
        try {
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgusu);
            sorgu.setInt(1,ID);

            ResultSet sonuc = sorgu.executeQuery();
            if(sonuc.next()){
                kitap = new Kitap(
                        ID,
                        sonuc.getString("Baslik"),
                        sonuc.getString("Yazar"),
                        sonuc.getInt("YayinYili"),
                        sonuc.getString("ISBN"),
                        sonuc.getInt("Adet")
                        );
                System.out.println("Kitap bulundu!");
                baglanti.close();
                return kitap;
            }
            else{
                System.out.println("Kitap bulunamadı!");
                baglanti.close();
                return null;
            }

        }catch (Exception e){
            System.out.println("Hata: "+e.getMessage());
        }
        return null;
    }

    public boolean KitapGuncelle(Kitap kitap){
        Kitap eskiKitap = KitapAra(kitap.getID());
        if(eskiKitap==null){
            return false;
        }
        else{
            kitap = KitapKarsilastirma(eskiKitap,kitap);
        }
        String sqlSorgusu = "UPDATE Kitap SET Baslik = ?, Yazar = ?, YayinYili = ?, ISBN = ?, Adet = ? WHERE ID = ?";
        try {
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgusu);
            sorgu.setString(1,kitap.getBaslik());
            sorgu.setString(2,kitap.getYazar());
            sorgu.setInt(3,kitap.getYayinYili());
            sorgu.setString(4,kitap.getISBN());
            sorgu.setInt(5,kitap.getAdet());
            sorgu.setInt(6,kitap.getID());

            int etkilenensatir = sorgu.executeUpdate();
            if(etkilenensatir>0){
                System.out.println("Kitap başarılı şekilde güncellendi!");
                baglanti.close();
                return true;
            }
            else{
                System.out.println("Kitap güncellenirken hata oluştu!");
                baglanti.close();
                return false;
            }
        }catch (Exception e){
            System.out.println("Hata: "+ e.getMessage());
        }
        return false;
    }

    public boolean KitapEkle(Kitap kitap){
        String sqlSorgu = "INSERT INTO Kitap (Baslik, Yazar, YayinYili, ISBN, Adet) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgu);
            sorgu.setString(1,kitap.getBaslik());
            sorgu.setString(2,kitap.getYazar());
            sorgu.setInt(3,kitap.getYayinYili());
            sorgu.setString(4,kitap.getISBN());
            sorgu.setInt(5,kitap.getAdet());

            int etkilenensatir = sorgu.executeUpdate();

            if (etkilenensatir>0){
                System.out.println("Kitap başarılı şekilde eklendi!");
                baglanti.close();
                return true;
            }
            else{
                System.out.println("Kitap ekleme başarısız!!!");
                baglanti.close();
                return false;
            }

        }catch (Exception e){
            System.out.println("Hata: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<Kitap> TumKitaplar(){
        ArrayList<Kitap> kitaplar = new ArrayList<Kitap>();
        String sqlSorgu = "SELECT * FROM Kitap";
        try{
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgu);
            ResultSet sonuc = sorgu.executeQuery();
            while (sonuc.next()){
                Kitap kitap = new Kitap(
                        sonuc.getInt("ID"),
                        sonuc.getString("Baslik"),
                        sonuc.getString("Yazar"),
                        sonuc.getInt("YayinYili"),
                        sonuc.getString("ISBN"),
                        sonuc.getInt("Adet")
                );
                kitaplar.add(kitap);
            }
            baglanti.close();
        }catch (Exception e){
            System.out.println("Hata: "+e.getMessage());
        }
        return kitaplar;
    }

    public ArrayList<Kitap> TariheGoreKitaplar(int Tarih, boolean Buyukmu){
        ArrayList<Kitap> kitaplar = new ArrayList<Kitap>();
        String sqlSorgu = "SELECT * FROM Kitap WHERE YayinYili";
        if(Buyukmu){
            sqlSorgu += " >= ?";
        }
        else{
            sqlSorgu += " <= ?";
        }
        try{
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgu);
            sorgu.setInt(1,Tarih);
            ResultSet sonuc = sorgu.executeQuery();
            while (sonuc.next()){
                Kitap kitap = new Kitap(
                        sonuc.getInt("ID"),
                        sonuc.getString("Baslik"),
                        sonuc.getString("Yazar"),
                        sonuc.getInt("YayinYili"),
                        sonuc.getString("ISBN"),
                        sonuc.getInt("Adet")
                );
                kitaplar.add(kitap);
            }
            baglanti.close();
        }catch (Exception e){
            System.out.println("Hata: "+e.getMessage());
        }
        return kitaplar;
    }

    public ArrayList<Kitap> YazarKitaplari(String Yazar){
        ArrayList<Kitap> kitaplar = new ArrayList<Kitap>();
        String sqlSorgu = "SELECT * FROM Kitap WHERE Yazar = ?";
        Yazar = Kitap.BasHarfDzn(Yazar);

        try{
            Connection baglanti = VeriTabaniBaglantisi.Baglanti();
            PreparedStatement sorgu = baglanti.prepareStatement(sqlSorgu);
            sorgu.setString(1,Yazar);
            ResultSet sonuc = sorgu.executeQuery();
            while (sonuc.next()){
                Kitap kitap = new Kitap(
                        sonuc.getInt("ID"),
                        sonuc.getString("Baslik"),
                        sonuc.getString("Yazar"),
                        sonuc.getInt("YayinYili"),
                        sonuc.getString("ISBN"),
                        sonuc.getInt("Adet")
                );
                kitaplar.add(kitap);
            }
            baglanti.close();
        }catch (Exception e){
            System.out.println("Hata: "+e.getMessage());
        }
        return kitaplar;
    }

    public Kitap GelenVeriKitapOltr(String veriButunu){
        String[] parcaciklar = veriButunu.split("&");
        Kitap kitap = new Kitap(
                Integer.valueOf(UTF8(parcaciklar[0].split("=")[1])),
                UTF8(parcaciklar[1].split("=")[1]),
                UTF8(parcaciklar[2].split("=")[1]),
                Integer.valueOf(UTF8(parcaciklar[3].split("=")[1])),
                UTF8(parcaciklar[4].split("=")[1]),
                Integer.valueOf(UTF8(parcaciklar[5].split("=")[1]))
        );
        return kitap;
    }
    public String UTF8(String metin){
        return URLDecoder.decode(metin, StandardCharsets.UTF_8);
    }

    public String KitaplarText(ArrayList<Kitap> kitaplar){
        StringBuilder mesaj = new StringBuilder();
        for (Kitap kitap : kitaplar){
            mesaj.append(kitap.getID() + ",\n");
            mesaj.append(kitap.getBaslik() + ",\n");
            mesaj.append(kitap.getYazar() + ",\n");
            mesaj.append(kitap.getYayinYili() + ",\n");
            mesaj.append(kitap.getISBN() + ",\n");
            mesaj.append(kitap.getAdet() + ",\n");
            mesaj.append("&\n");
        }
        return mesaj.toString();
    }
}
