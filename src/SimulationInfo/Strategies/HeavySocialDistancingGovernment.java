package SimulationInfo.Strategies;

import SimulationInfo.*;

public class HeavySocialDistancingGovernment implements SimulationInfo.Strategy {

    public void Subscribe(StrategyListener listener){
        listener.OnBeforeContagion.Subscribe(this);
    }

    public void ApplyStrategy(Population p, Economy e, HealthCare h){
        //Applies heavy social distancing, only 1/8 of the persons can still move
        for(int i =0;i<p.Size/8*7;i++){
            p.Components.get(i).CurrentState = State.STILL;
        }
    }

}
