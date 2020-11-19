package eubr.atmosphere.tma.entity.qualitymodel;

public enum PlanStatus {

	BUILDING,
	READY_TO_RUN,
    IN_PROGRESS,
    COMPLETED;
    
    @Override
    public String toString() {
        return Integer.toString(ordinal());
    }

    public static PlanStatus valueOf(int ordinal) {
        return (ordinal < values().length) ? values()[ordinal]
                : COMPLETED;
    }
	
}
