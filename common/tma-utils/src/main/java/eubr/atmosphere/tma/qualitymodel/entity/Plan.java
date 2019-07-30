package eubr.atmosphere.tma.qualitymodel.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the Plan database table.
 * 
 */
@Entity
@NamedQuery(name="Plan.findAll", query="SELECT p FROM Plan p")
public class Plan implements Serializable {

	private static final long serialVersionUID = 2305463323959347330L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int planId;

	private int configurationProfileId;

	private double scoreMax;

	private int status;

	private Timestamp valueTime;

	public Plan() {
	}

	public int getPlanId() {
		return this.planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getConfigurationProfileId() {
		return this.configurationProfileId;
	}

	public void setConfigurationProfileId(int configurationProfileId) {
		this.configurationProfileId = configurationProfileId;
	}

	public double getScoreMax() {
		return this.scoreMax;
	}

	public void setScoreMax(double scoreMax) {
		this.scoreMax = scoreMax;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getValueTime() {
		return this.valueTime;
	}

	public void setValueTime(Timestamp valueTime) {
		this.valueTime = valueTime;
	}

}