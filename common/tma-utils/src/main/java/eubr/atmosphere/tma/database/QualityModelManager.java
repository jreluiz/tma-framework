package eubr.atmosphere.tma.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eubr.atmosphere.tma.entity.qualitymodel.Data;
import eubr.atmosphere.tma.entity.qualitymodel.DataPK;
import eubr.atmosphere.tma.entity.qualitymodel.HistoricalData;

public class QualityModelManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(QualityModelManager.class);

	public int saveHistoricalData(HistoricalData historicalData) {
		String sql = "INSERT INTO HistoricalData(instant, value, attributeId) VALUES (?, ?, ?)";
		PreparedStatement ps;

		try {
			ps = DatabaseManager.getConnectionInstance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setTimestamp(1, new Timestamp(historicalData.getId().getInstant().getTime()));
			ps.setDouble(2, Double.isNaN(historicalData.getValue()) ? 0 : historicalData.getValue());
			ps.setInt(3, historicalData.getAttribute().getAttributeId());

			DatabaseManager databaseManager = new DatabaseManager();
			return databaseManager.execute(ps);
		} catch (SQLException e) {
			LOGGER.error("[ATMOSPHERE] Error when inserting HistoricalData in the database.", e);
		}
		return -1;
	}
	
	public List<Data> getLimitedDataListByIdAndTimestamp(Integer probeId, Integer descriptionId, Integer resourceId,
			Date date) {

		List<Data> dataList = new ArrayList<>();
		PreparedStatement ps = null;
		String sql = "SELECT d.probeid, " + 
							" d.descriptionid, " + 
							" d.resourceid, " + 
							" d.valuetime, " + 
							" d.metricid, " + 
							" d.value " + 
					" FROM   Data d " + 
					" WHERE  d.probeid = ? " + 
							" AND d.descriptionid = ? " + 
							" AND d.resourceid = ? " + 
							" AND d.valuetime = ? ";

		try {

			ps = DatabaseManager.getConnectionInstance().prepareStatement(sql);
			ps.setInt(1, probeId);
			ps.setInt(2, descriptionId);
			ps.setInt(3, resourceId);
			ps.setTimestamp(4, date != null ? new Timestamp(date.getTime()) : null);

			ResultSet rs = DatabaseManager.executeQuery(ps);
			if (rs.next()) {

				Data data = new Data();
				DataPK dataPK = new DataPK();
				dataPK.setProbeId(rs.getInt("probeId"));
				dataPK.setDescriptionId(rs.getInt("descriptionId"));
				dataPK.setResourceId(rs.getInt("resourceId"));
				dataPK.setValueTime(rs.getDate("valueTime"));
				data.setId(dataPK);
				data.setMetricId(rs.getInt("metricId"));
				data.setValue(rs.getDouble("value"));

				dataList.add(data);

			}

			return dataList;
		} catch (SQLException e) {
			LOGGER.error("[ATMOSPHERE] Error when getting plan using planId.", e);
		}

		return null;
	}

}
