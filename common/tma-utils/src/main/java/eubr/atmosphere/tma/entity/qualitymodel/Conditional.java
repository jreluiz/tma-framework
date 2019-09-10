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

	public Conditional(String property, ConditionalOperator operator, String value) {
		super();
		this.operator = operator;
		this.property = property;
		this.value = value;
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

		if (operator.isComparable(String.class)) {
			if (operator.equals(ConditionalOperator.CONTAINS)) {
				drl.append(property).append(".toUpperCase().contains(\"").append(((String) value).toUpperCase())
						.append("\")");
			} else {
				drl.append(property).append(" ").append(operator.getOperation()).append(" ").append("\"").append(value)
						.append("\"");
			}
		} else {
			throw new IllegalArgumentException("Is not possible to use the operator " + operator.getDescription()
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

		if ((operator.isComparable(Short.class)) || (operator.isComparable(Integer.class))
				|| (operator.isComparable(Long.class)) || (operator.isComparable(Double.class))
				|| (operator.isComparable(Float.class)) || (operator.isComparable(String.class)) ) {
			drl.append(property).append(" ").append(operator.getOperation()).append(" ").append(value);
		} else {
			throw new IllegalArgumentException("Is not possible to use the operator " + operator.getDescription()
					+ " to a " + value.getClass().getSimpleName() + " object.");
		}

		return drl.toString();
	}

	@Override
	public String toString() {
		return "Conditional [conditionId=" + conditionId + ", operator=" + operator + ", property=" + property
				+ ", value=" + value + "]";
	}
	
}
