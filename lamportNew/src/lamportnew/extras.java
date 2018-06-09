import java.io.Serializable;
import java.util.ArrayList;

public class extras implements Serializable {
public int messageId;
public int port;
public String operation;
public String selfName;
//public int lamportClock;
public  String fileName;
public int time;
public String commandSent;
public String fileContent;
    public boolean isComplete;
public  ArrayList<String> serverFiles=new ArrayList<>();
}
