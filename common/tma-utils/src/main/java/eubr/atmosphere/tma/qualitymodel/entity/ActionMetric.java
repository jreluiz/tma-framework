package eubr.atmosphere.tma.qualitymodel.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the ActionMetric database table.
 * 
 */
@Entity
@NamedQuery(name="ActionMetric.findAll", query="SELECT a FROM ActionMetric a")
public class ActionMetric implements Serializable {

	private static final long serialVersionUID = -6027908542964175672L;

	@EmbeddedId
	private ActionMetricPK id;

	private double score;

	public ActionMetric() {
	}

	public ActionMetricPK getId() {
		return this.id;
	}

	public void setId(ActionMetricPK id) {
		this.id = id;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}