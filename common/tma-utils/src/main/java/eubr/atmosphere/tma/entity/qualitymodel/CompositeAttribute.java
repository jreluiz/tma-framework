package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import eubr.atmosphere.tma.database.QualityModelManager;
import eubr.atmosphere.tma.exceptions.UndefinedException;
import eubr.atmosphere.tma.exceptions.UndefinedMetricException;
import eubr.atmosphere.tma.exceptions.UndefinedPreferenceException;
import eubr.atmosphere.tma.utils.ListUtils;

/**
 * The persistent class for the CompositeAttribute database table.
 * @author JorgeLuiz
 */
@Entity(name="CompositeAttribute")
@NamedQuery(name="compositeattribute.findAll", query="SELECT c FROM CompositeAttribute c")
public class CompositeAttribute extends Attribute implements Serializable {

	private static final long serialVersionUID = -833533561010795503L;
	
	@Enumerated(EnumType.ORDINAL)
	private AttributeAggregationOperator operator = AttributeAggregationOperator.NEUTRALITY;

	// bi-directional many-to-one association to Attribute
	@OneToMany(mappedBy="compositeattribute", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Attribute> children;
	
	public CompositeAttribute() {
	}

	@Override
	public void buildAttributeRules() {

		this.buildHierarchyRules();
		
		//build children rules
		if ( ListUtils.isNotEmpty(children) ) {
			for (Attribute child : children) {
				if ( !child.equals(this) ) {
					child.buildAttributeRules();
				}
			}
		}
		
	}
	
	public HistoricalData calculate(ConfigurationProfile profile) throws UndefinedException {
		
		if (profile == null || ListUtils.isEmpty(profile.getPreferences())) {
			throw new UndefinedPreferenceException("No defined preference for composite attribute " + this.getName());
		}

		HistoricalData d = new HistoricalData();
		d.setInstant(new Timestamp(System.currentTimeMillis()));
		d.setAttribute(profile.getPreference(this).getAttribute());

		switch (operator) {
		case NEUTRALITY:
			d.setValue(calculateNeutrality(profile));
			break;
		case REPLACEABILITY:
			d.setValue(calculateReplaceability(profile));
			break;
		case SIMULTANEITY:
			d.setValue(calculateSimultaneity(profile));
			break;
		default:
			throw new UnsupportedOperationException();
		}

		// Stores calculated score in HistoricalDate
		QualityModelManager qmm = new QualityModelManager();
		qmm.saveHistoricalData(d);
		
		return d;
	}

	protected double calculateNeutrality(ConfigurationProfile profile) throws UndefinedException {
		double score = 0.0;
		if (ListUtils.isNotEmpty(children)) {
			for (Attribute child : children) {
				if (!child.equals(this)) {
					Preference childPref = profile.getPreference(child);
					try {
						score += child.calculate(profile).getValue() * childPref.getWeight();
					} catch (UndefinedMetricException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return score;
	}

	protected double calculateSimultaneity(ConfigurationProfile profile) throws UndefinedException {
		double score = 0.0;
		if (ListUtils.isNotEmpty(this.children)) {
			for (Attribute child : children) {
				if (!child.equals(this)) {
					Preference childPref = profile.getPreference(child);
					try {
						double scoreAux = child.calculate(profile).getValue() * childPref.getWeight();
						if (scoreAux < childPref.getThreshold()) {
							score = 0.0;
							break;
						}
						score += scoreAux;
					} catch (UndefinedMetricException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return score;
	}

	protected double calculateReplaceability(ConfigurationProfile profile) throws UndefinedException {
		double score = 0.0;
		if (ListUtils.isNotEmpty(this.children)) {
			for (Attribute child : children) {
				if (!child.equals(this)) {
					Preference childPref = profile.getPreference(child);
					try {
						double scoreAux = child.calculate(profile).getValue() * childPref.getWeight();
						if (scoreAux > childPref.getThreshold()) {
							score += scoreAux;
						}
					} catch (UndefinedMetricException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return score;
	}

	public AttributeAggregationOperator getOperator() {
		return operator;
	}

	public void setOperator(AttributeAggregationOperator operator) {
		this.operator = operator;
	}

	public List<Attribute> getChildren() {
		if (children == null) {
			children = new ArrayList<Attribute>();
		}
		return children;
	}
	
	public void setChildren(List<Attribute> children) {
		this.children = children;
	}

	public Attribute addAttribute(Attribute attribute) {
		getChildren().add(attribute);
		attribute.setCompositeattribute(this);

		return attribute;
	}

	public Attribute removeAttribute(Attribute attribute) {
		getChildren().remove(attribute);
		attribute.setCompositeattribute(null);

		return attribute;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeAttribute other = (CompositeAttribute) obj;
		if (operator != other.operator)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CompositeAttribute [operator=" + operator + "]";
	}
	
}