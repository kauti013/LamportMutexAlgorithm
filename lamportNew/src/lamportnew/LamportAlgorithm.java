
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.PriorityQueue;

public class LamportAlgorithm implements Runnable {
   public static String operation;
   public String filename;
   public static int counter=4;
   public static int lamportClock;
   public static boolean wait=false;
 public LamportAlgorithm(){}

   public LamportAlgorithm(String operation, String filename)
    {
        this.operation=operation;
        this.filename=filename;
		/*System.out.println("OPERATION TOLD"+ " "+ operation);
		System.out.println("FILENAME"+ " " + filename);*/
    }
    public void Operation(String filename, String operation) throws IOException {
       extras ex4 = new extras();
       priorityQueueOperation po=new priorityQueueOperation();
	   System.out.println(" OPERATION TOOOOOOOOOOOOOOO BEEEEEE DONE !!!! " + " " + operation);
	   synchronized (LamportNew.class) {
           switch (this.operation.toUpperCase()) {
               case "READ":
                   //Socket sc=new Socket();
                   String key1 = null;
                   int value1 = 0;
                   for (Map.Entry<String, Integer> e1 : LamportNew.servers.entrySet()) {
                       key1 = e1.getKey();
                       value1 = e1.getValue();
                   }
                   System.out.println("Servers ARE : " + key1);
                   ex4.commandSent = "READ" + " " + filename;
                   ex4.selfName = LamportNew.selfName;

                   ex4.port = LamportNew.port;
                   Socket readSocket = new Socket(key1, value1);
                   OutputStream outstream = readSocket.getOutputStream();
                   ObjectOutputStream out = new ObjectOutputStream(outstream);
                   out.writeObject(ex4);
                   //  po.removeFromQueue(filename);
                   break;

               case "WRITE":

                   for (Map.Entry<String, Integer> e2 : LamportNew.servers.entrySet()) {
                       String key2 = e2.getKey();
                       int value2 = e2.getValue();
                       System.out.println("WRITE SERVERS ARE : " + key2);
                       ex4.commandSent = "WRITE" + " " + filename;
                       ex4.selfName = LamportNew.selfName;
                       ex4.time = LamportNew.lamportClock;
                       ex4.port = LamportNew.port;
                       Socket sc1 = new Socket(key2, value2);
                       OutputStream os = sc1.getOutputStream();
                       ObjectOutputStream oos = new ObjectOutputStream(os);
                       oos.writeObject(ex4);
                   }

                   break;
               default:
                   System.out.println("REACHED DEFAULT");
           }
       }
    }
    @Override
    public void run() {
       priorityQueueOperation po=new priorityQueueOperation();
        try {
            wait = true;
            synchronized(LamportNew.class) {
       switch(filename)
{
    case "file1":

           // LamportNew.lamportClock += 1;
            sendingFunction(filename);
            po.addToQueue(LamportNew.selfName, LamportNew.lamportClock, filename);

        wait = false;
            break;
    case "file2":
        //LamportNew.lamportClock+=1;
        sendingFunction(filename);
        po.addToQueue(LamportNew.selfName,LamportNew.lamportClock,filename);
        break;
    case "file3":
      //  LamportNew.lamportClock+=1;
        sendingFunction(filename);
        po.addToQueue(LamportNew.selfName,LamportNew.lamportClock,filename);
        break;
}
}
} catch (IOException e) {
            e.printStackTrace();
        }

    }
   public static void sendingFunction(String filename) throws IOException {
       extras ex2=new extras();
       ex2.messageId=0;
       ex2.selfName=LamportNew.selfName;
       ex2.port=LamportNew.port;
       ex2.fileName=filename;
       LamportNew.lamportClock += 1;
       ex2.time=LamportNew.lamportClock;
ex2.operation=operation;
       Socket sc;
       OutputStream os;
       ObjectOutputStream oos;
       for (Map.Entry<String, Integer> entry : LamportNew.peers.entrySet()) {
           String key = entry.getKey();
           Integer value = entry.getValue();
System.out.println("Peers are:" + key);
boolean check=key.equals(LamportNew.selfName);
if(check==false){
	//System.out.println("key" + " "+ key);
//System.out.println("Lamport SElfname" + " "+ LamportNew.selfName);
          // ex2.selfName = LamportNew.selfName;
            sc = new Socket(key, value);
           os = sc.getOutputStream();
         oos = new ObjectOutputStream(os);
           oos.writeObject(ex2);
}
       }


   }
}
