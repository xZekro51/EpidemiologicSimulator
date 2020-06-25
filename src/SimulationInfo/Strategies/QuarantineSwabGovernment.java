package SimulationInfo.Strategies;

import SimulationInfo.*;

import java.util.ArrayList;

public class QuarantineSwabGovernment implements SimulationInfo.Strategy {

    boolean applied = false;

    public QuarantineSwabGovernment(){}

    public void Subscribe(StrategyListener listener){
        listener.OnBeforeContagion.Subscribe(this);
    }

    public void ApplyStrategy(Population p, Economy e, HealthCare h){
        //Applies a quarantine state
        if(!applied) {
            ArrayList<Person> quarantined = new ArrayList<>();
            p.Components.forEach((x) -> {
                if (x.Tampone(e)) {
                    quarantined.add(x);
                }
            });
            for (Person pers : quarantined) {
                pers.CurrentState = State.STILL;
            }
            applied = true;
        }
    }

}
