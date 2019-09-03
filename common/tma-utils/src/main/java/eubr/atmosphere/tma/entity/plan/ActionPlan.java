package eubr.atmosphere.tma.entity.plan;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the PlanAction database table.
 * 
 */
@Entity
@NamedQuery(name="ActionPlan.findAll", query="SELECT p FROM ActionPlan p")
public class ActionPlan implements Serializable {

	private static final long serialVersionUID = 7291160731806388114L;

	@EmbeddedId
	private ActionPlanPK id;

	private int executionOrder;

	private int status;

	public ActionPlan() {
	}

	public ActionPlanPK getId() {
		return this.id;
	}

	public void setId(ActionPlanPK id) {
		this.id = id;
	}

	public int getExecutionOrder() {
		return this.executionOrder;
	}

	public void setExecutionOrder(int executionOrder) {
		this.executionOrder = executionOrder;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}