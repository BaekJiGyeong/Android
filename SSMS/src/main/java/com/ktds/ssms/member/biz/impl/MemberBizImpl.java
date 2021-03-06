package com.ktds.ssms.member.biz.impl;

import javax.servlet.http.HttpSession;

import com.ktds.ssms.member.biz.MemberBiz;
import com.ktds.ssms.member.dao.MemberDAO;
import com.ktds.ssms.member.vo.LoginLogVO;
import com.ktds.ssms.member.vo.MemberVO;

import kr.co.hucloud.utilities.SHA256Util;

public class MemberBizImpl implements MemberBiz{

	private MemberDAO memberDAO;
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	@Override
	public boolean addNewMember(MemberVO member) {
		
		String salt = SHA256Util.generateSalt();
		member.setSalt(salt);
		
		String newPassword = SHA256Util.getEncrypt(member.getPassword(), salt);
		member.setPassword(newPassword);
		
		return memberDAO.addNewMember(member) > 0;
	}

	@Override
	public boolean doLoginMember(MemberVO member, HttpSession session) {
		
		// SALT 값 가져와 입력한 암호 암호화 처리
		String memberSalt = memberDAO.getSaltById(member.getId());
		String saltPassword = SHA256Util.getEncrypt(member.getPassword(), memberSalt);
		member.setPassword(saltPassword);
		
		// 로그인 처리
		MemberVO loginMember = memberDAO.doLoginMember(member);
		
		// ID PW 맞는 경우
		if ( loginMember != null ) {
			
			// 로그인 이력 ID 생성하기 위한 시간과 시퀀스 값
			String time = memberDAO.getNowTime();
			String currentSeq = memberDAO.getCurrentSeq();
			
			// 로그인 이력 ID 생성
			String loginLogId = "LOGIN-" + time + "-" + lpad(currentSeq, 6, "0");
			
			// 로그인 이력 객체
			LoginLogVO loginLogVO = new LoginLogVO();
			loginLogVO.setId(loginMember.getId());
			loginLogVO.setLogId(loginLogId);
			
			memberDAO.insertLoginLog(loginLogVO);
			
			session.setAttribute("_MEMBER_", loginMember);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * ID 생성하기 위한 LPAD
	 * 
	 * @author 김현섭
	 * 
	 * @param source
	 * @param length
	 * @param defValue
	 * @return
	 */
	private String lpad(String source, int length, String defValue) {
		int sourceLength = source.length();
		int needLength = length - sourceLength;
		
		for (int i = 0; i < needLength; i++) {
			source = defValue + source;
		}
		return source;
	}

	@Override
	public boolean isExistId(String id) {
		
		if ( memberDAO.isExistId(id) == null ) {
			return true;
		}
		else {
			return false;
		}
	}

}
