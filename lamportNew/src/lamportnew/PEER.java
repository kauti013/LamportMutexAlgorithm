import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class PEER implements Runnable,Serializable {
    int port;
    String hostname;

    PEER(String hostname, String por) throws UnknownHostException {
        this.hostname = hostname;
        this.port = Integer.parseInt(por);
    }
    public void run() {
        try {
        extras ex3 = new extras();
        LamportNew.peers.put(hostname, port);
        ex3.messageId = 4;
        ex3.selfName = LamportNew.selfName;
        ex3.port = LamportNew.port;
        Socket sock = new Socket(hostname, port);
        OutputStream outstream = sock.getOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outstream);
        out.writeObject(ex3);
        sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
