package com.ktds.ssms.member.dao;

import com.ktds.ssms.member.vo.LoginLogVO;
import com.ktds.ssms.member.vo.MemberVO;

public interface MemberDAO {

	public int addNewMember(MemberVO member);

	public String getSaltById(String id);

	public MemberVO doLoginMember(MemberVO member);

	public String getNowTime();

	public String getCurrentSeq();

	public void insertLoginLog(LoginLogVO loginLogVO);

	public String isExistId(String id);

}
