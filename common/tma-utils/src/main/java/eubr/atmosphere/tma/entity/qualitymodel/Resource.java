package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the Resource database table.
 * 
 */
@Entity
@NamedQuery(name="Resource.findAll", query="SELECT r FROM Resource r")
public class Resource implements Serializable {

	private static final long serialVersionUID = -5213882924098259466L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int resourceId;

	private String resourceAddress;

	private String resourceName;

	private String resourceType;

	//bi-directional many-to-one association to ActionRule
	@OneToMany(mappedBy="resource", fetch=FetchType.EAGER)
	private Set<ActionRule> actionRules;
	
	public Resource() {
	}

	public int getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceAddress() {
		return this.resourceAddress;
	}

	public void setResourceAddress(String resourceAddress) {
		this.resourceAddress = resourceAddress;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Set<ActionRule> getActionRules() {
		return actionRules;
	}

	public void setActionRules(Set<ActionRule> actionRules) {
		this.actionRules = actionRules;
	}

}