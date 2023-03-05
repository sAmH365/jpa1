package jpabook.jpashop.service

import jakarta.persistence.NoResultException
import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    val memberRepository: MemberRepository
) {
    // 회원가입
    @Transactional
    fun join(member: Member): Long? {
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id
    }

    fun validateDuplicateMember(member: Member) {
        // Exception
        val findMembers: MutableList<Member> = memberRepository.findByName(member.name ?: throw NoResultException("이름이 없습니다."))
        if (findMembers.isNotEmpty())  {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    // 회원 전체 조회
    fun findMembers(): MutableList<Member> {
        return memberRepository.findAll()
    }

    // 회원 단건 조회
    fun findMember(id: Long): Member? {
        return memberRepository.findOne(id)
    }
}