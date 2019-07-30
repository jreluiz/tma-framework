package eubr.atmosphere.tma.qualitymodel.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the PlanAction database table.
 * 
 */
@Entity
@NamedQuery(name="PlanAction.findAll", query="SELECT p FROM PlanAction p")
public class PlanAction implements Serializable {

	private static final long serialVersionUID = 7291160731806388114L;

	@EmbeddedId
	private PlanActionPK id;

	private int executionOrder;

	private int status;

	public PlanAction() {
	}

	public PlanActionPK getId() {
		return this.id;
	}

	public void setId(PlanActionPK id) {
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