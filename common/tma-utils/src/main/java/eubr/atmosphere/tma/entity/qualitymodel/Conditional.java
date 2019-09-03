package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the Conditional database table.
 * 
 */
@Entity
@Table(name="Conditional")
@NamedQuery(name="Conditional.findAll", query="SELECT c FROM Conditional c")
public class Conditional implements Serializable {

	private static final long serialVersionUID = -43323380262048874L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int conditionId;

	@Enumerated(EnumType.ORDINAL)
	private ConditionalOperator operator = ConditionalOperator.EQUAL_TO;

	@Column
	private String property;

	//bi-directional many-to-one association to Rule
	@ManyToOne
	@JoinColumn(name="ruleId")
	private Rule rule;

	@Column
	private String value;

	public Conditional() {
	}

	public int getConditionId() {
		return this.conditionId;
	}

	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}

	public ConditionalOperator getOperator() {
		return operator;
	}

	public void setOperator(ConditionalOperator operator) {
		this.operator = operator;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
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

}
