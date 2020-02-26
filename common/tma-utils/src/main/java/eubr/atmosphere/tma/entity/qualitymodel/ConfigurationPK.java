package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the Configuration database table.
 * 
 */
@Embeddable
public class ConfigurationPK implements Serializable {

	private static final long serialVersionUID = 4695226215578477294L;

	private int configurationId;

	@Column(insertable=false, updatable=false)
	private int actionRuleId;

	public ConfigurationPK() {
	}
	public int getConfigurationId() {
		return this.configurationId;
	}
	public void setConfigurationId(int configurationId) {
		this.configurationId = configurationId;
	}
	public int getActionRuleId() {
		return this.actionRuleId;
	}
	public void setActionRuleId(int actionRuleId) {
		this.actionRuleId = actionRuleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConfigurationPK)) {
			return false;
		}
		ConfigurationPK castOther = (ConfigurationPK)other;
		return 
			(this.configurationId == castOther.configurationId)
			&& (this.actionRuleId == castOther.actionRuleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.configurationId;
		hash = hash * prime + this.actionRuleId;
		
		return hash;
	}
}