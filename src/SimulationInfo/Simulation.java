package SimulationInfo;

public class Simulation {

    public boolean Running = false;
    public boolean RanOnce = false;
    public int ElapsedDays = 0;
    public Population Population;
    public Economy Economy;
    public static HealthCare HealthCareInstance;

    public int PopSize;
    public int Resources;
    public double SwabCost;
    public double Infectivity;
    public double Lethality;
    public double SymptChance;
    public int Duration;
    public float V;

    public float Vd;

    public Thread MainThread;
    public Thread ContagionThread;

    public void RunOneDaySimulation(){

        if (Population==null)
            Population = new Population(PopSize,V);
        if (Economy == null) {
            Economy = new Economy(Resources);
            Economy.SwabCost = (int)SwabCost;
        }
        System.out.println(String.format("%d %d",Population.Size,Economy.Resources));
        if (Population.Size == 0 || Economy.Resources == 0)
            return;
        RanOnce = true;
        HealthCare.Duration = Duration;

        if (ElapsedDays == 0)
            Population.InitializePopulation();

        float vd = (V/Population.Size) * Population.moving();
        System.out.println("Current Vd is " + String.valueOf(vd));
        //Simulation.Population.SimulateContagion(vd,Infectivity);
        int curEnc = Population.Components.get(0).Encounters.size();
        Population.Components.stream().parallel().forEach((x)->Population.SimulateContagion(curEnc,x,vd,Infectivity));
        //Simulation.Population.UpdatePopulationHealthState(SymptChance,Lethality);
        Population.Components.stream().parallel().forEach((x)->Population.UpdatePopulationHealthState(x,SymptChance,Lethality));
        Economy.UpdateResources(Population);
        ElapsedDays++;

        System.out.println(String.format("Simulation.Simulation for day %s finalized.",String.valueOf(ElapsedDays)));
        System.out.println("Is there any red? " + String.valueOf(Population.anyRed()));
        System.out.println("Asymptomatic: " + String.valueOf(Population.asymptomatic()));
        System.out.println("Dead Count: " + String.valueOf(Population.dead()));
        System.out.println("Recovered Count: " + String.valueOf(Population.recovered()));
    }

    public int alive(){
        if (Population!=null)
            return Population.Size-Population.dead();
        else
            return 0;
    }

    public int currentResources(){
        if (Economy!=null)
            return Economy.Resources;
        else
            return 0;
    }

    public void ResetSimulation(){
        Population = new Population(PopSize,V);
        Economy = new Economy(Resources);
        Economy.SwabCost = (int)SwabCost;
        ElapsedDays = 0;
    }

    public void UpdateSimulationValues(){
        //Using this to prevent the updating of values after i've already run it once. If i want to reset the simulation,
        //I'll add another button
        if (!RanOnce) {
            if (Population != null) {
                Population.Size = PopSize;
            }
            if (Economy != null) {
                Economy.Resources = Resources;
                Economy.SwabCost = (int)SwabCost;
            }

        }
    }
}
