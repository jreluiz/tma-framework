package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the LeafRule database table.
 * 
 */
@Entity(name="LeafRule")
@NamedQuery(name="LeafRule.findAll", query="SELECT l FROM LeafRule l")
public class LeafRule extends Rule implements Serializable {

	private static final long serialVersionUID = -2695226377242174273L;

	public LeafRule() {
		super();
	}

	@Override
	public void buildRule(String dataObject, String parentRuleName) {
		this.setDataObject(dataObject);
		this.setName(this.getName() + "\" extends " + "\"" + parentRuleName);
	}

}