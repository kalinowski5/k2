package k2.valueobject;

public enum Space {

    BASE_CAMP(5000, 1, 1),
    S1(5000, 1, 1),
    S2(5000, 1, 1),
    S3(5000, 1, 1),
    S4(5000, 1, 1),
    S5(5000, 1, 1),
    S6(5000, 1, 1),
    S7(5000, 1, 1),
    SUMMIT(8000, 10, -2, 3);

    private Integer altitude;
    private Integer level;
    private Integer acclimatizationModifier;
    private Integer costOfEntry;

    private Space(Integer altitude, Integer level, Integer acclimatizationModifier) {
        this.altitude = altitude;
        this.level = level;
        this.acclimatizationModifier = acclimatizationModifier;
        this.costOfEntry = 1;
    }

    private Space(Integer altitude, Integer level, Integer acclimatizationModifier, Integer costOfEntry) {
        this.altitude = altitude;
        this.level = level;
        this.acclimatizationModifier = acclimatizationModifier;
        this.costOfEntry = costOfEntry;
    }

    public Integer getCostOfEntry() {
        return costOfEntry;
    }
}
