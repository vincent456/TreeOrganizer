package vincenthudry.organizer.utils;

public class DataTrigger {
    public Object object;
    private boolean trigger;

    public DataTrigger(){
        trigger=false;
    }

    public Object waitForData(){
        while (!trigger){}
        return object;
    }

    public void armTrigger(){
        trigger=false;
    }

    public void fireTrigger(){
        trigger=true;
    }

}
