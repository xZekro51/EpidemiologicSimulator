import java.util.ArrayList;
import java.sql.Array;
import java.util.List;
import java.util.Random;

//Defining the Population with size, encounter rate and other values
public class Population{

    public ArrayList<Person> Components;

    public int Size = 100; //The number of people in the population

    public float EncounterRate = 13; //The medium number of encounters each person has each day (assuming all are moving)

    public Population(){
        Components = new ArrayList<Person>(Size);
    }

    public Population(int size, float encounterRate){
        Size = size;
        EncounterRate = encounterRate;
        Components = new ArrayList<Person>(Size);
    }

    public void InitializePopulation(){
        for (int i=0;i<Size;i++){
            Person p = new Person();
            if (i==0) {
                p.Health = HealthState.YELLOW;
                p.IsSick = true;
            }
            else
                p.Health = HealthState.GREEN;
            Components.add(p);

        }
    }

    public void UpdatePopulationHealthState(Person p, double sympt, double lethality){
        if (p.Health == HealthState.BLACK || p.Health == HealthState.BLUE)
            return;

        if (p.Health == HealthState.GREEN) {
            //Checking the case whether a sick person has begun the incubation period
            if(p.IsSick) {
                p.IncubationDays--;
                if (p.IncubationDays==0) {
                    p.Health = HealthState.YELLOW;
                    return;
                }
            }
        }

        if (p.Health == HealthState.YELLOW) {
            p.SickCounter++;
            if(p.SickCounter==HealthCare.Duration)
            {
                p.Health = HealthState.BLUE;
                return;
            }
            if (p.SymptomsDays == 0) {
                return;
            }
            if (new Random().nextDouble() * 100 < sympt) {
                p.Health = HealthState.RED; //Setting the current health state of the person to red
                p.DailyCareCost = Economy.SwabCost * 3; //Increasing the daily care cost for each person.
                p.CurrentState = State.STILL; //Setting the current moving state of the person to still
                return;
            } else {
                p.SymptomsDays--;
            }
        }
        if (p.Health == HealthState.RED){
            p.SickCounter++;
            if(p.SickCounter==HealthCare.Duration)
            {
                p.Health = HealthState.BLUE;
                p.CurrentState = State.MOVING;
                return;
            }
            if(new Random().nextDouble()*100<lethality) {
                p.Health = HealthState.BLACK;
                return;
            }else {

            }
        }
    }

    public void UpdatePopulationHealthState(double sympt, double lethality){
        for (Person p : Components){
            if (p.Health == HealthState.BLACK || p.Health == HealthState.BLUE)
                continue;

            if (p.Health == HealthState.GREEN) {
                //Checking the case whether a sick person has begun the incubation period
                if(p.IsSick) {
                    p.IncubationDays--;
                    if (p.IncubationDays==0) {
                        p.Health = HealthState.YELLOW;
                        continue;
                    }
                }
            }

            if (p.Health == HealthState.YELLOW) {
                p.SickCounter++;
                if(p.SickCounter==HealthCare.Duration)
                {
                    p.Health = HealthState.BLUE;
                    continue;
                }
                if (p.SymptomsDays == 0) {
                    continue;
                }
                if (new Random().nextDouble() * 100 < sympt) {
                    p.Health = HealthState.RED; //Setting the current health state of the person to red
                    p.DailyCareCost = Economy.SwabCost * 3; //Increasing the daily care cost for each person.
                    p.CurrentState = State.STILL; //Setting the current moving state of the person to still
                    continue;
                } else {
                    p.SymptomsDays--;
                }
            }
            if (p.Health == HealthState.RED){
                p.SickCounter++;
                if(p.SickCounter==HealthCare.Duration)
                {
                    p.Health = HealthState.BLUE;
                    p.CurrentState = State.MOVING;
                    continue;
                }
                if(new Random().nextDouble()*100<lethality) {
                    p.Health = HealthState.BLACK;
                    continue;
                }else {

                }
            }
        }
    }

    public void SimulateContagion(int curEncounters, Person p,float Vd, double infectivity){
        //If a person is still he's not gonna make encounters. Note: there's still the possibility of moving people to meet the still person
        if (p.CurrentState==State.STILL)
            return;
        //Keep making people meet until they meet the old encounter value + Vd
        while(p.Encounters.size()<curEncounters+(int)Vd){
            Random r = new Random();
            int rIndex = r.nextInt(Components.size());

            //Avoid the case where a Person meets by himself
            if (rIndex != Components.indexOf(p)) {
                //Storing the met person in a variable to increase performance
                Person metPerson = Components.get(rIndex);
                //Avoiding the case where the metPerson is dead
                if(metPerson.Health == HealthState.BLACK)
                    continue;
                //In case a Person meets another, add both to the list of each other encounter
                //Keep in mind that people meeting 2 times is a possibility, so i'm still gonna consider it
                p.Encounters.add(metPerson);
                metPerson.Encounters.add(p);

                //Check if either was sick, if both are sick there's no need to check for contagion
                if ((p.IsSick||metPerson.IsSick) && !(p.IsSick && metPerson.IsSick)){
                    //Make sure i can infect someone only if they can be infected, meaning if they're GREEN
                    //If P is sick then i need to check for the contagion of the metPerson
                    if(p.IsSick && metPerson.Health == HealthState.GREEN){
                        if(new Random().nextInt(100)<infectivity){
                            //If metPerson was infected, mark it as sick and start incubation period
                            metPerson.IsSick = true;
                        }
                    }else if (metPerson.IsSick && p.Health == HealthState.GREEN)
                        if(new Random().nextInt(100)<infectivity){
                            //If metPerson was infected, mark it as sick and start incubation period
                            p.IsSick = true;
                        }
                }
            }
        }
    }

    public void SimulateContagion(float Vd,double infectivity)
    {
        int curEncounters = Components.get(0).Encounters.size();
        for(Person p : Components){
            //If a person is still he's not gonna make encounters. Note: there's still the possibility of moving people to meet the still person
            if (p.CurrentState==State.STILL)
                continue;
            //Keep making people meet until they meet the old encounter value + Vd
            while(p.Encounters.size()<curEncounters+(int)Vd){
                Random r = new Random();
                int rIndex = r.nextInt(Components.size());

                //Avoid the case where a Person meets by himself
                if (rIndex != Components.indexOf(p)) {
                    //Storing the met person in a variable to increase performance
                    Person metPerson = Components.get(rIndex);
                    //Avoiding the case where the metPerson is dead
                    if(metPerson.Health == HealthState.BLACK)
                        continue;
                    //In case a Person meets another, add both to the list of each other encounter
                    //Keep in mind that people meeting 2 times is a possibility, so i'm still gonna consider it
                    p.Encounters.add(metPerson);
                    metPerson.Encounters.add(p);

                    //Check if either was sick, if both are sick there's no need to check for contagion
                    if ((p.IsSick||metPerson.IsSick) && !(p.IsSick && metPerson.IsSick)){
                        //Make sure i can infect someone only if they can be infected, meaning if they're GREEN
                        //If P is sick then i need to check for the contagion of the metPerson
                        if(p.IsSick && metPerson.Health == HealthState.GREEN){
                            if(new Random().nextInt(100)<infectivity){
                                //If metPerson was infected, mark it as sick and start incubation period
                                metPerson.IsSick = true;
                            }
                        }else if (metPerson.IsSick && p.Health == HealthState.GREEN)
                            if(new Random().nextInt(100)<infectivity){
                                //If metPerson was infected, mark it as sick and start incubation period
                                p.IsSick = true;
                            }
                        }
                    }
            }
        }
    }


    //Returns all the yellow
    public int asymptomatic(){
        return (int)Components.stream().parallel().filter((x)-> x.Health == HealthState.YELLOW).count();
    }

    public int dead(){
        return (int)Components.stream().parallel().filter((x)-> x.Health == HealthState.BLACK).count();
    }
    public int recovered(){
        return (int)Components.stream().parallel().filter((x)-> x.Health == HealthState.BLUE).count();
    }

    public int moving(){
        return (int)Components.stream().parallel().filter((x)->x.CurrentState == State.MOVING).count();
    }
    public int red(){
        return (int)Components.stream().parallel().filter((x)-> x.Health == HealthState.RED).count();
    }

    //used to check whether the first red person has appeared or not
    public boolean anyRed(){
        return (int)Components.stream().parallel().filter((x) -> x.Health == HealthState.RED).count() > 0;
    }
}
