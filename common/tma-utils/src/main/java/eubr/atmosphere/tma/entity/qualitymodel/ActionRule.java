package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the ActionRule database table.
 * 
 */
@Entity
@Table(name="ActionRule")
@NamedQuery(name="ActionRule.findAll", query="SELECT a FROM ActionRule a")
public class ActionRule implements Serializable {

	private static final long serialVersionUID = 4059767502190736132L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int actionRuleId;

	@Column
	private String actionName;

	//bi-directional many-to-one association to Actuator
	@ManyToOne
	@JoinColumn(name="actuatorId")
	private Actuator actuator;

	//bi-directional many-to-one association to Resource
	@ManyToOne
	@JoinColumn(name="resourceId")
	private Resource resource;

	//bi-directional many-to-one association to Rule
	@ManyToOne
	@JoinColumn(name="ruleId")
	private Rule rule;
	
	//bi-directional many-to-one association to ActionPlan
	@OneToMany(mappedBy="actionRule", fetch=FetchType.EAGER)
	private Set<ActionPlan> actionPlans;

	//bi-directional many-to-one association to Configuration
	@OneToMany(mappedBy="actionRule", fetch=FetchType.EAGER)
	private Set<Configuration> configurations;
	
	public ActionRule() {
	}
	
	public ActionRule(int actionRuleId, String actionName, Actuator actuator, Resource resource) {
		super();
		this.actionRuleId = actionRuleId;
		this.actionName = actionName;
		this.actuator = actuator;
		this.resource = resource;
	}



	public int getActionRuleId() {
		return this.actionRuleId;
	}

	public void setActionRuleId(int actionRuleId) {
		this.actionRuleId = actionRuleId;
	}

	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Actuator getActuator() {
		return actuator;
	}

	public void setActuator(Actuator actuator) {
		this.actuator = actuator;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Set<ActionPlan> getActionPlans() {
		return actionPlans;
	}

	public void setActionPlans(Set<ActionPlan> actionPlans) {
		this.actionPlans = actionPlans;
	}

	public Set<Configuration> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(Set<Configuration> configurations) {
		this.configurations = configurations;
	}
	
	public void addConfiguration(Configuration configuration) {
		if (this.configurations == null) {
			this.configurations = new HashSet<>();
		}
		this.configurations.add(configuration);
	}

	@Override
	public String toString() {
		return "ActionRule [actionRuleId=" + actionRuleId + ", actionName=" + actionName + "]";
	}

}