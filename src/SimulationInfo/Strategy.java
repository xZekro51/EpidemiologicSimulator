package SimulationInfo;

//Standard class for a strategy, declares an ApplyStrategy and Subscribe method
public interface Strategy {

    void ApplyStrategy(Population p,Economy e,HealthCare h);

    void Subscribe(StrategyListener listener);

}
