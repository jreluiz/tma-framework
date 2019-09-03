package eubr.atmosphere.tma.entity.plan;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import eubr.atmosphere.tma.entity.qualitymodel.Actuator;


/**
 * The persistent class for the Action database table.
 * 
 */
@Entity
@NamedQuery(name="Action.findAll", query="SELECT a FROM Action a")
public class Action implements Serializable {

	private static final long serialVersionUID = -8353285786920788772L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int actionId;

	private String actionName;

	//bi-directional one-to-one association to Actuator
	@OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="actuatorId")
	private Actuator actuatorId;
	
	public Action() {
	}

	public int getActionId() {
		return this.actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Actuator getActuatorId() {
		return actuatorId;
	}

	public void setActuatorId(Actuator actuatorId) {
		this.actuatorId = actuatorId;
	}

}