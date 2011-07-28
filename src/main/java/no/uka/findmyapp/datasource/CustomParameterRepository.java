package no.uka.findmyapp.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import no.uka.findmyapp.datasource.mapper.CustomParameterRowMapper;
import no.uka.findmyapp.datasource.mapper.FactRowMapper;
import no.uka.findmyapp.datasource.mapper.LocationCountRowMapper;
import no.uka.findmyapp.datasource.mapper.LocationReportRowMapper;
import no.uka.findmyapp.datasource.mapper.LocationRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleRowMapper;
import no.uka.findmyapp.datasource.mapper.SampleSignalRowMapper;
import no.uka.findmyapp.datasource.mapper.SignalRowMapper;
import no.uka.findmyapp.datasource.mapper.UserLocationRowMapper;
import no.uka.findmyapp.datasource.mapper.UserPositionRowMapper;
import no.uka.findmyapp.datasource.mapper.UserRowMapper;
import no.uka.findmyapp.model.CustomParameter;
import no.uka.findmyapp.model.Fact;
import no.uka.findmyapp.model.Location;
import no.uka.findmyapp.model.LocationCount;
import no.uka.findmyapp.model.LocationReport;
import no.uka.findmyapp.model.ManageParameterRespons;
import no.uka.findmyapp.model.Sample;
import no.uka.findmyapp.model.Signal;
import no.uka.findmyapp.model.User;
import no.uka.findmyapp.model.UserPosition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CustomParameterRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;
	private static final Logger logger = LoggerFactory
			.getLogger(CustomParameterRepository.class);

	
	/*
	 * --------------------------------UserReports--------------------------------
	 * -------
	 */

	public void addData(LocationReport locationReport, int locationId) {
		Date now = new Date();
		try {
			jdbcTemplate
					.update("INSERT INTO CUSTOM_PARAMETER_VALUE "
							+ "(position_location_id ,custom_parameter_id,float_value, string_value, time) "
							+ "VALUES(?,(SELECT custom_parameter_id FROM CUSTOM_PARAMETER WHERE parameter_name = ?),"
							+ "?,?,?)", locationId,
							locationReport.getParameterName(),
							locationReport.getParameterNumberValue(),
							locationReport.getParameterTextValue(), now);
			logger.info("Data logged: " + locationReport.toString());

		} catch (Exception e) {
			logger.error("Could not add data to the given parameter: " + e);
		}
	}

	public List<LocationReport> getLastUserReportedData(int locationId,
			int numberOfelements, String parName) {
		logger.info("Fetching data ");
		try {
			return jdbcTemplate.query("SELECT CPV.*, CP.*, U.full_name FROM "
					+ "CUSTOM_PARAMETER_VALUE AS CPV "
					+ "JOIN CUSTOM_PARAMETER AS CP "
					+ "ON CPV.custom_parameter_id = CP.custom_parameter_id "
					+ "JOIN USER AS U "
					+ "ON CPV.user_id = U.user_id "
					+ "WHERE parameter_name = ? "
					+ "AND position_location_id=? "
					+ "ORDER BY time DESC LIMIT 0,? ",
					new LocationReportRowMapper(), parName, locationId,
					numberOfelements);

		} catch (Exception e) {
			logger.error("Could get the last values of parameter: " + e);
			return null;
		}

	}

	public List<LocationReport> getUserReportedDataFromTo(int locationId,
			Date from, Date to, String parName) {
		logger.info("Fetching data ");
		try {
			return jdbcTemplate.query("SELECT CPV.*, CP.*, U.full_name FROM "
					+ "CUSTOM_PARAMETER_VALUE AS CPV "
					+ "JOIN CUSTOM_PARAMETER AS CP "
					+ "on CPV.custom_parameter_id = CP.custom_parameter_id "
					+ "JOIN USER AS U "
					+ "ON CPV.user_id = U.user_id "
					+ "WHERE parameter_name = ? "
					+ "AND position_location_id=? "
					+ "AND time >= ? AND time <= ?",
					new LocationReportRowMapper(), parName, locationId, from,
					to);

		} catch (Exception e) {
			logger.error("Could not parameter between dates: " + e);
			return null;
		}

	}

	public List<LocationReport> getUserReportedDataFrom(int locationId,
			Date from, String parName) {
		logger.info("Fetching data ");
		try {
			return jdbcTemplate.query("SELECT CPV.*, CP.*, U.full_name FROM "
					+ "CUSTOM_PARAMETER_VALUE AS CPV "
					+ "JOIN CUSTOM_PARAMETER AS CP "
					+ "on CPV.custom_parameter_id = CP.custom_parameter_id "
					+ "JOIN USER AS U "
					+ "ON CPV.user_id = U.user_id "
					+ "WHERE parameter_name = ? "
					+ "AND position_location_id=? " + "AND time >= ?",
					new LocationReportRowMapper(), parName, locationId, from);

		} catch (Exception e) {
			logger.error("Could ni get parameter from date: " + e);
			return null;
		}

	}

	public List<LocationReport> getUserReportedData(int locationId,
			String parName) {
		logger.info("Fetching data HEHR ");
		try {
			return jdbcTemplate.query("SELECT CPV.*, CP.*, U.full_name FROM "
					+ "CUSTOM_PARAMETER_VALUE AS CPV "
					+ "JOIN CUSTOM_PARAMETER AS CP "
					+ "on CPV.custom_parameter_id = CP.custom_parameter_id "
					+ "JOIN USER AS U "
					+ "ON CPV.user_id = U.user_id "
					+ "WHERE parameter_name = ? "
					+ "AND position_location_id=? ",
					new LocationReportRowMapper(), parName, locationId);

		} catch (Exception e) {
			logger.error("Read API for what arguments are allowed " + e);
			return null;
		}
	}

	/*
	 * ---------------------------ManageParameter--------------------------------
	 * -----
	 */
	public int addParameter(final String parName, final int devId) throws DataAccessException,DuplicateKeyException,DataIntegrityViolationException  {
		logger.debug("Adding Parameter:" + parName);
		try {
			
			final String sql = "INSERT INTO CUSTOM_PARAMETER (parameter_name,appstore_developer_id)VALUES(?,?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps =
			                connection.prepareStatement(sql, new String[] {"custom_parameter_id"});
			            ps.setString(1, parName);
			            ps.setInt(2, devId);
			            return ps;
			        }

			    },
			    keyHolder);
			Number n = keyHolder.getKey();
			return (Integer) n.intValue();
			//jdbcTemplate.update("INSERT INTO CUSTOM_PARAMETER (parameter_name,appstore_developer_id)VALUES(?,?)", parName, devId);
			
			//return true;
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Could not add the parameter: " + e);
			throw new DuplicateKeyException("Parameter: " + parName + " could not be added. Dublicate entry");
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			logger.error("Could not add the parameter: " + e);
			throw new DataIntegrityViolationException("Parameter: " + parName + " could not be added. Developer id not valid.");
		} catch (DataAccessException e) {
			logger.error("Could not add parameter: " + e);
			throw e;
			
		}

	}

	public boolean cleanParameter(String parName, int devId) throws DataAccessException{
		// Must have access check in service, add check if variables  exist!!
		logger.debug("Cleaning Parameter:" + parName);
		try {

			jdbcTemplate.update("DELETE FROM CUSTOM_PARAMETER_VALUE"
							+ " WHERE custom_parameter_id ="
							+ " (SELECT custom_parameter_id FROM CUSTOM_PARAMETER "
							+ "WHERE parameter_name = ? "
							+ "AND appstore_developer_id = ?)", parName, devId);
			return true;

		} catch (DataAccessException e) {
			logger.error("Could not clean the parameter: " + e);
			throw e;
		}
	}

	public boolean removeParameter(String parName, int devId) throws DataAccessException {
		// Must have access check in service, add check if variables  exist!!
		try {
			jdbcTemplate.update("DELETE FROM CUSTOM_PARAMETER_VALUE"
					+ " WHERE custom_parameter_id ="
					+ " (SELECT custom_parameter_id FROM CUSTOM_PARAMETER "
					+ "WHERE parameter_name = ? "
					+ "AND appstore_developer_id = ?)", parName, devId);// Cleaning.
			
			jdbcTemplate.update("DELETE FROM CUSTOM_PARAMETER WHERE "
							+ " parameter_name = ? AND  appstore_developer_id = ?",
							parName, devId);			
			return true;

		} catch (DataAccessException e) {
			logger.error("Could remove the parameter: " + e);
			throw e;
		}

	}

	public List<CustomParameter> findAllParameters() {	
		return jdbcTemplate.query("SELECT * FROM CUSTOM_PARAMETER", new CustomParameterRowMapper());
	}
}
