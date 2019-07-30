package eubr.atmosphere.tma.qualitymodel.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the ActionMetric database table.
 * 
 */
@Embeddable
public class ActionMetricPK implements Serializable {

	private static final long serialVersionUID = 7624180214816599218L;

	private int actionId;

	private int attributeId;

	public ActionMetricPK() {
	}
	public int getActionId() {
		return this.actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	public int getAttributeId() {
		return this.attributeId;
	}
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ActionMetricPK)) {
			return false;
		}
		ActionMetricPK castOther = (ActionMetricPK)other;
		return 
			(this.actionId == castOther.actionId)
			&& (this.attributeId == castOther.attributeId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.actionId;
		hash = hash * prime + this.attributeId;
		
		return hash;
	}
}