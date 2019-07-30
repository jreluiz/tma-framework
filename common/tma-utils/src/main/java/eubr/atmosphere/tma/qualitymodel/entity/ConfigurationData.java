package eubr.atmosphere.tma.qualitymodel.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the ConfigurationDataPK database table.
 * 
 */
@Entity
@NamedQuery(name="ConfigurationData.findAll", query="SELECT c FROM ConfigurationData c")
public class ConfigurationData implements Serializable {

	private static final long serialVersionUID = -7685986829731868901L;

	@EmbeddedId
	private ConfigurationDataPK id;

	private String value;

	public ConfigurationData() {
	}

	public ConfigurationDataPK getId() {
		return this.id;
	}

	public void setId(ConfigurationDataPK id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}