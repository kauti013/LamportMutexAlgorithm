import java.util.Comparator;

public class pqcompatpr implements Comparator< priorityQueueOperation> {
public pqcompatpr(){
    System.out.println("pqcompator has been called");
}
    public int compare(priorityQueueOperation o1, priorityQueueOperation o2) {
        System.out.println("O1. LAMPort TIme"+o1.lamportTime);
        System.out.println("o2. LAMPORT TIME"+o2.lamportTime);
        if(o1.equals(o2))
            return 0;
        if(o1.lamportTime> o2.lamportTime) {
            System.out.println("O1. LAMPort TIme"+o1.lamportTime);
            System.out.println("o2. LAMPORT TIME"+o2.lamportTime);
            return 1;

        }else
            return -1;
    }
}
