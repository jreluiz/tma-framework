package eubr.atmosphere.tma.entity.qualitymodel;

/**
 * Rule status enumeration
 * @author JorgeLuiz
 */
public enum RuleStatus {
	
	ACTIVE(0, "ACTIVE"), NOT_ACTIVE(1, "NOT_ACTIVE");

	private Integer valor;
	private String label;

	private RuleStatus(Integer valor, String label) {
		this.valor = valor;
		this.label = label;
	}

	public Integer getValor() {
		return valor;
	}

	public String getLabel() {
		return label;
	}

	public static RuleStatus getEnumByValor(Integer valor) {
		if (valor == null) {
			return null;
		}
		for (RuleStatus item : RuleStatus.values()) {
			if (item.getValor().intValue() == valor.intValue()) {
				return item;
			}
		}
		return null;
	}
	
	public boolean isActive() {
		return this == ACTIVE;
	}
	
	public boolean isNotActive() {
		return this == NOT_ACTIVE;
	}
	
}
