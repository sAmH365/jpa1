package jpabook.jpashop.service

import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var memberRepository: MemberRepository

    @Test
    fun 회원가입() {
        // given
        val member = Member()
        member.name = "kim"

        // when
        val savedId = memberService.join(member)
        val findOne = memberRepository.findOne(savedId)

        // then
        assertThat(member).isEqualTo(findOne)
    }

    @Test
    fun 중복_회원_예외() {
        // given
        val member1 = Member()
        member1.name = "kim"

        val member2 = Member()
        member2.name = "kim"

        // when
        memberService.join(member1)

        // then
        // 예외가 발생해야한다.
        assertThatThrownBy {
            memberService.join(member2)
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessage("이미 존재하는 회원입니다.")
    }
}