package eubr.atmosphere.tma.entity.plan;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import eubr.atmosphere.tma.entity.qualitymodel.Attribute;


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

	//bi-directional many-to-one association to Attribute
	@ManyToOne
	@JoinColumn(name="attributeId")
	private Attribute attribute;
	
	private double score;

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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
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