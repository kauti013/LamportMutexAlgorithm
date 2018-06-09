import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.net.ServerSocket;

public class LamportNew {
    public static int port = 59001;
    public static int lamportClock = 0;
    public static int boolCount = 0;
    public static String selfName;
    public static String filename;
    public static HashMap<String, Integer> peers = new HashMap<String, Integer>();
    public static HashMap<String, Integer> servers = new HashMap<String, Integer>();
    public static PriorityBlockingQueue<priorityQueueOperation> pqfile1;
    public static PriorityBlockingQueue<priorityQueueOperation> pqfile2;
    public static PriorityBlockingQueue<priorityQueueOperation> pqfile3;

    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        servers.put("dc40.utdallas.edu", 57001);
        servers.put("dc41.utdallas.edu", 57002);
        servers.put("dc42.utdallas.edu", 57003);
        peers.put("dc03.utdallas.edu", 59001);
        peers.put("dc05.utdallas.edu", 59001);
        peers.put("dc17.utdallas.edu", 59001);
        peers.put("dc07.utdallas.edu", 59001);
        peers.put("dc27.utdallas.edu", 59001);
//peers.put("dc01.utdallas.edu",59001);
        pqcompatpr pcomp = new pqcompatpr();
        //System.out.println("cdf");
        pqfile1 = new PriorityBlockingQueue<priorityQueueOperation>(3, pcomp);
        pqfile2 = new PriorityBlockingQueue<priorityQueueOperation>(3, pcomp);
        pqfile3 = new PriorityBlockingQueue<priorityQueueOperation>(3, pcomp);

        //  Scanner scan=new Scanner(System.in);
        selfName = InetAddress.getLocalHost().getHostName();
        ExecutorService thread = Executors.newFixedThreadPool(200);
        Runnable gatewayRun = new LamportGateway();
        thread.execute(gatewayRun);
        extras ex2=new extras();
        ArrayList<String> operation = new ArrayList<>(Arrays.asList("READ", "WRITE","ENQUIRE"));

        ArrayList<String> file_select = new ArrayList<>(Arrays.asList("file1","file2","file3"));

        //out = new PrintWriter(new FileWriter(new File("output" + clientId + ".txt")), true);
        Thread.sleep(10000);
        for (int i = 1; i <= 20; i++) {
            System.out.println ("####################### NUMBER OF TIME #################" + "   " +i);
            String random_op = operation.get(new Random().nextInt(operation.size()));
            String random_file = file_select.get(new Random().nextInt(file_select.size()));
            System.out.println(".....Operation........ " + random_op + "..........File Selection........ " + random_file);
            // while (true) {
            System.out.println("FIRST CREATE TCP CONNECTIONS BY MAKING PEERS");
            System.out.println("READ <filename>");
            System.out.println("WRITE <filename>");
            System.out.println("ENQUIRE");
            //  String in = scan.nextLine();
            //in = in.trim();
            // String input[] = in.split(" ");
            // filename=input[1];
            // LamportAlgorithm.wait = true;
            synchronized (LamportNew.class) {
                // switch(input[1])
                // switch (random_file) {

                //    case "file1":
                //switch (input[0].toUpperCase())
                switch (random_op) {
                    case "PEER":
                        // Runnable peerRun = new PEER(input[1], input[2]);
                        // thread.execute(peerRun);
                        break;
                    case "READ":
                        //Runnable lamportrun = new LamportAlgorithm(input[0],input[1]);
                        //thread.execute(lamportrun);
                        //  LamportAlgorithm lamp = new LamportAlgorithm(input[0], input[1]);
                        LamportAlgorithm lamp = new LamportAlgorithm(random_op, random_file);
                        lamp.run();
//Thread.sleep(5000);
                        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                        break;
                    case "WRITE":
                        Runnable lamportrun1 = new LamportAlgorithm(random_op, random_file);
                        thread.execute(lamportrun1);
//Thread.sleep(10000);
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

                        break;
                    case "ENQUIRE":
                        int port2=64000;
                        ex2.port=port2;

ex2.commandSent = "ENQUIRE" + " " +"server1";
                        ex2.selfName=LamportNew.selfName;
                       Socket sc1 = new Socket("dc40.utdallas.edu", 57001);
                         //       ServerSocket sc1 = new ServerSocket(port2);
                        OutputStream os1 = sc1.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(os1);
                        oos.writeObject(ex2);
System.out.println("SENT TO SERVER");
                        ServerSocket ss = new ServerSocket(port2);
                        while(true){
System.out.println("WAITING");
                            Socket sc = ss.accept();
                            InputStream is = sc.getInputStream();
                            //LamportServer obj=new LamportServer();

                            ObjectInputStream ois = new ObjectInputStream(is);
                            try {
                                ex2= (extras) ois.readObject();

                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
System.out.println("MESSAGE ID:" + " " + ex2.messageId);
System.out.println(" IS IT COMPLETE ?"+ " " + ex2.isComplete);
for(String s: ex2.serverFiles)
{
    System.out.println("FILES ARE :"+ " " +s);
}

if(ex2.isComplete==true)
   {
sc.close();
sc1.close();
ss.close();
 break;}

                        }

                        break;
                    default:
                        System.exit(1);
                }

                //break;
                // }
            }
            Thread.sleep(5000);

        }



    }
}