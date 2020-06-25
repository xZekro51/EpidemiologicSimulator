package SimulationInfo;

//Handles all the economy related tasks.
public class Economy {

    //A STILL person consumes one unity of resources. A MOVING person consumes one and produces another one (if all population is moving, resources are constant).
    //This is the reference for the resources: R < 10*P*C
    public int Resources;

    //The cost to run a "Tampone" test. Note that this also influences how much the medication costs for a sick person.
    public static int SwabCost;

    public Economy(int resources){
        Resources = resources;
    }

    public void UpdateResources(Population pop){
        //Removing the cost of every sick and still person
        Resources -= (float)pop.Components.stream().parallel().filter((x)->x.CurrentState == State.STILL && x.Health != HealthState.RED && x.Health != HealthState.BLACK).count();
        Resources -= (float)pop.Components.stream().parallel().filter((x)->x.CurrentState == State.STILL && x.Health == HealthState.RED).count()*SwabCost*3;
    }
}
