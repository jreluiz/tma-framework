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

import eubr.atmosphere.tma.utils.ListUtils;


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
	public void buildHierarchy(String parentRuleName) {
		
		if (parentRuleName != null) {
			this.setName(this.getName() + "\" extends " + "\"" + parentRuleName);
		}
		
		if (ListUtils.isNotEmpty(children)) {
			for (Rule rule : children) {
				if ( !rule.equals(this) ) {
					rule.buildHierarchy(this.getName());
				}
			}
		}
		
	}
	
}