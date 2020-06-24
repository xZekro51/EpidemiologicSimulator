package SimulationInfo;

public enum HealthState {
    GREEN, //Sane person. Can move, state can be either
    YELLOW, //Asyntomatic, but contagious. Can move, state can be either
    RED, //Sick and countagious, cannot move, state is STILL
    BLUE, //Recovered person, can move, state can be either
    BLACK //Dead. Cannot move, meaning state is STILL, doesnt consume any resources and is not contagious.
}
