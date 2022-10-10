package com.tistory.shanepark.dutypark.duty.controller

import com.tistory.shanepark.dutypark.duty.service.DutyService
import com.tistory.shanepark.dutypark.member.domain.dto.MemberDto
import com.tistory.shanepark.dutypark.member.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime
import java.time.YearMonth

@Controller
class DutyController(
    val memberService: MemberService,
    val dutyService: DutyService,
) {

    @GetMapping("/duty/edit/{name}")
    fun editDuty(
        @PathVariable name: String,
        model: Model,
        year: Int,
        month: Int,
    ): String {
        val member = memberService.findMemberByName(name)

        dutyService.findDutyByMemberAndYearAndMonth(member, year, month).let {
            model.addAttribute("duties", it)
        }
        dutyService.findAllDutyTypes(member.department).let {
            model.addAttribute("dutyTypes", it)
        }

        model.addAttribute("member", MemberDto(member))
        model.addAttribute("year", year)
        model.addAttribute("month", month)
        YearMonth.of(year, month).let {
            model.addAttribute("offset", it.atDay(1).dayOfWeek.value)
            model.addAttribute("lastDay", it.lengthOfMonth())
        }

        return "duty/duty-edit"
    }

    @GetMapping("/duty/{name}")
    fun retrieveMemberDuty(
        model: Model,
        @PathVariable name: String,
        @RequestParam(required = false) year: Int?,
        @RequestParam(required = false) month: Int?,
    ): String {
        val now = LocalDateTime.now()
        val year = year ?: now.year
        val month = month ?: now.monthValue
        val member = memberService.findMemberByName(name)

        dutyService.findDutyByMemberAndYearAndMonth(member, year, month).let {
            model.addAttribute("duties", it)
        }
        dutyService.findAllDutyTypes(member.department).let {
            model.addAttribute("dutyTypes", it)
        }

        model.addAttribute("member", MemberDto(member))
        model.addAttribute("year", year)
        model.addAttribute("month", month)

        val yearMonth = YearMonth.of(year, month)
        yearMonth.let {
            model.addAttribute("prevMonth", it.minusMonths(1))
            model.addAttribute("nextMonth", it.plusMonths(1))
            model.addAttribute("offset", it.atDay(1).dayOfWeek.value)
            model.addAttribute("lastDay", it.lengthOfMonth())
        }

        return "duty/duty"
    }

}
