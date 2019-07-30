package eubr.atmosphere.tma.qualitymodel.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the ConfigurationDataPK database table.
 * 
 */
@Embeddable
public class ConfigurationDataPK implements Serializable {

	private static final long serialVersionUID = -2410822925796130606L;

	private int actionId;

	private int configurationId;

	public ConfigurationDataPK() {
	}
	public int getActionId() {
		return this.actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
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
			(this.actionId == castOther.actionId)
			&& (this.configurationId == castOther.configurationId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.actionId;
		hash = hash * prime + this.configurationId;
		
		return hash;
	}
}