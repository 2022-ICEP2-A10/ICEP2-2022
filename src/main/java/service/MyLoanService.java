package service;

import lombok.RequiredArgsConstructor;
import repository.CheckoutRepository;
import repository.MemberRepository;

import java.util.List;

import domain.Checkout;
import domain.Member;
import util.CurrentMember;

@RequiredArgsConstructor
public class MyLoanService {

	private final CheckoutRepository checkoutRepository;
	 public void myloan(String[] args) {

		 // 내 대출 목록 검색할 땐 아무 명령어가 없어야 하는데 checkoutrepository엔 user id를 넣어야함
		 List<Checkout> checkout = checkoutRepository.findAllByUserid(CurrentMember.getCurrentMember().getUserid());
		 for(Checkout c:checkout) {
			 System.out.println(c.toString());
		 }
	 }
}
