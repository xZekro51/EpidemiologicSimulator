package SimulationInfo;

import java.util.ArrayList;
import java.util.Random;

//Defining the Simulation.Population with size, encounter rate and other values
public class Population{

    //The actual list holding all the persons in the population.
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

    //Initializes the population
    public void InitializePopulation(){
        //Creates a new population full of sane persons
        for (int i=0;i<Size;i++){
            Person p = new Person();
            p.Health = HealthState.GREEN;
            Components.add(p);
        }
        //Gets a random r value that will indicate who is the infected person.
        int r = new Random().nextInt(Size);
        Components.get(r).Health = HealthState.YELLOW;
        Components.get(r).IsSick = true;
    }

    public void SimulateContagion(int curEncounters, Person p,float Vd, double infectivity){
        //If a person is still he's not gonna make encounters. Note: there's still the possibility of moving people to meet the still person
        if (p.CurrentState==State.STILL)
            return;
        //Keep making people meet until they meet the old encounter value + Vd
        while(p.Encounters.size()<curEncounters+Math.round(Vd)){
            Random r = new Random();
            int rIndex = r.nextInt(Size);

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

                //Check if either is sick
                if (p.IsContagious()||p.IsContagious()){
                    //Make sure i can infect someone only if they can be infected, meaning if they're GREEN
                    //If P is sick then i need to check for the contagion of the metPerson
                    if(p.IsContagious() && metPerson.Health == HealthState.GREEN && !metPerson.IsSick){
                        if(new Random().nextInt(100)<infectivity){
                            //If metPerson was infected, mark it as sick and start incubation period
                            metPerson.IsSick = true;
                        }
                    }else if (metPerson.IsContagious() && p.Health == HealthState.GREEN && !p.IsSick)
                        if(new Random().nextInt(100)<infectivity){
                            //If metPerson was infected, mark it as sick and start incubation period
                            p.IsSick = true;
                        }
                }
            }
        }
    }

    //Returns all the yellow
    public int asymptomatic(){
        return (int)Components.stream().parallel().filter((x)-> x.Health == HealthState.YELLOW).count();
    }

    //Returns all the sick persons
    public int sick(){
        return (int)Components.stream().parallel().filter((x)-> x.IsSick && x.Health!=HealthState.BLACK).count();
    }

    //Returns all the dead persons
    public int dead(){
        return (int)Components.stream().parallel().filter((x)-> x.Health == HealthState.BLACK).count();
    }

    //Returns all the recovered persons
    public int recovered(){
        return (int)Components.stream().parallel().filter((x)-> x.Health == HealthState.BLUE).count();
    }

    //Returns all the moving persons
    public int moving(){
        return (int)Components.stream().parallel().filter((x)->x.CurrentState == State.MOVING).count();
    }

    //Returns all the still persons
    public int still(){
        return (int)Components.stream().parallel().filter((x)->x.CurrentState == State.STILL && x.Health != HealthState.BLACK).count();
    }
    //Returns all the RED (Infected and symptomatic) persons
    public int red(){
        return (int)Components.stream().parallel().filter((x)-> x.Health == HealthState.RED).count();
    }

    //used to check whether the first red person has appeared or not
    public boolean anyRed(){
        return (int)Components.stream().parallel().filter((x) -> x.Health == HealthState.RED).count() > 0;
    }
}
