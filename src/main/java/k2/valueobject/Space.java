package k2.valueobject;

import java.util.Set;

public class Space {
    final Integer costOfEntry;
    final Integer altitude;
    final Set<Space> connectionsUpward;
    final Set<Space> connectionsDownward;

    public Space(Integer costOfEntry, Integer altitude, Set<Space> connectionsUpward, Set<Space> connectionsDownward) {
        this.costOfEntry = costOfEntry;
        this.altitude = altitude;
        this.connectionsUpward = connectionsUpward;
        this.connectionsDownward = connectionsDownward;
    }
}
