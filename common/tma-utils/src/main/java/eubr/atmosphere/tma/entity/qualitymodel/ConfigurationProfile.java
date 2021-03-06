package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * The persistent class for the configurationprofile database table.
 * @author JorgeLuiz
 */
@Entity(name="ConfigurationProfile")
@NamedQuery(name="configurationprofile.findAll", query="SELECT c FROM ConfigurationProfile c")
public class ConfigurationProfile implements Serializable {

	private static final long serialVersionUID = 726009967174063732L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int configurationprofileId;

	boolean active = false;

	//bi-directional many-to-one association to Metric
	@OneToMany(mappedBy="configurationprofile", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Metric> metrics;

	//bi-directional many-to-one association to Preference
	@OneToMany(mappedBy="configurationprofile", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Preference> preferences;

	public ConfigurationProfile() {
	}

	public int getConfigurationprofileId() {
		return this.configurationprofileId;
	}

	public void setConfigurationprofileId(int configurationprofileId) {
		this.configurationprofileId = configurationprofileId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Metric> getMetrics() {
		if (metrics == null) {
			metrics = new HashSet<Metric>();
		}
		return metrics;
	}

	public void setMetrics(Set<Metric> metrics) {
		this.metrics = metrics;
	}

	public Metric addMetric(Metric metric) {
		getMetrics().add(metric);
		metric.setConfigurationprofile(this);

		return metric;
	}

	public Metric removeMetric(Metric metric) {
		getMetrics().remove(metric);
		metric.setConfigurationprofile(null);

		return metric;
	}

	public Set<Preference> getPreferences() {
		if (preferences == null) {
			preferences = new HashSet<Preference>();
		}
		return preferences;
	}

	public Preference getPreference(Attribute child) {
		for (Preference p : preferences) {
			if (child.equals(p.getAttribute())) {
				return p;
			}
		}
		return null;
	}

	public void setPreferences(Set<Preference> preferences) {
		this.preferences = preferences;
	}

	public Preference addPreference(Preference preference) {
		getPreferences().add(preference);
		preference.setConfigurationprofile(this);

		return preference;
	}

	public Preference removePreference(Preference preference) {
		getPreferences().remove(preference);
		preference.setConfigurationprofile(null);

		return preference;
	}

	@Override
	public String toString() {
		return "ConfigurationProfile [metrics=" + metrics + ", preferences=" + preferences + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + configurationprofileId;
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
		ConfigurationProfile other = (ConfigurationProfile) obj;
		if (active != other.active)
			return false;
		if (configurationprofileId != other.configurationprofileId)
			return false;
		return true;
	}
	
}