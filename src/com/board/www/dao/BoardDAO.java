package com.board.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.board.www.dto.BoardDTO;
import com.board.www.dto.MemberDTO;

public class BoardDAO {
	// 데이터베이스 처리용 crud

	public BoardDAO() {

	} // 기본 생성자

	public void createBoard(Connection connection, MemberDTO loginMember, BoardDTO boardDTO) { // 게시물 생성 메서드

		try {
			String sql = "insert into board (bno, btitle, bcontent, bwriter, bdate) values (board_seq.nextval, ?, ?, ?, sysdate)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO.getBtitle()); // service에서 키보드로 입력받은 제목 삽입
			preparedStatement.setString(2, boardDTO.getBcontent()); // service에서 키보드로 입력받은 내용 삽입
			preparedStatement.setString(3, loginMember.getMid()); // 로그인 객체에서 닉네임 받음
			preparedStatement.executeUpdate(); // 쿼리문 실행

			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("관리자 : sql문을 확인해주세요.");
			// e.printStackTrace();
		}

	} // createBoard() 종료

	public void list(Connection connection) { // 게시물 읽기 메서드
		// BoardDTO boardDTO = null;

		try {
			String sql = "select bno, btitle, bcontent, bwriter, bdate from board order by bno desc";
			// board 테이블에 있는 데이터를 가져온다
			PreparedStatement preparedStatement = connection.prepareStatement(sql); // 3단계
			ResultSet resultSet = preparedStatement.executeQuery(); // 4단계
			// boardDTO = new BoardDTO();
			while (resultSet.next()) { // 표형식으로 리턴된 값 유무 판단

				System.out.print("no." + resultSet.getInt("bno") + "\t");
				System.out.print("제목 : " + resultSet.getString("btitle") + "\t");
				// System.out.print("내용 : " + resultSet.getString("bcontent") + "\t");
				System.out.print("작성자 : " + resultSet.getString("bwriter") + "\t");
				System.out.println("작성일 : " + resultSet.getDate("bdate") + "\t");

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
	} // list() 종료

	public void readBoard(Connection connection, MemberDTO loginMember, BoardDTO boardDTO) { // 로그인 객체의 게시물 수정 전 읽기 메서드

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String sql = "select bno, btitle, bcontent, bwriter, bdate from board where bwriter = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginMember.getMid());

			resultSet = preparedStatement.executeQuery(); // 4단계
			// boardDTO = new BoardDTO();
			while (resultSet.next()) { // 표형식으로 리턴된 값 유무 판단

				System.out.print("no." + resultSet.getInt("bno") + "\t");
				System.out.print("제목 : " + resultSet.getString("btitle") + "\t");
				// System.out.print("내용 : " + resultSet.getString("bcontent") + "\t");
				System.out.print("작성자 : " + resultSet.getString("bwriter") + "\t");
				System.out.println("작성일 : " + resultSet.getDate("bdate") + "\t");
			}

			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("관리자 : sql문을 확인하세요.");
			// e.printStackTrace();
		}
	} // readBoard() 종료

	public void select(int selectNum, Connection connection) { // 수정하려는 게시물 선택 메서드

		try {
			String sql = "select bno, btitle, bcontent, bwriter, bdate from board where bno = ? ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, selectNum);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("no." + resultSet.getInt("bno") + "\t");
				System.out.println("제목 : " + resultSet.getString("btitle") + "\t");
				System.out.println("내용 : " + resultSet.getString("bcontent") + "\t");
				System.out.println("작성자 : " + resultSet.getString("bwriter") + "\t");
				System.out.println("작성일 : " + resultSet.getDate("bdate") + "\t");
			}

		} catch (SQLException e) {
			System.out.println("관리자 : sql문을 확인해주세요");
			// e.printStackTrace();
		}
	} // select() 종료

	public void update(Connection connection, int selectNum, BoardDTO boardDTO2) {

		try {
			String sql = "update board set btitle = ?, bcontent = ? where bno = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO2.getBtitle());
			preparedStatement.setString(2, boardDTO2.getBcontent());
			preparedStatement.setInt(3, selectNum);
			preparedStatement.executeUpdate();
			System.out.println("수정이 완료되었습니다.");

			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("관리자 : sql문을 확인해주세요.");
			// e.printStackTrace();
		}
	} // update() 종료

	public void delete(Connection connection, int selectNum) { // 게시물 삭제 메서드

		try {
			String sql = "delete from board where bno = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, selectNum);
			preparedStatement.executeUpdate(); // 쿼리문 실행

			System.out.println("삭제가 완료되었습니다.");
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("관리자 : sql문을 확인해주세요.");
			// e.printStackTrace();
		}

	} // delete() 종료
}
