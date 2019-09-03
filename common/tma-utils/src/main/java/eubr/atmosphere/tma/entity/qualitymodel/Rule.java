package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
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
@NamedQuery(name="Rule.findAll", query="SELECT r FROM Rule r")
public class Rule implements Serializable {
	
	private static final long serialVersionUID = 6163237338626808371L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int ruleId;

	//bi-directional many-to-one association to CompositeAttribute
	@ManyToOne
	@JoinColumn(name="compositeattributeId")
	private CompositeAttribute compositeattribute;

	@Column
	private String name;
	
	//bi-directional many-to-one association to Conditional
	@OneToMany(mappedBy="rule", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Conditional> conditions;
	
	//bi-directional many-to-one association to Conditional
	@OneToMany(mappedBy="rule", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<ActionRule> actions;
	
	public Rule() {
	}

	public int getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
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
		return "Rule [ruleId=" + ruleId + ", attributeId=" + compositeattribute + ", name=" + name + ", conditions="
				+ conditions + ", actions=" + actions + "]";
	}

}
