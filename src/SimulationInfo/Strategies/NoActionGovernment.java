package SimulationInfo.Strategies;

import SimulationInfo.*;

public class NoActionGovernment implements SimulationInfo.Strategy {

    public void Subscribe(StrategyListener listener){
        listener.OnBeforeContagion.Subscribe(this);
    }

    public void ApplyStrategy(Population p, Economy e, HealthCare h){}

}
