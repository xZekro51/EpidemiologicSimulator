package SimulationInfo;

import java.util.ArrayList;
import java.util.Random;

//Holds the specific information about a person in the population, like cost, if he's sick or sane and others.
//Keep in mind an individual lives forever UNLESS he dies for sickness.
public class Person {

    //Holds information about the past encounters of the person.
    public ArrayList<Person> Encounters = new ArrayList<Person>();

    //The health state of the person
    public HealthState Health = HealthState.GREEN;

    //The current state of the person
    public State CurrentState = State.MOVING;

    //Whether the person is sick or not (this does not mean it's contagious yet)
    public boolean IsSick = false;

    private int willBecomeRed = 0; //0 not defined, 1 true, 2 false

    private int willDie = 0; //0 not defined, 1 true, 2 false

    //The daily care cost for the person
    public float DailyCareCost = 0;

    public int IncubationDays = (int)(HealthCare.Duration/6);

    public int SymptomsDays = (int)(HealthCare.Duration/3);

    //Starts from 0 and then begins counting
    public int SickCounter = 0;

    public Person(){ }

    //Runs a "Tampone" test, that will use up resources and return whether the person is contagious or not.
    public boolean Tampone(Economy e){
        e.Resources-=Economy.SwabCost;
        return Health != HealthState.GREEN && Health != HealthState.BLUE;
    }

    public void UpdatePopulationHealthState(double sympt, double lethality){
        //If i'm dead or recovered i'm not gonna change my healthstate
        if (this.Health == HealthState.BLACK || this.Health == HealthState.BLUE)
            return;

        //I increase the sick counter if the person is sick, regardless of the current healthstate
        if (this.IsSick){
            this.SickCounter++;
        }
        //If i reach the end of the sickness i recover
        if (this.SickCounter == HealthCare.Duration){
            this.Health = HealthState.BLUE;
            //If i was previously still, after i recover i wanna move
            if (this.CurrentState == State.STILL)
                this.CurrentState = State.MOVING;
            this.IsSick = false;
            return;
        }


        if (this.Health == HealthState.GREEN) {
            //Checking the case whether a sick person has begun the incubation period
            if (this.IsSick) {
                this.IncubationDays--;
                if (this.IncubationDays==0) {
                    this.Health = HealthState.YELLOW;
                    return;
                }
            }
        }

        if (this.Health == HealthState.YELLOW) {
            //If i can't show symptoms anymore i just stay yellow
            if (this.SymptomsDays==0 && willBecomeRed == 2)
                return;
            else if (this.SymptomsDays==0 && willBecomeRed == 1)
            {
                this.Health = HealthState.RED; //Setting the current health state of the person to red
                this.DailyCareCost = Economy.SwabCost * 3; //Increasing the daily care cost for each person.
                this.CurrentState = State.STILL; //Setting the current moving state of the person to still
                return;
            }
            //I check randomly if it becomes red or not when his destiny is written
            if (willBecomeRed==1){
                if (new Random().nextInt()<50){
                    this.Health = HealthState.RED; //Setting the current health state of the person to red
                    this.DailyCareCost = Economy.SwabCost * 3; //Increasing the daily care cost for each person.
                    this.CurrentState = State.STILL; //Setting the current moving state of the person to still
                    return;
                }
            }

            //I check if i will become red or keep being yellow until i recover
            if(willBecomeRed == 0) {
                if (new Random().nextInt(100) < sympt) {
                    willBecomeRed = 1;
                }else {
                    willBecomeRed = 2;
                }
                    this.SymptomsDays--;
                return;
            }



        }
        if (this.Health == HealthState.RED){
            //if it wont die i'll just wait for it to recover
            if (willDie == 2)
                return;
            //If i will recover next day but i'm destined to die, i'm just gonna die now
            if (willDie == 1 && this.SickCounter+1 == HealthCare.Duration)
            {
                this.Health = HealthState.BLACK;
                this.DailyCareCost = 0;
            }

            //If i know i will die, i check if i die today
            if (willDie == 1) {
                if(new Random().nextInt()<50){
                    this.Health = HealthState.BLACK;
                    this.DailyCareCost = 0;
                }
                return;
            }

            //roll to check if i die
            if(willDie == 0) {
                if (new Random().nextInt(100) < lethality) {
                    willDie = 1;//this.Health = HealthState.BLACK;
                }else{
                    willDie = 2;
                }

            }
        }
    }

    //Returns if the person is contagious or not
    public boolean IsContagious(){
        return Health == HealthState.YELLOW || Health == HealthState.RED;
    }

}
