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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * The persistent class for the Rule database table.
 * 
 */
@Entity
@Table(name = "Rule")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "Rule.findAll", query = "SELECT r FROM Rule r")
public class Rule implements Serializable {

	private static final long serialVersionUID = 6163237338626808371L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int ruleId;

	// bi-directional many-to-one association to CompositeRule
	@ManyToOne
	@JoinColumn(name = "compositeRuleId")
	private CompositeRule compositeRule;

	// bi-directional many-to-one association to Attribute
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "attributeId")
	private Attribute attribute;

	@Column
	private String name;

	// bi-directional many-to-one association to Conditional
	@OneToMany(mappedBy = "rule", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Conditional> conditions;

	// bi-directional many-to-one association to ActionRule
	@OneToMany(mappedBy = "rule", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<ActionRule> actions;

	@Column
	private boolean enabled;

	@Column
	private Integer priority;

	@Column
	private String activationGroup;
	
	public void buildHierarchy(String parentRuleName) {
	}

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

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
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
		if ((getName() == null) || (getAttribute() == null)) {
			throw new IllegalArgumentException("The rule has no name or attribute to be evaluated to be accomplished.");
		}

		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put(Rule.AttributeRule.RULE_NAME.toString(), name);
		attributes.put(Rule.AttributeRule.DATA_ATTRIBUTE.toString(), attribute.getClass().getName());
		attributes.put(Rule.AttributeRule.CONDITIONAL.toString(), conditionAsDRL());
		attributes.put(Rule.AttributeRule.ACTIONS.toString(), getPlanActionsIds());
		attributes.put(Rule.AttributeRule.PRIORITY.toString(), priority);
		attributes.put(Rule.AttributeRule.ENABLED.toString(), enabled);
		attributes.put(Rule.AttributeRule.ACTIVATION_GROUP.toString(), "\""+activationGroup+"\"");

		return attributes;
	}

	/**
	 * Cria string contendo o numero do plano criado mais as acoes a serem adicionadas no plano.
	 * 
	 * Formato: 1:3,5,6
	 * Plano com id=1 com as acoes 3, 5 e 6
	 * 
	 * @return string no formato [plano]:[acoes separadas por virgua]
	 */
	private String getPlanActionsIds() {
		StringBuilder sb = new StringBuilder();

		sb.append("\"");
		sb.append(attribute.getPlan().getPlanId());
		sb.append(":");

		Iterator<ActionRule> it = actions.iterator();
		while (it.hasNext()) {
			ActionRule actionRule = it.next();
			sb.append(actionRule.getActionRuleId());
			if (it.hasNext()) {
				sb.append(",");
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
		Conditional condition = new Conditional(operator, value);
		conditions.add(condition);

		return condition;
	}

	/**
	 * List of attributes available to use in template.<br>
	 * These names must be the same used to write the .drl file template, which is
	 * compiled in runtime.
	 */
	public enum AttributeRule {
		/**
		 * Name of the rule.
		 */
		RULE_NAME("name"),
		/**
		 * Attribute with data to be processed.
		 */
		DATA_ATTRIBUTE("attributeObject"),
		/**
		 * Conditional expression.
		 */
		CONDITIONAL("conditional"),
		/**
		 * Action to take.
		 */
		ACTIONS("actions"),
		/**
		 * Execution priority.
		 */
		PRIORITY("priority"),
		/**
		 * Rule active
		 */
		ENABLED("active"),
		/**
		 * Activation group
		 */
		ACTIVATION_GROUP("activationGroup");

		/**
		 * Name used in template to assign each attribute.
		 */
		private final String name;

		private AttributeRule(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public String getActivationGroup() {
		return activationGroup;
	}

	public void setActivationGroup(String activationGroup) {
		this.activationGroup = activationGroup;
	}

	public Set<Conditional> getConditions() {
		return conditions;
	}

	public Set<ActionRule> getActions() {
		return actions;
	}

}
