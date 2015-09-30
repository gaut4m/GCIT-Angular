/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

/**
 * @author Gautham
 *
 */
public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>>{

	@Autowired
	BookDAO bdao;

	public int createGenre(final Genre genre) throws ClassNotFoundException,
			SQLException {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String query = "insert into tbl_genre (genre_name) values(?)";
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {           

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
               PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,genre.getGenreName());
                
                
                return ps;
            }
        };
        
        
        
		template.update(psc,keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}

	public int updateGenre(Genre genre) throws ClassNotFoundException,
			SQLException {
		return template.update("update tbl_genre set genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public int deleteGenre(int genreId) throws ClassNotFoundException,
			SQLException {
		if(checkGenre(genreId)==0)
		return template.update("delete from tbl_genre where genre_id=?",
				new Object[] { genreId });
		return 0;
	}
	
	public int checkGenre(int genreId) throws ClassNotFoundException,
	SQLException {
	return template.queryForObject("select Count(*) from tbl_book_genres where genre_id=?",
		new Object[] {genreId},Integer.class);
	}

	public List<Genre> getAllGenres(String searchString, int pageNo, int pageSize) throws ClassNotFoundException,
			SQLException {
		
		setPageNo(pageNo);
		setPageSize(pageSize);
		searchString = "%" + searchString + "%";
		String query = "select * from tbl_genre where genre_name like ?";

		if (pageNo > -1) {
			int start = (pageNo - 1) * getPageSize();
			if (start >= 0) {
				query = query + " LIMIT " + start + " , " + getPageSize();
			} else {
				query = query + " LIMIT " + getPageSize();
			}
		}
		
		return template.query(query, new Object[] { searchString },this);
	}

	public Genre getGenre(Genre genre) throws ClassNotFoundException,
			SQLException {
		List<Genre> genres = new ArrayList<Genre>();
		genres = template.query("select * from tbl_genre where genre_id = ?",
				new Object[] { genre.getGenreId() },this);

		if (genres != null && genres.size() > 0) {
			return genres.get(0);
		}
		return null;
	}

	@Override
	public List<Genre> extractData(ResultSet rs) {
		List<Genre> genres = new ArrayList<Genre>();
		
		try {
			while (rs.next()) {
				Genre a = new Genre();
				a.setGenreId(rs.getInt("genre_id"));
				a.setGenreName(rs.getString("genre_name"));

				genres.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return genres;
	}

	public List<Genre> getGenreByBookId(int bookId) {
		List<Genre> genres = (List<Genre>)template.query("select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = ?)",
		new Object[] { bookId },this);
		return genres;
	}

//	@Override
//	public List extractDataFirstLevel(ResultSet rs) {
//		// TODO Auto-generated method stub
//		List<Genre> genres = new ArrayList<Genre>();
//		
//		try {
//			while (rs.next()) {
//				Genre a = new Genre();
//				a.setGenreId(rs.getInt("genre_id"));
//				a.setGenreName(rs.getString("genre_name"));
//				
//				genres.add(a);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return genres;
//	}
}
