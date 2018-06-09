

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class priorityQueueOperation implements Comparable<priorityQueueOperation>{
public  int lamportTime;
public  String filename;
public  String nodeName;
   // priorityQueueOperation po2=new priorityQueueOperation();
    public  static ArrayList<priorityQueueOperation>pqfile1=new ArrayList<priorityQueueOperation>();
    public  static ArrayList<priorityQueueOperation>pqfile2=new ArrayList<priorityQueueOperation>();
    public  static ArrayList<priorityQueueOperation>pqfile3=new ArrayList<priorityQueueOperation>();
   // public static PriorityBlockingQueue<priorityQueueOperation> pqfile1=new PriorityBlockingQueue<priorityQueueOperation>();
   // public static PriorityBlockingQueue<priorityQueueOperation> pqfile2=new PriorityBlockingQueue<priorityQueueOperation>();
  //  public static PriorityBlockingQueue<priorityQueueOperation> pqfile3=new PriorityBlockingQueue<priorityQueueOperation>();
    //public static PriorityQueue<priorityQueueOperation> pqfile2=new PriorityQueue<priorityQueueOperation>();
    //public static PriorityQueue<priorityQueueOperation> pqfile3=new PriorityQueue<priorityQueueOperation>();

public void addToQueue(String nodeName,int lamportTime,String filename){
    priorityQueueOperation po2=new priorityQueueOperation();
    po2.lamportTime =lamportTime;
    po2.filename =filename;
    po2.nodeName =nodeName;
synchronized (LamportNew.class) {
    switch (filename) {
        case "file1":
            System.out.println(" ADDING PRIORITY QUEUE OPERATION");

            priorityQueueOperation.pqfile1.add(po2);
            Collections.sort(pqfile1);
            for (priorityQueueOperation str : pqfile1) {
                System.out.println(str);
            }
            LamportNew.lamportClock += 1;
            break;
        case "file2":
            // pqfile2.add(po2);
            //LamportNew.lamportClock+=1;
            System.out.println(" ADDING OF PRIORITY QUEUE OPERATION");

            priorityQueueOperation.pqfile2.add(po2);
            Collections.sort(pqfile2);
            for (priorityQueueOperation str : pqfile2) {
                System.out.println(str);
            }
            LamportNew.lamportClock += 1;
            break;
        case "file3":
            // pqfile3.add(po2);
            //LamportNew.lamportClock+=1;
            System.out.println(" ADDING OF PRIORITY QUEUE OPERATION");

            priorityQueueOperation.pqfile3.add(po2);
            Collections.sort(pqfile3);
            for (priorityQueueOperation str : pqfile3) {
                System.out.println(str);
            }
            LamportNew.lamportClock += 1;
            break;
    }
}
}
    public void removeFromQueue(String filename) {
        /*priorityQueueOperation po2=new priorityQueueOperation();
        po2.lamportTime =lamportTime;
        po2.filename =filename;
        po2.nodeName =nodeName;*/
        synchronized (LamportNew.class) {
            switch (filename) {
                case "file1":
                    System.out.println(" ****************************************** REMOVING PRIORITY QUEUE OPERATION***************************");

                    priorityQueueOperation.pqfile1.remove(0);
                    //  Collections.sort(pqfile1);
                    //pqfile1.poll();
                    for (priorityQueueOperation str : pqfile1) {
                        System.out.println(str);
                    }
                    //  LamportNew.lamportClock+=1;
                    break;
                case "file2":
                    // pqfile2.poll();
                    // LamportNew.lamportClock+=1;
                    System.out.println(" ******************************************PRIORITY QUEUE OPERATION***************************");

                    priorityQueueOperation.pqfile2.remove(0);
                    //  Collections.sort(pqfile1);
                    //pqfile1.poll();
                    for (priorityQueueOperation str : pqfile1) {
                        System.out.println(str);
                    }
                    break;
                case "file3":
                    System.out.println(" ******************************************PRIORITY QUEUE OPERATION***************************");

                    priorityQueueOperation.pqfile3.remove(0);
                    //  Collections.sort(pqfile1);
                    //pqfile1.poll();
                    for (priorityQueueOperation str : pqfile1) {
                        System.out.println(str);
                    }
                    break;
            }
        }
    }

    public int compareTo(priorityQueueOperation o) {
       // return 0;
        int compareage=((priorityQueueOperation)o).getTime();
        /* For Ascending order*/
        return this.lamportTime -compareage;
    }
    public int getTime()
    {
        return lamportTime;
    }
    public String toString() {
        return "[ Name=" + nodeName + ", Filename" + filename + ", Lamport Clock =" + lamportTime + "]";
    }
}
