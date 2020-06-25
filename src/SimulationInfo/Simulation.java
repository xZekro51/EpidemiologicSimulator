package SimulationInfo;

import SimulationInfo.Strategies.NoActionGovernment;

public class Simulation {

    public boolean Running = false;
    public boolean RanOnce = false;
    public int ElapsedDays = 0;
    public Population Population;
    public Economy Economy;
    public HealthCare HealthCareInstance;

    public StrategyListener EventStrategy = new StrategyListener();

    public int PopSize;
    public int Resources;
    public double SwabCost;
    public double Infectivity;
    public double Lethality;
    public double SymptChance;
    public int Duration;
    public float V;
    public float R0=0;
    public Strategy Strategy;
    public Class StrategyClass;


    public boolean FirstRedFound;

    public Thread MainThread;
    public Thread ContagionThread;

    public void RunOneDaySimulation(){

        if (Population==null)
            Population = new Population(PopSize,V);
        if (Economy == null) {
            Economy = new Economy(Resources);
            Economy.SwabCost = (int)SwabCost;
        }
        if (EventStrategy.Cleared && StrategyClass != null){
            try {
                Strategy = (Strategy) StrategyClass.newInstance();
            }catch(Exception e){}

        }

        if (Strategy!=null && EventStrategy.Cleared) {
            Strategy.Subscribe(EventStrategy);
            EventStrategy.Cleared = false;
        }
        if (Population.Size == 0 || Economy.Resources == 0)
            return;
        RanOnce = true;
        HealthCare.Duration = Duration;

        if (ElapsedDays == 0)
            Population.InitializePopulation();

        if (Population.anyRed() && !FirstRedFound)
            FirstRedFound = true;
        System.out.println("First red found? "+String.valueOf(FirstRedFound));
        if (FirstRedFound)
            EventStrategy.OnBeforeContagion.ApplyStrategy(Population,Economy,HealthCareInstance);

        float vd = Population.moving()/(float)Population.Size;//(V/Population.Size) * Population.moving();
        //Simulation.Population.SimulateContagion(vd,Infectivity);
        int curEnc = Population.Components.get(0).Encounters.size();
        try {
            Population.Components.parallelStream().parallel().forEach((x) -> Population.SimulateContagion(curEnc, x, V, Infectivity));
        }
        catch(ArrayIndexOutOfBoundsException e){}

        if (FirstRedFound)
            EventStrategy.OnAfterContagion.ApplyStrategy(Population,Economy,HealthCareInstance);
        //Simulation.Population.UpdatePopulationHealthState(SymptChance,Lethality);
        try{
            Population.Components.parallelStream().parallel().forEach((x)->x.UpdatePopulationHealthState(SymptChance,Lethality));
        }
        catch(ArrayIndexOutOfBoundsException e){}

        if (FirstRedFound)
            EventStrategy.OnAfterHealthUpdate.ApplyStrategy(Population,Economy,HealthCareInstance);
        Economy.UpdateResources(Population);
        if (FirstRedFound)
            EventStrategy.OnAfterResourceUpdate.ApplyStrategy(Population,Economy,HealthCareInstance);
        ElapsedDays++;
        if (FirstRedFound)
            EventStrategy.OnDayEnd.ApplyStrategy(Population,Economy,HealthCareInstance);

        R0=(float)(vd*Duration/10.0*Infectivity/(float)100);

        System.out.println(String.format("Simulation for day %s finalized.",String.valueOf(ElapsedDays)));
        System.out.println("Is there any red? " + String.valueOf(Population.anyRed()));
        System.out.println("Sick overall (not dead): "+ String.valueOf(Population.sick()));
        System.out.println("Asymptomatic: " + String.valueOf(Population.asymptomatic()));
        System.out.println("Dead Count: " + String.valueOf(Population.dead()));
        System.out.println("Recovered Count: " + String.valueOf(Population.recovered()));
        System.out.println("Current Vd is " + String.valueOf(vd));
        System.out.println("Current R0 is " + String.valueOf(Math.round(R0)));
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
        EventStrategy.Clear();
        FirstRedFound = false;
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
            if (Strategy != null){
                try {
                    Strategy = (Strategy) StrategyClass.newInstance();
                }catch(Exception e){}
            }
        }
    }
}
