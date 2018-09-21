package org.yuanhong.li.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.yuanhong.li.huajiao.VideoInfo;

@Component("huajiaoService")
public class HuajiaoService {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public VideoInfo getByMd5sum(final String md5sum) {
		if(md5sum == null) {
			return null;
		}
		String sql = "select * from hj_video_info where md5sum=?";
		
		List<VideoInfo> videoList =  jdbcTemplate.query(sql, new String[] {md5sum}, new RowMapper<VideoInfo>() {

			@Override
			public VideoInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				VideoInfo videoInfo = new VideoInfo();
				videoInfo.setId(rs.getLong("id"));
				videoInfo.setAuthorId(rs.getString("author_id"));
				videoInfo.setGmtCreate(rs.getLong("gmt_create"));
				videoInfo.setMd5sum(rs.getString("md5sum"));
				videoInfo.setMp4(rs.getString("mp4"));
				videoInfo.setPlayCount(rs.getInt("play_count"));
				videoInfo.setUid(rs.getString("uid"));
				videoInfo.setUidName(rs.getString("uid_name"));
				return videoInfo;
			}
		});
		if(videoList == null || videoList.size()==0) {
			return null;
		}
		if(videoList.size() == 1) {
			return videoList.get(0);
		}
		throw new RuntimeException("data base md5sum found more than one record."+md5sum);
	}
	
	public List<VideoInfo> queryByDateTime(final Long current){
		if(current == null) {
			return null;
		}
		String sql = "select * from hj_video_info where gmt_create=?";
		PreparedStatementSetter ps = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, current);
			}
		};
		return jdbcTemplate.query(sql, ps,new RowMapper<VideoInfo>() {

			@Override
			public VideoInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				VideoInfo videoInfo = new VideoInfo();
				videoInfo.setId(rs.getLong("id"));
				videoInfo.setAuthorId(rs.getString("author_id"));
				videoInfo.setGmtCreate(rs.getLong("gmt_create"));
				videoInfo.setMd5sum(rs.getString("md5sum"));
				videoInfo.setMp4(rs.getString("mp4"));
				videoInfo.setPlayCount(rs.getInt("play_count"));
				videoInfo.setUid(rs.getString("uid"));
				videoInfo.setUidName(rs.getString("uid_name"));
				return videoInfo;
			}
		});
	}
	
	public boolean addVideo(final VideoInfo video) {
		String sql = "insert into hj_video_info(author_id,uid,uid_name,play_count,mp4,gmt_create,md5sum) values(?,?,?,?,?,?,?)";
		PreparedStatementSetter ps = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, video.getAuthorId());
				ps.setString(2, video.getUid());
				ps.setString(3, video.getUidName());
				ps.setInt(4, video.getPlayCount());
				ps.setString(5, video.getMp4());
				ps.setLong(6, video.getGmtCreate());
				ps.setString(7, video.getMd5sum());
			}
		};
		return jdbcTemplate.update(sql, ps) == 1;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
