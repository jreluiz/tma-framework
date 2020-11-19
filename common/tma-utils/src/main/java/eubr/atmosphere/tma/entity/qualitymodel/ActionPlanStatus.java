package eubr.atmosphere.tma.entity.qualitymodel;

public enum ActionPlanStatus {

    RUNNING,
    EXECUTED;
    
    @Override
    public String toString() {
        return Integer.toString(ordinal());
    }

    public static ActionPlanStatus valueOf(int ordinal) {
        return (ordinal < values().length) ? values()[ordinal]
                : EXECUTED;
    }
	
}
