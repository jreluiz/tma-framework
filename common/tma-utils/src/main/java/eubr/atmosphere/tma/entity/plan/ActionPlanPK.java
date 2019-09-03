package eubr.atmosphere.tma.entity.plan;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the PlanAction database table.
 * 
 */
@Embeddable
public class ActionPlanPK implements Serializable {

	private static final long serialVersionUID = -2976274966203458390L;

	private int planId;

	private int actionId;

	public ActionPlanPK() {
	}
	public int getPlanId() {
		return this.planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public int getActionId() {
		return this.actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
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
			&& (this.actionId == castOther.actionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.planId;
		hash = hash * prime + this.actionId;
		
		return hash;
	}
}