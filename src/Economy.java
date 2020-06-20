
//Handles all the economy related tasks.
public class Economy {

    //A STILL person consumes one unity of resources. A MOVING person consumes one and produces another one (if all population is moving, resources are constant).
    //This is the reference for the resources: R < 10*P*C
    public int Resources;

    public int SwabCost;

    public Economy(int resources){
        Resources = resources;
    }

}
