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
import org.yuanhong.li.comic.ComicInfo;
import org.yuanhong.li.comic.ComicSection;

@Component("comicService")
public class ComicService {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public ComicInfo getByComicId(String source,String comicId) {
		if(source == null || comicId == null) {
			return null;
		}
		String sql = "select * from net_comic_info where source=? and comic_id=?";
		List<ComicInfo> infoList =  jdbcTemplate.query(sql, new String[] {source,comicId}, new RowMapper<ComicInfo>() {

			@Override
			public ComicInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				ComicInfo info = new ComicInfo();
				info.setId(rs.getLong("id"));
				info.setAuthor(rs.getString("author"));
				info.setComicId(rs.getString("comic_id"));
				info.setCover(rs.getString("cover"));
				info.setDescription(rs.getString("description"));
				info.setRawTags(rs.getString("raw_tag"));
				info.setSource(rs.getString("source"));
				info.setStatus(rs.getInt("status"));
				info.setTitle(rs.getString("title"));
				return info;
			}
		});
		if(infoList == null || infoList.size()==0) {
			return null;
		}
		if(infoList.size() == 1) {
			return infoList.get(0);
		}
		throw new RuntimeException("getByComicId found more than one record."+comicId);
	}
	
	public ComicInfo getByComicIdAndStatus(String source,String comicId, int status) {
		if(source == null || comicId == null) {
			return null;
		}
		String sql = "select * from net_comic_info where source=? and comic_id=? and status=?";
		List<ComicInfo> infoList =  jdbcTemplate.query(sql, new Object[] {source,comicId, status}, new RowMapper<ComicInfo>() {

			@Override
			public ComicInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				ComicInfo info = new ComicInfo();
				info.setId(rs.getLong("id"));
				info.setAuthor(rs.getString("author"));
				info.setComicId(rs.getString("comic_id"));
				info.setCover(rs.getString("cover"));
				info.setDescription(rs.getString("description"));
				info.setRawTags(rs.getString("raw_tag"));
				info.setSource(rs.getString("source"));
				info.setStatus(rs.getInt("status"));
				info.setTitle(rs.getString("title"));
				return info;
			}
		});
		if(infoList == null || infoList.size()==0) {
			return null;
		}
		if(infoList.size() == 1) {
			return infoList.get(0);
		}
		throw new RuntimeException("getByComicIdAndStatus found more than one record."+comicId);
	}
	
	public List<ComicInfo> getComicListByStatus(String source, int status) {
		if(source == null ) {
			return null;
		}
		String sql = "select * from net_comic_info where source=? and status=?";
		List<ComicInfo> infoList =  jdbcTemplate.query(sql, new Object[] {source, status}, new RowMapper<ComicInfo>() {

			@Override
			public ComicInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				ComicInfo info = new ComicInfo();
				info.setId(rs.getLong("id"));
				info.setAuthor(rs.getString("author"));
				info.setComicId(rs.getString("comic_id"));
				info.setCover(rs.getString("cover"));
				info.setDescription(rs.getString("description"));
				info.setRawTags(rs.getString("raw_tag"));
				info.setSource(rs.getString("source"));
				info.setStatus(rs.getInt("status"));
				info.setTitle(rs.getString("title"));
				return info;
			}
		});
		return infoList;
	}
	
	public boolean saveComicInfo(final ComicInfo info) {
		String sql = "insert into net_comic_info(comic_id,source,title,author,description,raw_tag,cover,status) values(?,?,?,?,?,?,?,?)";
		PreparedStatementSetter ps = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, info.getComicId());
				ps.setString(2, info.getSource());
				ps.setString(3, info.getTitle());
				ps.setString(4, info.getAuthor());
				ps.setString(5, info.getDescription());
				ps.setString(6, info.getRawTags());
				ps.setString(7, info.getCover());
				ps.setInt(8, info.getStatus());
			}
		};
		return jdbcTemplate.update(sql, ps) == 1;
	}
	
	public boolean updateComicStatus(final Long id, final int status) {
		String sql = "update net_comic_info set status=? where id=?";
		PreparedStatementSetter ps = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, status);
				ps.setLong(2, id);
			}
		};
		return jdbcTemplate.update(sql, ps) == 1;
	}
	
	public List<ComicSection> getSectionList(String source, String comicId) {
		if(source == null || comicId == null) {
			return null;
		}
		String sql = "select * from net_comic_section where source=? and comic_id=?";
		List<ComicSection> sectionList =  jdbcTemplate.query(sql, new String[] {source,comicId}, new RowMapper<ComicSection>() {

			@Override
			public ComicSection mapRow(ResultSet rs, int rowNum) throws SQLException {
				ComicSection section = new ComicSection();
				section.setId(rs.getLong("id"));
				section.setComicId(rs.getString("comic_id"));
				section.setSectionId(rs.getString("section_id"));
				section.setSectionName(rs.getString("section_name"));
				section.setSort(rs.getString("sort"));
				section.setSource(rs.getString("source"));
				section.setStatus(rs.getInt("status"));
				return section;
			}
		});
		
		return sectionList;
	}
	
	public List<ComicSection> getSectionListByComicAndStatus(String source, String comicId, int status) {
		if(source == null || comicId == null) {
			return null;
		}
		String sql = "select * from net_comic_section where source=? and comic_id=? and status=?";
		List<ComicSection> sectionList =  jdbcTemplate.query(sql, new Object[] {source,comicId, status}, new RowMapper<ComicSection>() {

			@Override
			public ComicSection mapRow(ResultSet rs, int rowNum) throws SQLException {
				ComicSection section = new ComicSection();
				section.setId(rs.getLong("id"));
				section.setComicId(rs.getString("comic_id"));
				section.setSectionId(rs.getString("section_id"));
				section.setSectionName(rs.getString("section_name"));
				section.setSort(rs.getString("sort"));
				section.setSource(rs.getString("source"));
				section.setStatus(rs.getInt("status"));
				return section;
			}
		});
		
		return sectionList;
	}
	
	public List<ComicSection> getSectionListByStatus(String source,int status) {
		if(source == null) {
			return null;
		}
		String sql = "select * from net_comic_section where source=? and status=?";
		List<ComicSection> sectionList =  jdbcTemplate.query(sql, new Object[] {source, status}, new RowMapper<ComicSection>() {

			@Override
			public ComicSection mapRow(ResultSet rs, int rowNum) throws SQLException {
				ComicSection section = new ComicSection();
				section.setId(rs.getLong("id"));
				section.setComicId(rs.getString("comic_id"));
				section.setSectionId(rs.getString("section_id"));
				section.setSectionName(rs.getString("section_name"));
				section.setSort(rs.getString("sort"));
				section.setSource(rs.getString("source"));
				section.setStatus(rs.getInt("status"));
				return section;
			}
		});
		
		return sectionList;
	}
	
	public boolean saveSection(final ComicSection section) {
		String sql = "insert into net_comic_section(comic_id,source,section_id,section_name,sort,status) values(?,?,?,?,?,?)";
		PreparedStatementSetter ps = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, section.getComicId());
				ps.setString(2, section.getSource());
				ps.setString(3, section.getSectionId());
				ps.setString(4, section.getSectionName());
				ps.setString(5, section.getSort());
				ps.setInt(6, section.getStatus());
			}
		};
		return jdbcTemplate.update(sql, ps) == 1;
	}
	
	public boolean updateSectionStatus(final Long id, final int status) {
		String sql = "update net_comic_section set status=? where id=?";
		PreparedStatementSetter ps = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, status);
				ps.setLong(2, id);
			}
		};
		return jdbcTemplate.update(sql, ps) == 1;
	}
}
