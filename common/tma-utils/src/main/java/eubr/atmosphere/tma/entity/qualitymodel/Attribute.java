package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eubr.atmosphere.tma.exceptions.UndefinedException;
import eubr.atmosphere.tma.utils.ListUtils;
import eubr.atmosphere.tma.utils.TreeUtils;

/**
 * The persistent class for the attribute database table.
 * @author JorgeLuiz
 */
@Entity(name="Attribute")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name="attribute.findAll", query="SELECT a FROM Attribute a")
public abstract class Attribute implements Serializable {

	private static final long serialVersionUID = 4884416721621562261L;

	@Transient
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int attributeId;

	private String name;
	
	//bi-directional one-to-many association to Historicaldata
	@OneToMany (mappedBy="attribute", fetch = FetchType.EAGER)
	private List<HistoricalData> historicaldata;
	
	//bi-directional many-to-one association to Rule
	@OneToMany(mappedBy="attribute", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Preference> preferences;

	//bi-directional many-to-one association to compositeattribute
	@ManyToOne
	@JoinColumn(name="compositeattributeId")
	private CompositeAttribute compositeattribute;

	//bi-directional many-to-one association to Rule
	@OneToMany(mappedBy="attribute", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Rule> rules;
	
	@Transient
	private Plan plan;
	
	@Transient
	private RuleAttributeStatus ruleAttributeStatus;
	
	public abstract HistoricalData calculate(ConfigurationProfile user) throws UndefinedException;

	public abstract void buildAttributeRules();
	
	public void buildHierarchyRules() {
		
		TreeUtils treeUtils = TreeUtils.getInstance();

		List<CompositeRule> rootRules = treeUtils.getRootRules(getRules());
		for (CompositeRule rootRule : rootRules) {
			rootRule.buildHierarchy(null);
		}

	}
	
	public double getScore() {
		
		// sort historical data list by instant
		Comparator<HistoricalData> compareByInstant = (HistoricalData h1, HistoricalData h2) -> h1.getId()
				.getInstant().compareTo(h2.getId().getInstant());
		Collections.sort(historicaldata, compareByInstant);
		
		// get last historical data element
		HistoricalData lastHistoricalData = ListUtils.getLastElement(historicaldata);

		System.out.println("Score: " + lastHistoricalData.getValue());
		
		return lastHistoricalData.getValue();
	}
	
	public Double getPreviousScore() {
		
		// sort historical data list by instant
		Comparator<HistoricalData> compareByInstant = (HistoricalData h1, HistoricalData h2) -> h1.getId()
				.getInstant().compareTo(h2.getId().getInstant());
		Collections.sort(historicaldata, compareByInstant);
		
		// get second last historical data element
		HistoricalData secondLastHistoricalData = null;
		try { 
			secondLastHistoricalData = historicaldata.get(ListUtils.size(historicaldata) - 2);
		} catch (IndexOutOfBoundsException e) {
			LOGGER.info("Historical data has not second last element.");
		}

		if (secondLastHistoricalData != null) {
			
			System.out.println("Previous score: " + secondLastHistoricalData.getValue());
			
			return secondLastHistoricalData.getValue();
		}
		
		System.out.println("Previous score: null");
		
		return null;
	}
	
	public Double getThreshold() {
		System.out.println("Threshold: " + getActivePreference().getThreshold());
		return getActivePreference().getThreshold();
	}
	
	public Attribute() {
	}
	
	public int getAttributeId() {
		return this.attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CompositeAttribute getCompositeattribute() {
		return this.compositeattribute;
	}

	public void setCompositeattribute(CompositeAttribute compositeattribute) {
		this.compositeattribute = compositeattribute;
	}

	public List<HistoricalData> getHistoricaldata() {
		return historicaldata;
	}

	public void setHistoricaldata(List<HistoricalData> historicaldata) {
		this.historicaldata = historicaldata;
	}

	public List<Preference> getPreferences() {
		return preferences;
	}

	public void setPreferences(List<Preference> preferences) {
		this.preferences = preferences;
	}

	public Preference getActivePreference() {
		if (ListUtils.isNotEmpty(preferences)) {
			for (Preference pref : preferences) {
				if (pref.getConfigurationprofile().isActive()) {
					return pref;
				}
			}
		}
		
		return null;
	}
	
	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public RuleAttributeStatus getRuleAttributeStatus() {
		return ruleAttributeStatus;
	}

	public void setRuleAttributeStatus(RuleAttributeStatus ruleAttributeStatus) {
		this.ruleAttributeStatus = ruleAttributeStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + attributeId;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((getActivePreference() == null) ? 0 : getActivePreference().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (attributeId != other.attributeId)
			return false;
		if (compositeattribute == null) {
			if (other.compositeattribute != null)
				return false;
		} else if (!compositeattribute.equals(other.compositeattribute))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (getActivePreference() == null) {
			if (other.getActivePreference() != null)
				return false;
		} else if (!getActivePreference().equals(other.getActivePreference()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Attribute [attributeId=" + attributeId + ", name=" + name + ", historicaldata=" + historicaldata
				+ ", preference=" + getActivePreference() + ", compositeattribute=" + compositeattribute + "]";
	}

}