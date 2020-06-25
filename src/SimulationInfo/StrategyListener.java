package SimulationInfo;

public class StrategyListener {

    public boolean Cleared = true;

    public StrategyDelegate OnBeforeContagion = new StrategyDelegate();
    public StrategyDelegate OnAfterContagion = new StrategyDelegate();
    public StrategyDelegate OnAfterHealthUpdate = new StrategyDelegate();
    public StrategyDelegate OnAfterResourceUpdate = new StrategyDelegate();
    public StrategyDelegate OnDayEnd = new StrategyDelegate();

    public void Clear(){
        OnBeforeContagion.Clear();
        OnAfterContagion.Clear();
        OnAfterHealthUpdate.Clear();
        OnAfterResourceUpdate.Clear();
        OnDayEnd.Clear();
        Cleared = true;
    }

}
