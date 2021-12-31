package hu.gemesi.taskmaker.common.model.prize;

import hu.gemesi.taskmaker.common.model.user.UserRole;

public enum PrizeEnumType {
    FIRST("Első"),
    SECOND("Második"),
    THIRD("Harmadik");
    private final String value;

    PrizeEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PrizeEnumType fromValue(String v) {
        for (PrizeEnumType c: PrizeEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
