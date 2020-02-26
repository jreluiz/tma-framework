package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the ConfigurationData database table.
 * 
 */
@Embeddable
public class ConfigurationDataPK implements Serializable {

	private static final long serialVersionUID = -8535322440187316284L;

	@Column
	private int planId;

	@Column
	private int actionRuleId;

	@Column
	private int configurationId;

	public ConfigurationDataPK() {
	}
	public int getPlanId() {
		return this.planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public int getActionRuleId() {
		return this.actionRuleId;
	}
	public void setActionRuleId(int actionRuleId) {
		this.actionRuleId = actionRuleId;
	}
	public int getConfigurationId() {
		return this.configurationId;
	}
	public void setConfigurationId(int configurationId) {
		this.configurationId = configurationId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConfigurationDataPK)) {
			return false;
		}
		ConfigurationDataPK castOther = (ConfigurationDataPK)other;
		return 
			(this.planId == castOther.planId)
			&& (this.actionRuleId == castOther.actionRuleId)
			&& (this.configurationId == castOther.configurationId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.planId;
		hash = hash * prime + this.actionRuleId;
		hash = hash * prime + this.configurationId;
		
		return hash;
	}
	@Override
	public String toString() {
		return "ConfigurationDataPK [planId=" + planId + ", actionRuleId=" + actionRuleId + ", configurationId="
				+ configurationId + "]";
	}
	
}