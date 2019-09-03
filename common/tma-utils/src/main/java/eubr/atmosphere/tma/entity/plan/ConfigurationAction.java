package eubr.atmosphere.tma.entity.plan;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the ConfigurationDataPK database table.
 * 
 */
@Entity
@NamedQuery(name="ConfigurationAction.findAll", query="SELECT c FROM ConfigurationAction c")
public class ConfigurationAction implements Serializable {

	private static final long serialVersionUID = -7685986829731868901L;

	@EmbeddedId
	private ConfigurationActionPK id;

	private String value;

	public ConfigurationAction() {
	}

	public ConfigurationActionPK getId() {
		return this.id;
	}

	public void setId(ConfigurationActionPK id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}