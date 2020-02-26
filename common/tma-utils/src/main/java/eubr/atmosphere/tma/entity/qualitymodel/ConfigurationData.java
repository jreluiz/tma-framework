package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the ConfigurationData database table.
 * 
 */
@Entity
@NamedQuery(name="ConfigurationData.findAll", query="SELECT c FROM ConfigurationData c")
public class ConfigurationData implements Serializable {

	private static final long serialVersionUID = -8812093520861923441L;

	@EmbeddedId
	private ConfigurationDataPK id;

	private String value;

	//bi-directional many-to-one association to ActionPlan
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="actionRuleId", referencedColumnName="actionRuleId", insertable = false, updatable = false),
		@JoinColumn(name="planId", referencedColumnName="planId", insertable = false, updatable = false)
		})
	private ActionPlan actionPlan;

	//bi-directional many-to-one association to Configuration
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="actionRuleId", referencedColumnName="configurationId", insertable = false, updatable = false),
		@JoinColumn(name="configurationId", referencedColumnName="actionRuleId", insertable = false, updatable = false)
		})
	private Configuration configuration;

	public ConfigurationData() {
	}

	public ConfigurationData(int planId, int actionRuleId, int configurationId, String value) {
		ConfigurationDataPK id = new ConfigurationDataPK();
		id.setPlanId(planId);
		id.setActionRuleId(actionRuleId);
		id.setConfigurationId(configurationId);
		this.id = id;
		this.value = value;
	}
	
	public ConfigurationDataPK getId() {
		return this.id;
	}

	public void setId(ConfigurationDataPK id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ActionPlan getActionPlan() {
		return this.actionPlan;
	}

	public void setActionPlan(ActionPlan actionPlan) {
		this.actionPlan = actionPlan;
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public String toString() {
		return "ConfigurationData [id=" + id + ", value=" + value + ", actionPlan=" + actionPlan + ", configuration="
				+ configuration + "]";
	}

}