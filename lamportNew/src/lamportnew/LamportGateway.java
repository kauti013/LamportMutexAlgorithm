

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;


public class LamportGateway implements Runnable, Serializable{


    public void run() {
        int port= 52000;
        ServerSocket ss= null;
        try {
            ss = new ServerSocket(LamportNew.port);
            extras ex1;
            extras ex2=new extras();
LamportAlgorithm la= new LamportAlgorithm();
            priorityQueueOperation po= new priorityQueueOperation();
        while (true) {
            Socket soc1 = ss.accept();
            InputStream is = soc1.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            ex1 = (extras) ois.readObject();
            switch(ex1.messageId)
            {
                case 0:/*

                REQUEST CODE;

                */
                    LamportAlgorithm.wait = true;
                    synchronized(LamportNew.class) {
                   // LamportNew.lamportClock+=1;
System.out.println("REQUEST RECEIVED for "+ ex1.operation + " "+ ex1.fileName + "by "+ " "+ ex1.selfName );
                    ex2.messageId=1;
                    //ex2.time=LamportNew.lamportClock;
                    if (ex1.time>LamportNew.lamportClock)
                        LamportNew.lamportClock=ex1.time+1;
                //    ex2.time=LamportNew.lamportClock;
                ex2.selfName=LamportNew.selfName;
                ex2.fileName=ex1.fileName;
ex2.operation=ex1.operation;

                        po.addToQueue(ex1.selfName, LamportNew.lamportClock, ex1.fileName);


                    LamportAlgorithm.wait = false;
//Iterator it = LamportNew..iterator();

              //      System.out.println("Priority queue values REQUEST TIME: ");

                    /*while (it.hasNext()) {
                        System.out.println("Value in QUEUE FOR FILE 1: "+ it.next().toString());
 priorityQueueOperation current = (priorityQueueOperation) it.next();
                        System.out.println(current.nodeName);
                    }*/
/*for(priorityQueueOperation q1: LamportNew.pqfile1){
                        System.out.println(q1.nodeName);
                    }*/
                    LamportNew.lamportClock+=1;
ex2.time=LamportNew.lamportClock;
                    Socket reqReply=new Socket(ex1.selfName,ex1.port);
                    OutputStream os= reqReply.getOutputStream();
                    ObjectOutputStream oos=new ObjectOutputStream(os);
                    oos.writeObject(ex2);
                    }
                    break;
                case 1:
                    /*
                    *
                    *
                    * REPLY CODE
                    *
                    * */
                    LamportAlgorithm.wait = true;
                    synchronized(LamportNew.class) {
                        System.out.println("Value as soon as I get a reply" + " " + LamportAlgorithm.counter);
System.out.println("REPLY SENT FOR FILE"+ " "+ex1.fileName+ " "+ "FOR THE OPERATION"+ " "+ex1.operation+ "by"+ " "+ex1.selfName);
 System.out.println("LAMPORT COUNTER VALUE RECORDED"+ " " +LamportAlgorithm.counter);
                        LamportAlgorithm.counter -= 1;
if(LamportAlgorithm.counter<0)
{
    LamportAlgorithm.counter=0;
}
                       // System.out.println("Value after subtraction of 1" + " " + LamportAlgorithm.counter);
System.out.println("LAMPORT COUNTER VALUE RECORDED after subtraction :"+ " " +LamportAlgorithm.counter);
                        if (ex1.time > LamportNew.lamportClock)
                            LamportNew.lamportClock = ex1.time+1;
                      //  LamportNew.lamportClock += 1;
                        System.out.println(ex1.operation);
                        switch (ex1.fileName) {
                            case "file1":

                                if (LamportAlgorithm.counter == 0 && po.pqfile1.get(0).nodeName == LamportNew.selfName) {
                                    System.out.println("COUNTER WAS ZERO ANDDDDDDDDDDDDDDDDD    I WAS FIRST");
                                    la.Operation(ex1.fileName, ex1.operation);
                                    LamportAlgorithm.counter = 4;
                                }
                                break;
                            case "file2":
                                if (LamportAlgorithm.counter == 0 && po.pqfile2.get(0).nodeName == LamportNew.selfName) {
                                    System.out.println("COUNTER WAS ZERO ANDDDDDDDDDDDDDDDDD    I WAS FIRST");
                                    la.Operation(ex1.fileName, ex1.operation);
                                    LamportAlgorithm.counter = 4;
                                }
                                break;
                            case "file3":
                                if (LamportAlgorithm.counter == 0 && po.pqfile3.get(0).nodeName == LamportNew.selfName) {
                                    System.out.println("COUNTER WAS ZERO ANDDDDDDDDDDDDDDDDD    I WAS FIRST");
                                    la.Operation(ex1.fileName, ex1.operation);
                                    LamportAlgorithm.counter = 4;
                                }
                                break;

                        }

                    }
                    break;

                case 2:
                    /*
                    *
                    * SERVER REPLY CODE
                    *
                    *
                    * */
                    synchronized(LamportNew.class) {
System.out.println("BOOLEAN RECV:"+ ex1.isComplete);
//LamportNew.lamportClock+=1;
if(ex1.isComplete==false)
{
    switch (ex1.commandSent.toUpperCase())
    {
        case "READ":
                  System.out.println(ex1.fileContent);
            break;
        case "WRITE":
            break;
    }
}
                    LamportAlgorithm.wait = true;
                   // synchronized(LamportNew.class) {
                    if(ex1.isComplete == true) {
                        switch (ex1.commandSent.toUpperCase()) {
                            case "READ":
                                System.out.println(" $$$$$$$$$$ REMOVING" + " "+ ex1.selfName+ " "+ " FROM "+ " "+ex1.fileName);
                                po.removeFromQueue(ex1.fileName);
                                LamportNew.lamportClock += 1;
                                Socket sc;
                                OutputStream os1;
                                ObjectOutputStream oos1;
                                for (Map.Entry<String, Integer> e2 : LamportNew.peers.entrySet()) {
                                    String key2 = e2.getKey();
                                    int value2 = e2.getValue();
                                    ex2.messageId = 3;
                                    ex2.fileName = ex1.fileName;
                                    ex2.selfName = LamportNew.selfName;
                                    boolean check = key2.equals(LamportNew.selfName);
                                    if (check == false) {

                                        sc = new Socket(key2, value2);
                                        os1 = sc.getOutputStream();
                                        oos1 = new ObjectOutputStream(os1);
                                        oos1.writeObject(ex2);
                                    }
                                }
                                break;
                            case "WRITE":
                                LamportNew.boolCount++;
                                System.out.println(" ************** BOOLEAN COUNT ="+ " "+LamportNew.boolCount);
                                if (LamportNew.boolCount == 3) {
                                    System.out.println(" ^^^^^^^^^^^^ REMOVING" + " "+ ex1.selfName+ " "+ " FROM "+ " "+ex1.fileName);
LamportNew.boolCount=0;
                                    po.removeFromQueue(ex1.fileName);
                                    LamportNew.lamportClock += 1;
                                    Socket sc1;
                                    OutputStream os2;
                                    ObjectOutputStream oos2;
                                    for (Map.Entry<String, Integer> e2 : LamportNew.peers.entrySet()) {
                                        String key3 = e2.getKey();
                                        int value3 = e2.getValue();
                                        ex2.messageId = 3;
                                        ex2.fileName = ex1.fileName;
                                        ex2.selfName = LamportNew.selfName;
                                        boolean check = key3.equals(LamportNew.selfName);
                                        if (check == false) {

                                            sc1 = new Socket(key3, value3);
                                            os2 = sc1.getOutputStream();
                                            oos2 = new ObjectOutputStream(os2);
                                            oos2.writeObject(ex2);
                                        }
                                    }
                                }
                                break;
                        }
                    }
                     //   System.out.println("Prioirty queue content" + priorityQueueOperation.pqfile1.get(0));
                       // po.removeFromQueue(ex1.fileName);

//System.out.println(ex1.fileContent);
                     /*   LamportNew.lamportClock += 1;
                        Socket sc;
                        OutputStream os1;
                        ObjectOutputStream oos1;
                        for (Map.Entry<String, Integer> e2 : LamportNew.peers.entrySet()) {
                            String key2 = e2.getKey();
                            int value2 = e2.getValue();
                            ex2.messageId = 3;
                            ex2.fileName = ex1.fileName;
                            ex2.selfName = LamportNew.selfName;
                            boolean check = key2.equals(LamportNew.selfName);
                            if (check == false) {

                                sc = new Socket(key2, value2);
                                os1 = sc.getOutputStream();
                                oos1 = new ObjectOutputStream(os1);
                                oos1.writeObject(ex2);
                            }
                        }*/
                    }

                    break;
                case 3:
                    /*
                    *
                    *
                    *
                    * RELEASE MSG RECEIVING ACTION CODE.
                    *
                    *
                    *
                    *
                    * */
  //  System.out.println(priorityQueueOperation.pqfile1.peek());
                    LamportAlgorithm.wait = true;
                    synchronized(LamportNew.class) {
                        System.out.println(" $$$$$$$$$$ REMOVING DURING RELEASE " + " "+ ex1.selfName+ " "+ " FROM "+ " "+ex1.fileName);
                        po.removeFromQueue(ex1.fileName);
                        //  Iterator it1 = priorityQueueOperation.pqfile1.iterator();

                        System.out.println("Priority queue values RELEASE TIME: ");
/*
                    while (it1.hasNext()) {
                        System.out.println("Value in QUEUE FOR FILE 1: "+ it1.next());
                    }*/


                        switch (ex1.fileName) {
                            case "file1":
                                if (LamportAlgorithm.counter == 0 && po.pqfile1.get(0).nodeName == LamportNew.selfName) {
                                    System.out.println("COUNTER WAS ZERO ANDDDDDDDDDDDDDDDDD    I WAS FIRST");
                                    la.Operation(ex1.fileName, LamportAlgorithm.operation);/*




CHECK THIS CODE AS TO HOW WILL THE SYSTEM KNOW WHICH COMMAND TO DO.




            */
                                    LamportAlgorithm.counter = 4;
                                }
                                break;
                            case "file2":
                                if (LamportAlgorithm.counter == 0 && po.pqfile2.get(0).nodeName == LamportNew.selfName) {
                                    System.out.println("COUNTER WAS ZERO ANDDDDDDDDDDDDDDDDD    I WAS FIRST");
                                    la.Operation(ex1.fileName, LamportAlgorithm.operation);/*




CHECK THIS CODE AS TO HOW WILL THE SYSTEM KNOW WHICH COMMAND TO DO.




            */
                                    LamportAlgorithm.counter = 4;
                                }
                                break;
                            case "file3":
                                if (LamportAlgorithm.counter == 0 && po.pqfile3.get(0).nodeName == LamportNew.selfName) {
                                    System.out.println("COUNTER WAS ZERO AND I WAS FIRST");
                                    la.Operation(ex1.fileName, LamportAlgorithm.operation);/*




CHECK THIS CODE AS TO HOW WILL THE SYSTEM KNOW WHICH COMMAND TO DO.




            */
                                    LamportAlgorithm.counter = 4;
                                }
                                break;

                        }
                    }
                    break;

                case 4:
                    /*
                    *
                    * PEER REQUEST CODE
                    *
                    * */
                    LamportNew.peers.put(ex1.selfName, ex1.port);
                    break;

            }




















        }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

