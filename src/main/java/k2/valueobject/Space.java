package k2.valueobject;

import java.util.Set;

public class Space {
    final Integer costOfEntry;
    final Integer altitude;
    final Integer level;
    final Set<Space> connectionsUpward;
    final Set<Space> connectionsDownward;

    public Space(Integer costOfEntry, Integer altitude, Integer level, Set<Space> connectionsUpward, Set<Space> connectionsDownward) {
        this.costOfEntry = costOfEntry;
        this.altitude = altitude;
        this.level = level;
        this.connectionsUpward = connectionsUpward;
        this.connectionsDownward = connectionsDownward;
    }
}
