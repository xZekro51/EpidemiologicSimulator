import java.util.ArrayList;

//Holds the specific information about a person in the population, like cost, if he's sick or sane and others.
//Keep in mind an individual lives forever UNLESS he dies for sickness.
public class Person {

    public ArrayList<Person> Encounters = new ArrayList<Person>(); //Holds information about the past encounters of the person.

    public HealthState Health = HealthState.GREEN;

    public State CurrentState = State.MOVING;

    public boolean IsSick = false;

    public float DailyCareCost = 0;

    public int IncubationDays = (int)(HealthCare.Duration/6);

    public int SymptomsDays = (int)(HealthCare.Duration/3);

    //Starts from 0 and then begins counting
    public int SickCounter = 0;

    public Person(){ }

    public Person(boolean sick, float cost){
        IsSick = sick;
        DailyCareCost = cost;
    }

    public boolean Tampone(){
        return Health != HealthState.GREEN && Health != HealthState.BLUE;
    }

}
