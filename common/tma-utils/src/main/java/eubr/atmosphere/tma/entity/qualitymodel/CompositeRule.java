package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * The persistent class for the CompositeRule database table.
 * 
 */
@Entity(name="CompositeRule")
@NamedQuery(name="CompositeRule.findAll", query="SELECT c FROM CompositeRule c")
public class CompositeRule extends Rule implements Serializable {
	
	private static final long serialVersionUID = -3295077977653605652L;
	
	// bi-directional many-to-one association to Attribute
	@OneToMany(mappedBy="compositeRule", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Rule> children;

	public CompositeRule() {
	}

	@Override
	public Rule buildRule(TrustworthinessObject dataObject, String parentRuleName) {
		this.setDataObject(dataObject);
		if (parentRuleName != null) {
			this.setName(this.getName() + "\" extends " + "\"" + parentRuleName);
		}
		
		for (Rule rule : children) {
			rule.buildRule(dataObject, this.getName());
		}
		
		return this;
	}
	
}