package com.eunocompany.home4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.eunocompany.home4.dto.BDto;

public class BDao {
	
	DataSource dataSource;
	
	public BDao() {
		super();
		// TODO Auto-generated constructor stub
		
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<BDto> list() {
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection conn = null;//DB 연결 생성
		PreparedStatement pstmt = null;//sql 실행
		ResultSet rs = null;//select 일때 결과 받아주는 객체
		
		try {
			conn = dataSource.getConnection();//dataSource에서 connection 생성
			//System.out.println(conn);
			
			String sql = "SELECT * FROM mvc_board ORDER BY bgroup DESC, bstep ASC ";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
									
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {//rs에 들어있는 글들의 수만큼 반복				
				int bid = rs.getInt("bid");
				String bname = rs.getString("bname");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Timestamp bdate = rs.getTimestamp("bdate");
				int bhit = rs.getInt("bhit");
				int bgroup = rs.getInt("bgroup");
				int bstep = rs.getInt("bstep");
				int bindent = rs.getInt("bindent");
				
				BDto dto = new BDto(bid, bname, btitle, bcontent, bdate, bhit, bgroup, bstep, bindent);
				dtos.add(dto);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return dtos;
		
	}
	
	public void write(String bname, String btitle, String bcontent) {
		
		Connection conn = null;//DB 연결 생성
		PreparedStatement pstmt = null;//sql 실행		
		
		try {
			conn = dataSource.getConnection();//dataSource에서 connection 생성	
			String sql = "INSERT INTO mvc_board(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent) "
					+ "VALUES(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
									
			pstmt = conn.prepareStatement(sql);	
			
			pstmt.setString(1, bname);
			pstmt.setString(2, btitle);
			pstmt.setString(3, bcontent);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public BDto contentView(String cid) {
		
		upHit(cid);//조회수 증가 함수 호출
		
		BDto dto = null;
		
		Connection conn = null;//DB 연결 생성
		PreparedStatement pstmt = null;//sql 실행
		ResultSet rs = null;//select 일때 결과 받아주는 객체
		
		try {
			conn = dataSource.getConnection();//dataSource에서 connection 생성		
			
			String sql = "SELECT * FROM mvc_board WHERE bid=?";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
									
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {//rs에 들어있는 글들의 수만큼 반복				
				int bid = rs.getInt("bid");
				String bname = rs.getString("bname");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Timestamp bdate = rs.getTimestamp("bdate");
				int bhit = rs.getInt("bhit");
				int bgroup = rs.getInt("bgroup");
				int bstep = rs.getInt("bstep");
				int bindent = rs.getInt("bindent");
				
				dto = new BDto(bid, bname, btitle, bcontent, bdate, bhit, bgroup, bstep, bindent);
				
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return dto;
		
	}
	
	public void modify(String bid, String btitle, String bcontent) {
		
		Connection conn = null;//DB 연결 생성
		PreparedStatement pstmt = null;//sql 실행		
		
		try {
			conn = dataSource.getConnection();//dataSource에서 connection 생성	
			String sql = "UPDATE mvc_board SET btitle=?, bcontent=? WHERE bid=?";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
									
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, btitle);
			pstmt.setString(2, bcontent);
			pstmt.setString(3, bid);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void delete(String bid) {
		
		Connection conn = null;//DB 연결 생성
		PreparedStatement pstmt = null;//sql 실행		
		
		try {
			conn = dataSource.getConnection();//dataSource에서 connection 생성	
			String sql = "DELETE FROM mvc_board WHERE bid=?";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
									
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bid);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void upHit(String bid) {
		
		Connection conn = null;//DB 연결 생성
		PreparedStatement pstmt = null;//sql 실행		
		
		try {
			conn = dataSource.getConnection();//dataSource에서 connection 생성	
			String sql = "UPDATE mvc_board SET bhit=bhit+1 WHERE bid=?";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
									
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bid);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void reply(String bid, String bname, String btitle, String bcontent, String bgroup, String bstep, String bindent) {
		
		replySort(bgroup, bstep);
		
		Connection conn = null;//DB 연결 생성
		PreparedStatement pstmt = null;//sql 실행		
		
		try {
			conn = dataSource.getConnection();//dataSource에서 connection 생성	
			String sql = "INSERT INTO mvc_board(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent) "
					+ "VALUES(mvc_board_seq.nextval, ?, ?, ?, 0, ?, ?, ?)";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
									
			pstmt = conn.prepareStatement(sql);	
			
			pstmt.setString(1, bname);
			pstmt.setString(2, btitle);
			pstmt.setString(3, bcontent);
			pstmt.setString(4, bgroup);
			pstmt.setInt(5, Integer.parseInt(bstep)+1);
			pstmt.setInt(6, Integer.parseInt(bindent)+1);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void replySort(String bgroup, String bstep) {
		
		Connection conn = null;//DB 연결 생성
		PreparedStatement pstmt = null;//sql 실행		
		
		try {
			conn = dataSource.getConnection();//dataSource에서 connection 생성	
			String sql = "UPDATE mvc_board SET bstep=bstep+1 WHERE bgroup=? AND bstep>?";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
									
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bgroup);
			pstmt.setString(2, bstep);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public int boardCount() {
		
		Connection conn = null;//DB 연결 생성
		PreparedStatement pstmt = null;//sql 실행
		ResultSet rs = null;//select 일때 결과 받아주는 객체
		
		int boardCount = 0;
		
		try {
			conn = dataSource.getConnection();//dataSource에서 connection 생성		
			
			String sql = "SELECT count(*) FROM mvc_board";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
									
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			System.out.println(rs);
			
			while(rs.next()) {//rs에 들어있는 글들의 수만큼 반복				
				
				//boardCount = boardCount + 1;
				boardCount = rs.getInt(1);
				
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return boardCount;
		
	}
	
}