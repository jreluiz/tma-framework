package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import eubr.atmosphere.tma.utils.ListUtils;


/**
 * The persistent class for the ActionPlan database table.
 * 
 */
@Entity
@NamedQuery(name="ActionPlan.findAll", query="SELECT a FROM ActionPlan a")
public class ActionPlan implements Serializable {

	private static final long serialVersionUID = 6413658271117136346L;

	@EmbeddedId
	private ActionPlanPK id;

	private int executionOrder;

	private int status;

	//bi-directional many-to-one association to Plan
	@ManyToOne
	@JoinColumn(name="planId", insertable = false, updatable = false)
	private Plan plan;

	//bi-directional many-to-one association to ActionRule
	@ManyToOne
	@JoinColumn(name="actionRuleId", insertable = false, updatable = false)
	private ActionRule actionRule;

	//bi-directional many-to-one association to ConfigurationData
	@OneToMany(mappedBy="actionPlan", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ConfigurationData> configurationData;

	public ActionPlan() {
	}

	public ActionPlan(int planId, int actionRuleId, int executionOrder, int status) {
		super();
		ActionPlanPK id = new ActionPlanPK();
		id.setPlanId(planId);
		id.setActionRuleId(actionRuleId);
		this.id = id;
		this.executionOrder = executionOrder;
		this.status = status;
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

	public Plan getPlan() {
		return this.plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public ActionRule getActionRule() {
		return this.actionRule;
	}

	public void setActionRule(ActionRule actionRule) {
		this.actionRule = actionRule;
	}

	public Set<ConfigurationData> getConfigurationData() {
		if ( ListUtils.isEmpty(this.configurationData) ) {
			this.configurationData = new HashSet<ConfigurationData>();
		}
		return this.configurationData;
	}

	public void setConfigurationData(Set<ConfigurationData> configurationData) {
		this.configurationData = configurationData;
	}

	public ConfigurationData addConfigurationData(ConfigurationData configurationData) {
		getConfigurationData().add(configurationData);
		configurationData.setActionPlan(this);

		return configurationData;
	}

	public ConfigurationData removeConfigurationData(ConfigurationData configurationData) {
		getConfigurationData().remove(configurationData);
		configurationData.setActionPlan(null);

		return configurationData;
	}

}