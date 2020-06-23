
//Handles all the economy related tasks.
public class Economy {

    //A STILL person consumes one unity of resources. A MOVING person consumes one and produces another one (if all population is moving, resources are constant).
    //This is the reference for the resources: R < 10*P*C
    public int Resources;

    public static int SwabCost;

    public Economy(int resources){
        Resources = resources;
    }

    public void UpdateResources(Population pop){
        //Removing the cost of every sick and still person
        Resources -= (float)pop.Components.stream().parallel().filter((x)->x.CurrentState == State.STILL && x.Health != HealthState.RED && x.Health != HealthState.BLACK).count();
        Resources -= (float)pop.Components.stream().parallel().filter((x)->x.CurrentState == State.STILL && x.Health == HealthState.RED).count()*SwabCost*3;
        //Every moving person produces 1 unit back to the economy resources
        //Resources += (float)pop.Components.stream().parallel().filter((x)->x.CurrentState == State.MOVING).count();
    }
}
