package jpabook.jpashop

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class MemberRepositoryTest {

    @Autowired lateinit var memberRepository: MemberRepository

    @Test
    @Transactional
    @Rollback(false)
    fun testMember() {
        // given
        val member = Member()
        member.username = "userC"

        // when
        val savedId = memberRepository.save(member)
        val findMember: Member? = memberRepository.find(savedId!!)

        // then
        Assertions.assertThat(findMember?.id).isEqualTo(member.id)
        Assertions.assertThat(findMember?.username).isEqualTo(member.username)
        Assertions.assertThat(findMember).isEqualTo(member)
    }
}