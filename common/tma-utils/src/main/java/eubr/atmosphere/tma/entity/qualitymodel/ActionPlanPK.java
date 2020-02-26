package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the ActionPlan database table.
 * 
 */
@Embeddable
public class ActionPlanPK implements Serializable {

	private static final long serialVersionUID = -5791071933346705133L;

	@Column
	private int planId;

	@Column
	private int actionRuleId;

	public ActionPlanPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ActionPlanPK)) {
			return false;
		}
		ActionPlanPK castOther = (ActionPlanPK)other;
		return 
			(this.planId == castOther.planId)
			&& (this.actionRuleId == castOther.actionRuleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.planId;
		hash = hash * prime + this.actionRuleId;
		
		return hash;
	}
}