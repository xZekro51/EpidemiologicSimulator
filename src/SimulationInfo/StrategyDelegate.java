package SimulationInfo;

public class StrategyDelegate {

    private Strategy d;

    public void ApplyStrategy(Population p,Economy e,HealthCare h){

        if(d==null) {
            System.out.println("No strat found");
            return;
        }
        d.ApplyStrategy(p,e,h);
    }

    public void Clear(){
        d = null;
    }

    public void Subscribe(Strategy s){
        d=s;
    }

}
