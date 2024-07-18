package com.board.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.board.www.dto.BoardDTO;

public class BoardDAO {
	// 데이터베이스 처리용 crud

	public void list(Connection connection) {
		//BoardDTO boardDTO = null;

		try {
			String sql = "select bno, btitle, bcontent, bwriter, bdate from board order by bno desc";
			// board 테이블에 있는 데이터를 가져온다
			PreparedStatement preparedStatement = connection.prepareStatement(sql); // 3단계
			ResultSet resultSet = preparedStatement.executeQuery(); // 4단계
			//boardDTO = new BoardDTO();
			while (resultSet.next()) { // 표형식으로 리턴된 값 유무 판단
				
				System.out.print(resultSet.getInt("bno") + "\t");
				System.out.print(resultSet.getString("btitle") + "\t");
				//System.out.print(resultSet.getString("bcontent") + "\t");
				System.out.print(resultSet.getString("bwriter") + "\t");
				System.out.println(resultSet.getDate("bdate") + "\t");
				
				/*
				 * boardDTO.setBno(resultSet.getInt("bno"));
				 * boardDTO.setBtitle(resultSet.getString("btitle"));
				 * boardDTO.setBcontent(resultSet.getString("bcontent"));
				 * boardDTO.setBwriter(resultSet.getString("bwiter"));
				 * boardDTO.setBdate(resultSet.getDate("bdate"));
				 */
			}
			// 5단계
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("BoardDAO.list() sql문 오류");
			e.printStackTrace();
		}

	}

}
