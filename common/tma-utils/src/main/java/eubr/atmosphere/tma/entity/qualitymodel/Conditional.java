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

	//bi-directional many-to-one association to Rule
	@ManyToOne
	@JoinColumn(name="ruleId")
	private Rule rule;

	@Enumerated(EnumType.ORDINAL)
	private ConditionalOperator conditionalOperator = ConditionalOperator.EQUAL_TO;
	
	@Column
	private String value;

	public Conditional() {
	}

	public Conditional(ConditionalOperator operator, String value) {
		super();
		this.conditionalOperator = operator;
		this.value = value;
	}

	public int getConditionId() {
		return this.conditionId;
	}

	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}

	public ConditionalOperator getConditionalOperator() {
		return conditionalOperator;
	}

	public void setConditionalOperator(ConditionalOperator conditionalOperator) {
		this.conditionalOperator = conditionalOperator;
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

	/**
	 * Convert the condition to textual expression.
	 * 
	 * @return The expression of this condition in dialect.
	 * @throws IllegalArgumentException Indicates the use of invalid pair of value
	 *                                  and condition.
	 */
	public String buildExpression() throws IllegalArgumentException {
		StringBuilder drl = new StringBuilder();

		if (value instanceof String) {
			drl.append(expressionForNumberValue());
		} else {
			throw new IllegalArgumentException(
					"The class " + value.getClass().getSimpleName() + " of value is not acceptable.");
		}

		return drl.toString();
	}

	/**
	 * Convert the condition for <b>String</b> value in expression.
	 * 
	 * @return Expression in dialect.
	 * @throws IllegalArgumentException Indicates the use of invalid pair of value
	 *                                  and condition.
	 */
	private String expressionForStringValue() throws IllegalArgumentException {
		StringBuilder drl = new StringBuilder();

		if (conditionalOperator.isComparable(String.class)) {
			if (conditionalOperator.equals(ConditionalOperator.CONTAINS)) {
				drl.append("score").append(".toUpperCase().contains(\"").append(((String) value).toUpperCase())
						.append("\")");
			} else {
				drl.append("score").append(" ").append(conditionalOperator.getOperation()).append(" ").append("\"").append(value)
						.append("\"");
			}
		} else {
			throw new IllegalArgumentException("Is not possible to use the operator " + conditionalOperator.getDescription()
					+ " to a " + value.getClass().getSimpleName() + " object.");
		}

		return drl.toString();
	}
	
	/**
	 * Convert the condition for <b>Integer</b>, <b>Double</b>, <b>Float</b> or
	 * <b>String</b> value in expression.
	 * 
	 * @return Expression in dialect.
	 * @throws IllegalArgumentException Indicates the use of invalid pair of value
	 *                                  and condition.
	 */
	private String expressionForNumberValue() throws IllegalArgumentException {
		StringBuilder drl = new StringBuilder();

		if ((conditionalOperator.isComparable(Short.class)) || (conditionalOperator.isComparable(Integer.class))
				|| (conditionalOperator.isComparable(Long.class)) || (conditionalOperator.isComparable(Double.class))
				|| (conditionalOperator.isComparable(Float.class)) || (conditionalOperator.isComparable(String.class)) ) {
			drl.append("score").append(" ").append(conditionalOperator.getOperation()).append(" ").append(value);
		} else {
			throw new IllegalArgumentException("Is not possible to use the operator " + conditionalOperator.getDescription()
					+ " to a " + value.getClass().getSimpleName() + " object.");
		}

		return drl.toString();
	}

	@Override
	public String toString() {
		return "Conditional [conditionId=" + conditionId + ", operator=" + conditionalOperator + ", property=" + "score"
				+ ", value=" + value + "]";
	}
	
}
