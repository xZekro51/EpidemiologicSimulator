package SimulationInfo.Strategies;

import SimulationInfo.*;

import java.util.ArrayList;

public class MultipleQuarantineSwabGovernment implements SimulationInfo.Strategy {

    int dayCounter = 0;

    int dayDelay = 15;

    public MultipleQuarantineSwabGovernment(){}

    public void Subscribe(StrategyListener listener){
        listener.OnBeforeContagion.Subscribe(this);
    }

    public void ApplyStrategy(Population p, Economy e, HealthCare h){
        //Applies a quarantine state multiple times with a delay
        if(dayCounter <= 0) {
            ArrayList<Person> quarantined = new ArrayList<>();
            e.Resources -= Economy.SwabCost * p.Components.size();
            p.Components.forEach((x) -> {
                if (x.CurrentState==State.MOVING && x.Tampone(e)) {
                    quarantined.add(x);
                }
            });
            for (Person pers : quarantined) {
                pers.CurrentState = State.STILL;
            }
            dayCounter = dayDelay;
        }else{
            dayCounter--;
        }
    }

}

