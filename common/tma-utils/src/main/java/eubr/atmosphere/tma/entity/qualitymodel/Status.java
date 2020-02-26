package eubr.atmosphere.tma.entity.qualitymodel;

public enum Status {

	BUILDING,
    TO_DO,
    IN_PROGRESS,
    COMPLETED;
    
    @Override
    public String toString() {
        return Integer.toString(ordinal());
    }

    public static Status valueOf(int ordinal) {
        return (ordinal < values().length) ? values()[ordinal]
                : COMPLETED;
    }
	
}
