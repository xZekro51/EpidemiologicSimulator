package SimulationInfo.Strategies;

import SimulationInfo.*;

public class LightSocialDistancingGovernment implements Strategy {

    @Override
    public void ApplyStrategy(Population p, Economy e, HealthCare h){
        //Applying a light social distancing. Half the population will be put into lockdown
        for(int i =0;i<p.Size/2;i++){
            p.Components.get(i).CurrentState = State.STILL;
        }
    }

    public void Subscribe(StrategyListener s){
        s.OnBeforeContagion.Subscribe(this);
    }

}
