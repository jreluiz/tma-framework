package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * The persistent class for the Rule database table.
 * 
 */
@Entity
@Table(name="Rule")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name="Rule.findAll", query="SELECT r FROM Rule r")
public class Rule implements Serializable {
	
	private static final long serialVersionUID = 6163237338626808371L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int ruleId;

	//bi-directional many-to-one association to compositeattribute
	@ManyToOne
	@JoinColumn(name="compositeRuleId")
	private CompositeRule compositeRule;
	
	//bi-directional many-to-one association to CompositeAttribute
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="compositeattributeId")
	private CompositeAttribute compositeattribute;
	
	@Column
	private String name;
	
	//bi-directional many-to-one association to Conditional
	@OneToMany(mappedBy="rule", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Conditional> conditions;
	
	//bi-directional many-to-one association to Conditional
	@OneToMany(mappedBy="rule", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<ActionRule> actions;
	
	@Column
	protected RuleType ruleType;
	
	public void buildRule(String dataObject, String parentRuleName) {}
	
	@Transient
	private String object;
	
	public Rule() {
	}
	
	public int getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	
	public CompositeRule getCompositeRule() {
		return compositeRule;
	}

	public void setCompositeRule(CompositeRule compositeRule) {
		this.compositeRule = compositeRule;
	}

	public CompositeAttribute getCompositeattribute() {
		return compositeattribute;
	}

	public void setCompositeattribute(CompositeAttribute compositeattribute) {
		this.compositeattribute = compositeattribute;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Rule [ruleId=" + ruleId + ", name=" + name + "]";
	}

	/**
	 * Converts these conditionals to Drools Rule Language (DRL) format.<br>
	 * The formatted conditional is in dialect Java (<i>dialect "java"</i>).
	 * 
	 * @return Rule's conditional expression.
	 * @throws IllegalStateException    Indicates none conditional declared.
	 * @throws IllegalArgumentException Indicates the use of invalid pair of value
	 *                                  and condition.
	 */
	public String conditionAsDRL() throws IllegalStateException, IllegalArgumentException {
		if ((conditions == null) || (conditions.isEmpty())) {
			throw new IllegalStateException("You must declare at least one condition to be evaluated.");
		}

		StringBuilder drl = new StringBuilder();
		// For each condition of this rule, we create its textual representation
		for (int i = 0; i < conditions.size(); i++) {
			Conditional condition = conditions.stream().findFirst().get();
			drl.append("(");
			drl.append(condition.buildExpression());
			drl.append(")");
			if ((i + 1) < conditions.size()) {
				drl.append(" && ");
			}
		}

		return drl.toString();
	}
	
	/**
	 * Returns the created rule as a map of its properties to be compiled with
	 * template.
	 * 
	 * @return Map of rule's properties.
	 * @throws IllegalStateException Indicate a non valid rule.
	 */
	public Map<String, Object> asMap() throws IllegalStateException {
		if ((getName() == null) || (getDataObject() == null)) {
			throw new IllegalArgumentException(
					"The rule has no name or object to be evaluated to be accomplished.");
		}

		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put(Rule.Attribute.RULE_NAME.toString(), name);
		attributes.put(Rule.Attribute.DATA_OBJECT.toString(), object);
		attributes.put(Rule.Attribute.CONDITIONAL.toString(), conditionAsDRL());
		attributes.put(Rule.Attribute.ACTIONS.toString(), getActionsIds());

		return attributes;
	}
	
	private String getActionsIds() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\"");
		
		Iterator<ActionRule> it = actions.iterator();
		while(it.hasNext()) {
			ActionRule actionRule = it.next();
			sb.append(actionRule.getActionRuleId());
			if (it.hasNext()) {
				sb.append(";");
			}
		}
		sb.append("\"");
		
		return sb.toString();
	}

	/**
	 * Create new condition and add it to this rule.
	 * 
	 * @param property Object property to be evaluated.
	 * @param operator Operator used to compare the data.
	 * @param value    Value to be evaluated.
	 * @return Condition created.
	 */
	public Conditional addCondition(String property, ConditionalOperator operator, String value) {
		Conditional condition = new Conditional(property, operator, value);
		conditions.add(condition);

		return condition;
	}

	/**
	 * List of attributes available to use in template.<br>
	 * These names must be the same used to write the .drl file template, which is
	 * compiled in runtime.
	 */
	public enum Attribute {
		/**
		 * Name of the rule.
		 */
		RULE_NAME("name"),
		/**
		 * Object with data to be processed.
		 */
		DATA_OBJECT("object"),
		/**
		 * Conditional expression.
		 */
		CONDITIONAL("conditional"),
		/**
		 * Action to take.
		 */
		ACTIONS("actions");

		/**
		 * Name used in template to assign each attirbute.
		 */
		private final String name;

		private Attribute(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}

	public String getDataObject() {
		return object;
	}

	public void setDataObject(String object) {
		this.object = object;
	}

	public Set<Conditional> getConditions() {
		return conditions;
	}

	public Set<ActionRule> getActions() {
		return actions;
	}
	
}
