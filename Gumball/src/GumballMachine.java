/**
 * Created by Pultilian on 2/28/2018.
 */
public class GumballMachine {
    int gumballs;
    int quaters;
    State state;


    public GumballMachine() {
        this.gumballs = 100;
        this.quaters = 0;
        this.state = new State.YesGNoQ();
    }

    public void addGumballs(int count) {
        this.gumballs += count;
    }

    public void insertQuarter(){
        try {
            if (state.isQuarterInSlot()) {
                throw new Exception("Quarter in Slot");
            } else {
                if(state.isGumballs()){
                    state = new State.YesGYesQ();
                } else {
                    state = new State.NoGYesQ();
                }
            }
        }catch (Exception e){
            System.out.print(e);
        }
    }

    public void removeQuarter(){
        try {
            if(state.isQuarterInSlot()){
                if(state.isGumballs()){
                    state = new State.YesGNoQ();
                } else {
                    state = new State.NoGNoQ();
                }
            } else {
                throw new Exception("No Quarter in Slot");
            }
        } catch (Exception e){
            System.out.print(e);
        }
    }

    public void turnHandle(){
        try {
            if (state.isQuarterInSlot()) {
                this.quaters++;
                if (gumballs-1 != 0) {
                    this.gumballs--;
                    state = new State.YesGNoQ();
                } else if (gumballs - 1 == 0){
                    this.gumballs--;
                    state = new State.NoGNoQ();
                } else {
                    state = new State.NoGNoQ();
                }
            } else {
                throw new Exception("No QuarterInSlot");
            }
        } catch (Exception e){
            System.out.print(e);
        }
    }
}

public interface State {
    boolean gumballs = false;
    boolean quarterInSlot = false;

    public boolean isQuarterInSlot();
    public boolean isGumballs();

    public class NoGNoQ implements State{
        public NoGNoQ(){
            gumballs = false;
            quarterInSlot = false;
        }
        public boolean isGumballs(){
            return false;
        }
        public boolean isQuarterInSlot(){
            return false;
        }
    }

    public class NoGYesQ implements State{
        public NoGYesQ(){
            gumballs = false;
            quarterInSlot = true;
        }
        public boolean isGumballs(){
            return false;
        }
        public boolean isQuarterInSlot(){
            return true;
        }

    }

    public class YesGNoQ implements State{
        public YesGNoQ(){
            gumballs = true;
            quarterInSlot = false;
        }
        public boolean isGumballs(){
            return true;
        }
        public boolean isQuarterInSlot(){
            return false;
        }
    }

    public class YesGYesQ implements State{
        public YesGYesQ(){
            gumballs = true;
            quarterInSlot = true;
        }
        public boolean isGumballs(){
            return true;
        }
        public boolean isQuarterInSlot(){
            return true;
        }
    }
}
