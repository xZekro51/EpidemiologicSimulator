package SimulationInfo.Strategies;

import SimulationInfo.*;

import java.util.HashSet;
import java.util.Set;

public class EncounterTracingGovernment implements Strategy {

    int delayCounter = 15;
    int delay = 0;

    public void Subscribe(StrategyListener listener){
        listener.OnBeforeContagion.Subscribe(this);
    }

    public void ApplyStrategy(Population p, Economy e, HealthCare h){
        //I apply a multiple encounter tracing
        if (delay == 0) {
            for (Person pers: p.Components) {
                if(pers.Health==HealthState.RED)
                    EncounterTrace(pers);
            }
            System.out.println("Applied Encounter Tracing.");
            delay = delayCounter;
        }else
        {
            delay--;
        }
    }

    public void EncounterTrace(Person p){
        p.CurrentState = State.STILL;
        Set<Person> s = new HashSet<Person>(p.Encounters);
        s.parallelStream().forEach((x) -> x.CurrentState = State.STILL);
    }
}
