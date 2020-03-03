package eubr.atmosphere.tma.entity.qualitymodel;

public enum RuleAttributeStatus {

	NOT_EXECUTED,
	EXECUTED;
    
    @Override
    public String toString() {
        return Integer.toString(ordinal());
    }

    public static RuleAttributeStatus valueOf(int ordinal) {
        return (ordinal < values().length) ? values()[ordinal]
                : EXECUTED;
    }
	
}
