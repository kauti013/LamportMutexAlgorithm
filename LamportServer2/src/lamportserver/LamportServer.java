

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author kautilay
 */
public class LamportServer implements Serializable,Runnable {
public String senderName;
public int port;
public String commandTold;
public int timeWriter;
public String toAppendFile;
public int messageId;
public String fileText;
public boolean isComplete=false;
public String fileName;
public static HashMap<String,String>serverList= new HashMap<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
serverList.put("server1","/home/012/k/kx/kxs168330/AOS1/LamportServer/src/lamportserver");
        serverList.put("server2","/home/012/k/kx/kxs168330/AOS1/LamportServer2/src/lamportserver");
        serverList.put("server3","/home/012/k/kx/kxs168330/AOS1/LamportServer3/src/lamportserver");
        ExecutorService thread = Executors.newFixedThreadPool(200);
        Runnable gatewayRun = new LamportServer();
        thread.execute(gatewayRun);
    }

    @Override
    public void run() {
        try {
            int port= 57002;
          //  LamportServer obj1= new LamportServer();
            extras ex1=new extras();
extras ex2=new extras();
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                Socket sc = ss.accept();
                InputStream is = sc.getInputStream();
               // LamportServer obj=new LamportServer();

                ObjectInputStream ois = new ObjectInputStream(is);
                try {
                    ex1= (extras) ois.readObject();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // /  BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));

                String line = ex1.commandSent;
                String[] command;
                command = line.split(" ");
                switch(command[0].toUpperCase())
                {
                    case "READ":
                        String fileName = command[1];

                        // This will reference one line at a time
                        String line1 = null;

                        try {
                            // FileReader reads text files in the default encoding.
                            FileReader fileReader =
                                    new FileReader(fileName);

                            // Always wrap FileReader in BufferedReader.
                            BufferedReader bufferedReader =
                                    new BufferedReader(fileReader);

                            while((line1 = bufferedReader.readLine()) != null) {
                                System.out.println(line);
                                //Socket sc2 = new Socket(obj.senderName, obj.port);
                                Socket sc2 = new Socket(ex1.selfName, ex1.port);
OutputStream os2 = sc2.getOutputStream();
                               // PrintWriter out = new PrintWriter(os2);
                               // out.print(line1);
                                ex1.messageId=2;
                                ex1.fileContent=line1;
                             //   ex1.isComplete=true;
ex1.fileName=fileName;
ex1.commandSent=command[0];
                                ObjectOutputStream oos2 = new ObjectOutputStream(os2);
                                oos2.writeObject(ex1);
System.out.println("SENDING" + " "+ ex1.fileContent + " TO "+ ex1.selfName);
System.out.println("FILENAME SENT:"+ ex1.fileName);

System.out.println("BOOLEAN SENT:"+ ex1.isComplete);

                            }
                            Socket sc2 = new Socket(ex1.selfName, ex1.port);
                            OutputStream os2 = sc2.getOutputStream();
                            // PrintWriter out = new PrintWriter(os2);
                            // out.print(line1);
                            ex1.messageId=2;
                            ex1.isComplete=true;

                            ObjectOutputStream oos2 = new ObjectOutputStream(os2);
                            oos2.writeObject(ex1);

                            // Always close files.
                            bufferedReader.close();
                        }

                        catch(FileNotFoundException ex) {
                            System.out.println(
                                    "Unable to open file '" +
                                            fileName + "'");
                        }

                        break;
                    case "WRITE":
                        FileWriter fw = new FileWriter(command[1], true);
                        BufferedWriter b1 = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(b1);
toAppendFile= ex1.selfName + " "+ ex1.time;
                        out.println(toAppendFile);
                        b1.close();
                        fw.close();
Socket sc2 = new Socket(ex1.selfName, ex1.port);
                       // Socket sc2 = new Socket(obj.senderName, obj.port);
                        OutputStream os2 = sc2.getOutputStream();
                        ex1.messageId=2;
                        ex1.isComplete=true;
                        ex1.fileName=command[1];
                        ex1.commandSent=command[0];
                        ObjectOutputStream oos2 = new ObjectOutputStream(os2);
                        oos2.writeObject(ex1);
                     //   b1.close();
                        break;
case "ENQUIRE":

String filepath=null;
                        String serverName=command[1];

                        Socket sc1;
                        OutputStream os;
                        ObjectOutputStream oos;
                            for (Map.Entry<String, String> entry : LamportServer.serverList.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            System.out.println("Peers are:" + key);
                            //boolean check=key.equals(LamportNew.selfName);
                            boolean check= key.equals(command[1]);
                            if(check==true){
                                //System.out.println("key" + " "+ key);
//System.out.println("Lamport SElfname" + " "+ LamportNew.selfName);
                                // ex2.selfName = LamportNew.selfName;
/*
                                sc1 = new Socket(ex1.fileName, ex1.port);
                                os = sc1.getOutputStream();
                                oos = new ObjectOutputStream(os);
                                oos.writeObject(ex2);*/
                        filepath=value;
                                File f = new File(filepath);
                                ArrayList<String> files = new ArrayList<String>(Arrays.asList(f.list()));
                                ex1.serverFiles=files;
ex1.messageId=2;
                        System.out.println("SERVER FILES ON ENQUIRE" +" " +ex1.serverFiles);
                        ex1.isComplete=true;
                            sc1 = new Socket(ex1.fileName, ex1.port);
                            os = sc1.getOutputStream();
                            oos = new ObjectOutputStream(os);
                            oos.writeObject(ex2);
                            }
                        }
break;

                }

















            }
        } catch (IOException ex) {
            Logger.getLogger(LamportServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
