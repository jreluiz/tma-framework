package eubr.atmosphere.tma.entity.qualitymodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the Actuator database table.
 * 
 */
@Entity
@NamedQuery(name="Actuator.findAll", query="SELECT a FROM Actuator a")
public class Actuator implements Serializable {

	private static final long serialVersionUID = -4614451664252386530L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int actuatorId;

	private String address;

	private String pubKey;

	public Actuator() {
	}

	public int getActuatorId() {
		return this.actuatorId;
	}

	public void setActuatorId(int actuatorId) {
		this.actuatorId = actuatorId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPubKey() {
		return this.pubKey;
	}

	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}

}