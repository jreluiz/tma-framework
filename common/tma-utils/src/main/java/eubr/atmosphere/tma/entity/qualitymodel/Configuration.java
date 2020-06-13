package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the Configuration database table.
 * 
 */
@Entity
@NamedQuery(name="Configuration.findAll", query="SELECT c FROM Configuration c")
public class Configuration implements Serializable {

	private static final long serialVersionUID = 7385717837153139765L;

	@EmbeddedId
	private ConfigurationPK id;

	private String domain;

	private String keyName;
	
	private String value;

	//bi-directional many-to-one association to ActionRule
	@ManyToOne
	@JoinColumn(name="actionRuleId", insertable = false, updatable = false)
	private ActionRule actionRule;

	//bi-directional many-to-one association to ConfigurationData
	@OneToMany(mappedBy="configuration", fetch=FetchType.EAGER)
	private Set<ConfigurationData> configurationData;

	public Configuration() {
	}

	public Configuration(ConfigurationPK id, String domain, String keyName, String value) {
		super();
		this.id = id;
		this.domain = domain;
		this.keyName = keyName;
		this.value = value;
	}

	public ConfigurationPK getId() {
		return this.id;
	}

	public void setId(ConfigurationPK id) {
		this.id = id;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ActionRule getActionRule() {
		return this.actionRule;
	}

	public void setActionRule(ActionRule actionRule) {
		this.actionRule = actionRule;
	}

	public Set<ConfigurationData> getConfigurationData() {
		return this.configurationData;
	}

	public void setConfigurationData(Set<ConfigurationData> configurationData) {
		this.configurationData = configurationData;
	}

	public ConfigurationData addConfigurationData(ConfigurationData configurationData) {
		getConfigurationData().add(configurationData);
		configurationData.setConfiguration(this);

		return configurationData;
	}

	public ConfigurationData removeConfigurationData(ConfigurationData configurationData) {
		getConfigurationData().remove(configurationData);
		configurationData.setConfiguration(null);

		return configurationData;
	}

}