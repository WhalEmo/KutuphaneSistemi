import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class BildirimAPI {
    public static BildirimServisi bildirimServisi = new BildirimServisi();
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        HttpServer server;
        try {
            server = HttpServer.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bildirimServisi.BildirimHatirlatma();
    }
}
