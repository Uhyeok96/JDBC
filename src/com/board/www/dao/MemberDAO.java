package com.board.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.board.www.dto.MemberDTO;

public class MemberDAO {
	// 회원 db에 대한 C(회원가입) R(로그인) U(회원정보수정) D(회원탈퇴)

	public MemberDAO() {

	} // 기본 생성자

	public MemberDAO(Connection connection) {
		// TODO Auto-generated constructor stub
	} // 커스텀 생성자

	// 메서드

	public void register(Connection connection, MemberDTO joinMemberDTO) { // 회원가입 처리
		// connection -> main에서 넘어온 jdbc 1,2단계
		// joinMemberDTO -> 키보드로 입력받은 id, pw 값이 들어 있다.
		// db에 키보드로 입력받은 값을 저장시켜 회원가입함

		try {
			String sql = "insert into member (mno, mid, mpw, mnickname, mdate) values (board_seq.nextval, ?, ?, ?, sysdate)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, joinMemberDTO.getMid());
			// service에서 키보드로 입력받은 id가 첫번째 ? 에 적용
			preparedStatement.setString(2, joinMemberDTO.getMpw());
			// service에서 키보드로 입력받은 pw가 두번째 ? 에 적용
			preparedStatement.setString(3, joinMemberDTO.getMnickname());
			// service에서 키보드로 입력받은 nickname가 세번째 ? 에 적용
			//int result;
			//result = preparedStatement.executeUpdate();
			preparedStatement.executeUpdate();

//			if (result > 0) {
//				System.out.println(result + "행이 추가 되었습니다.");
//				System.out.println("회원가입이 완료되었습니다.");
//				connection.commit(); // 저장
//			} else {
//				System.out.println("결과 : " + result + " 입니다.");
//				System.out.println("회원가입에 실패했습니다. 롤백됩니다.");
//				connection.rollback(); // 전 단계로 롤백 (회원가입에 실패)
//			}

			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("관리자 : sql문을 확인하세요");
			System.out.println("사용자 : 이미 사용중인 계정입니다. 다른 계정으로 입력해주세요");
			// e.printStackTrace();
		}
	}

	public MemberDTO login(Connection connection, MemberDTO loginMemberDTO) { // 로그인 처리
		// connection -> main에서 넘어온 jdbc 1,2단계
		// loginMemberDTO -> 로그인시 키보드로 입력받은 id, pw 값이 들어 있다.
		// db에 있는 로그인 값을 찾아 옴
		MemberDTO loginDTO = new MemberDTO(); // 리턴용 빈객체

		try {
			String sql = "select mno, mid, mpw, mnickname, mdate from member where mid = ? and mpw = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginMemberDTO.getMid());
			// service에서 받은 id가 첫번째 ? 에 적용
			preparedStatement.setString(2, loginMemberDTO.getMpw());
			// service에서 받은 pw가 두번째 ? 에 적용

			ResultSet resultSet = preparedStatement.executeQuery();
			// 위에서 만든 쿼리문을 실행하고 결과를 resultSet 표로 받는다.

			while (resultSet.next()) {
				loginDTO.setMno(resultSet.getInt("mno"));
				loginDTO.setMid(resultSet.getString("mid"));
				loginDTO.setMpw(resultSet.getString("mpw"));
				loginDTO.setMnickname(resultSet.getString("mnickname"));
				loginDTO.setMdate(resultSet.getDate("mdate"));
				// resultSet 표에 있는 정보를 MemberDTO 객체에 넣음
			}

			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("찾는 id와 pw가 없습니다.");
			System.out.println("관리자 : sql문을 확인하세요");
			System.out.println("회원 : id와 pw를 확인하세요");
			// e.printStackTrace();
		}

		return loginDTO; // 로그인 완성용 객체
	} // 로그인 메서드 종료

	public void update(MemberDTO modifyMemberDTO, Connection connection, MemberDTO loginMember) { // 회원 정보 수정

		try {
			String sql = "update member set mid = ?, mpw = ?, mnickname = ? where mid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, modifyMemberDTO.getMid());
			// service에서 키보드로 입력받은 id가 첫번째 ? 에 적용
			preparedStatement.setString(2, modifyMemberDTO.getMpw());
			// service에서 키보드로 입력받은 pw가 두번째 ? 에 적용
			preparedStatement.setString(3, modifyMemberDTO.getMnickname());
			// service에서 키보드로 입력받은 nickname가 세번째 ? 에 적용
			preparedStatement.setString(4, loginMember.getMid());
			// service에서 키보드로 입력받은 id가 네번째 ? 에 적용
//			int result = 0;
//			result = preparedStatement.executeUpdate();
			preparedStatement.executeUpdate();
//
//			if (result > 0) {
//				System.out.println(result + "행 실행이 완료되었습니다.");
//				System.out.println("회원 정보 수정 완료! 저장됩니다!!");
//				connection.commit();
//			} else {
//				System.out.println("결과 : " + result + " 입니다.");
//				System.out.println("회원 정보 수정 실패! 롤백됩니다!!");
//				connection.rollback();
//			}
			System.out.println("회원 정보 수정 완료!");
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("관리자 : sql문을 확인하세요");
			System.out.println("회원 정보 수정 실패, 다시 시도해주세요");
			// e.printStackTrace();
		}

	} // update() 종료

	public void delete(MemberDTO loginMember, Connection connection) { // 회원 탈퇴

		try {
			String sql = "delete from member where mid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginMember.getMid());
			//int result;
			//result = preparedStatement.executeUpdate();
			preparedStatement.executeUpdate();

//			if (result > 0) {
//				System.out.println(result + "행이 실행되었습니다");
//				System.out.println("삭제 완료! 저장됩니다.");
//				connection.commit();
//			} else {
//				System.out.println("결과 : " + result + " 입니다.");
//				System.out.println("삭제 실패! 롤백됩니다.");
//				connection.rollback();
//			}

		} catch (SQLException e) {
			System.out.println("관리자 : sql문을 확인하세요");
			// e.printStackTrace();
		}

	} // delete() 종료

}
