package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the Plan database table.
 * 
 */
@Entity
@NamedQuery(name="Plan.findAll", query="SELECT p FROM Plan p")
public class Plan implements Serializable {

	private static final long serialVersionUID = 2782431160933417137L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int planId;

	private int status;

	private Timestamp valueTime;

	//bi-directional many-to-one association to ActionPlan
	@OneToMany(mappedBy="plan", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ActionPlan> actionPlans;

	public Plan() {
	}

	public Plan(int planId, int status, Timestamp valueTime) {
		super();
		this.planId = planId;
		this.status = status;
		this.valueTime = valueTime;
	}

	public int getPlanId() {
		return this.planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getValueTime() {
		return this.valueTime;
	}

	public void setValueTime(Timestamp valueTime) {
		this.valueTime = valueTime;
	}

	public Set<ActionPlan> getActionPlans() {
		return this.actionPlans;
	}

	public void setActionPlans(Set<ActionPlan> actionPlans) {
		this.actionPlans = actionPlans;
	}

	public ActionPlan addActionPlan(ActionPlan actionPlan) {
		getActionPlans().add(actionPlan);
		actionPlan.setPlan(this);

		return actionPlan;
	}

	public ActionPlan removeActionPlan(ActionPlan actionPlan) {
		getActionPlans().remove(actionPlan);
		actionPlan.setPlan(null);

		return actionPlan;
	}

}