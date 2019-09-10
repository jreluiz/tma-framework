package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
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

	@Column(nullable=false)
	private int actuatorId;

	@Column
	private String domain;

	@Column
	private String keyName;

	@Column(nullable=false)
	private int resourceId;

	//bi-directional many-to-one association to Rule
	@ManyToOne
	@JoinColumn(name="ruleId")
	private Rule rule;

	@Column
	private String value;

	public ActionRule() {
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

	public int getActuatorId() {
		return this.actuatorId;
	}

	public void setActuatorId(int actuatorId) {
		this.actuatorId = actuatorId;
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

	public int getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ActionRule [actionRuleId=" + actionRuleId + ", actionName=" + actionName + ", domain=" + domain
				+ ", keyName=" + keyName + ", value=" + value + "]";
	}

}