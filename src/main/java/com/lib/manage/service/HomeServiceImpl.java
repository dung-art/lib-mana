package com.lib.manage.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.lib.manage.dto.StatisticProcessDasboard;
import com.lib.manage.dto.StatisticQueryData;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public StatisticProcessDasboard getStatistics(LocalDateTime issueDate, LocalDateTime expiryDate) {
		String query = "SELECT COUNT(*) FROM lb_user_info";
		String query1 = "SELECT COUNT(*) FROM lb_user_info";
		String query2 = "SELECT COUNT(DISTINCT userCode) FROM lb_borrow Where status = '..dang muon.'";
		// quá hạn
		String query3 = "SELECT COUNT(DISTINCT userCode) FROM lb_borrow Where status = '... dang muon' and borrowTime = ''";
		// mất sách
		String query4 = "SELECT COUNT(DISTINCT userCode) FROM lb_borrow Where status = '..lose.'";
		// cấm
		String query5 = "SELECT COUNT(*) FROM lb_user_info Where status = '..lose.'";

		// số sách đang được mượn
		String query6 = "SELECT COUNT(*) FROM lb_book Where status = '..lose.'";
		// số sách mượn quá hạn
		String query7 = "SELECT COUNT(DISTINCT bookId) FROM lb_borrow Where status = '..dang muon.' and borrowTime = ''";
		// số sách bị thất lạc
		String query8 = "SELECT COUNT(*) FROM lb_book Where status = '..lose.'";

//		UNION
//		SELECT City FROM Suppliers
//		ORDER BY City;

		SqlParameterSource param = new MapSqlParameterSource().addValue(query7, query8).addValue(query7, query8)
				.addValue(query7, query8).addValue(query7, query8).addValue(query7, query8).addValue(query7, query8
						);
		List<StatisticQueryData> statistic = jdbcTemplate.query(query,
				new ResultSetExtractor<List<StatisticQueryData>>() {
					@Override
					public List<StatisticQueryData> extractData(ResultSet rs) throws SQLException, DataAccessException {
						List<StatisticQueryData> ls = new ArrayList<StatisticQueryData>();
						while (rs.next()) {
							StatisticQueryData statisticQueryData = new StatisticQueryData();
							statisticQueryData.setType(rs.getString("TYPE"));
							statisticQueryData.setValue(rs.getLong("VALUE"));
							ls.add(statisticQueryData);
						}
						return ls;
					}
				});
		return null;
		// Dashboard
	}
}
