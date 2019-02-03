package k2.valueobject;

public enum Space {

    BASE_CAMP(5000, 1, 1),
    S1(5000, 1, 1),
    S2(5000, 1, 1),
    S3(5000, 1, 1),
    S4(5000, 1, 1),
    S5(5000, 1, 1),
    S6(5000, 1, 1, 2),
    S7(5000, 1, 1),
    S8(5000, 1, 1),
    S9(5000, 1, 0),
    S10(5000, 1, 0),
    S11(5000, 1, 0),
    S12(5000, 1, 0, 2),

    S13(6000, 2, 0, 2),
    S14(6000, 2, 0, 2),
    S15(6000, 2, 0, 2),

    S16(6000, 3, 0, 1),
    S18(6000, 3, -1, 1),

    S17(6000, 4, -1, 3),
    S19(6000, 4, -1, 2),
    S20(6000, 4, -1, 2),

    S21(7000, 5, -1, 2),
    S22(7000, 5, -1, 2),
    S24(7000, 5, -1, 2),

    S25(7000, 6, -1, 2),
    S23(7000, 6, -2, 3),

    S26(8000, 7, -2, 3),

    SUMMIT(8000, 8, -2, 3);

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
