package eubr.atmosphere.tma.entity.qualitymodel;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import eubr.atmosphere.tma.database.QualityModelManager;

/**
 * The persistent class for the metric database table.
 * @author JorgeLuiz
 */
@Entity(name="Metric")
@NamedQuery(name="metric.findAll", query="SELECT m FROM Metric m")
public class Metric {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int attributeId;

	private Integer descriptionId;
	
	private Integer probeId;
	
	private Integer resourceId;
	
	private String descriptionName;

	private String probeName;

	private String resourceName;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTimestampDataInserted;

	//bi-directional many-to-one association to Configurationprofile
	@ManyToOne
	@JoinColumn(name="configurationprofileId")
	private ConfigurationProfile configurationprofile;

	//bi-directional one-to-one association to Leafattribute
	@OneToOne
	@JoinColumn(name="attributeId")
	@LazyCollection(LazyCollectionOption.FALSE)
	private LeafAttribute attribute;
	
	//bi-directional many-to-one association to Data
//	@OneToMany(mappedBy="metric", fetch = FetchType.EAGER)
//	@Fetch(FetchMode.SUBSELECT)
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private Set<Data> data;
	
	@Transient
	private List<Data> data;
	
	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public Integer getDescriptionId() {
		return descriptionId;
	}

	public void setDescriptionId(Integer descriptionId) {
		this.descriptionId = descriptionId;
	}

	public Integer getProbeId() {
		return probeId;
	}

	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getDescriptionName() {
		return this.descriptionName;
	}

	public void setDescriptionName(String descriptionName) {
		this.descriptionName = descriptionName;
	}

	public String getProbeName() {
		return this.probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public ConfigurationProfile getConfigurationprofile() {
		return this.configurationprofile;
	}

	public void setConfigurationprofile(ConfigurationProfile configurationprofile) {
		this.configurationprofile = configurationprofile;
	}

	public LeafAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(LeafAttribute attribute) {
		this.attribute = attribute;
	}

//	public Set<Data> getData() {
//		if (this.data == null) {
//			this.data = new HashSet<>();
//		}
//		return this.data;
//	}
//
//	public void setData(Set<Data> data) {
//		this.data = data;
//	}
	
	public List<Data> updateData() {
		QualityModelManager qmm = new QualityModelManager();
		return qmm.getLimitedDataListByIdAndTimestamp(probeId, descriptionId, resourceId, lastTimestampDataInserted);
	}

	public Date getLastTimestampDataInserted() {
		return lastTimestampDataInserted;
	}

	public void setLastTimestampDataInserted(Date lastTimestampDataInserted) {
		this.lastTimestampDataInserted = lastTimestampDataInserted;
	}

	@Override
	public String toString() {
		return "Metric [descriptionName=" + descriptionName + ", probeName=" + probeName + ", resourceName="
				+ resourceName + "]";
	}

}