package k2.valueobject;

import java.util.Objects;

public class Space {
    final String id;
    final Integer altitude;
    final Integer level;
    final Integer acclimatizationModifier;
    final Integer costOfEntry;

    public Space(String id, Integer altitude, Integer level, Integer acclimatizationModifier) {
        this.id = id;
        this.altitude = altitude;
        this.level = level;
        this.acclimatizationModifier = acclimatizationModifier;
        this.costOfEntry = 1;
    }

    public Space(String id, Integer altitude, Integer level, Integer acclimatizationModifier, Integer costOfEntry) {
        this.id = id;
        this.altitude = altitude;
        this.level = level;
        this.acclimatizationModifier = acclimatizationModifier;
        this.costOfEntry = costOfEntry;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Space compareTo = (Space) o;

        return Objects.equals(compareTo.getId(), this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Space " + id;
    }
}
