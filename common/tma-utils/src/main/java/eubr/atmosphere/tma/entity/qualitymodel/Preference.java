package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the preference database table.
 * @author JorgeLuiz
 */
@Entity(name="Preference")
@NamedQuery(name="preference.findAll", query="SELECT p FROM Preference p")
public class Preference implements Serializable {

	private static final long serialVersionUID = 4897045709573210431L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int preferenceId;

	private double threshold;

	private double weight;

	//bi-directional many-to-one association to Configurationprofile
	@ManyToOne
	@JoinColumn(name="configurationprofileId")
	private ConfigurationProfile configurationprofile;

//	//bi-directional one-to-one association to Attribute
//	@OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name="attributeId")
//	private Attribute attribute;
	
	// bi-directional many-to-one association to Attribute
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "attributeId")
	private Attribute attribute;

	public Preference() {
	}

	public Preference(int preferenceId, double threshold, double weight) {
		this.preferenceId = preferenceId;
		this.threshold = threshold;
		this.weight = weight;
	}

	public int getPreferenceId() {
		return preferenceId;
	}

	public void setPreferenceId(int preferenceId) {
		this.preferenceId = preferenceId;
	}

	public double getThreshold() {
		return this.threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public ConfigurationProfile getConfigurationprofile() {
		return this.configurationprofile;
	}

	public void setConfigurationprofile(ConfigurationProfile configurationprofile) {
		this.configurationprofile = configurationprofile;
	}

	public Attribute getAttribute() {
		return this.attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public String toString() {
		return "Preference [threshold=" + threshold + ", weight=" + weight + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((configurationprofile == null) ? 0 : configurationprofile.hashCode());
		result = prime * result + preferenceId;
		long temp;
		temp = Double.doubleToLongBits(threshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Preference other = (Preference) obj;
		if (configurationprofile == null) {
			if (other.configurationprofile != null)
				return false;
		} else if (!configurationprofile.equals(other.configurationprofile))
			return false;
		if (preferenceId != other.preferenceId)
			return false;
		if (Double.doubleToLongBits(threshold) != Double.doubleToLongBits(other.threshold))
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

}