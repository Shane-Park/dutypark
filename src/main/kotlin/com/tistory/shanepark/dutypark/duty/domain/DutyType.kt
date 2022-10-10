package com.tistory.shanepark.dutypark.duty.domain

import com.tistory.shanepark.dutypark.duty.enums.Color
import com.tistory.shanepark.dutypark.member.domain.Department
import javax.persistence.*

@Entity
@Table(name = "duty_type")
class DutyType(
    val name: String,
    val index: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    val department: Department,
    @Enumerated(value = EnumType.STRING)
    val color: Color,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
