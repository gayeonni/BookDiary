/*
어시슈트 - 소북소북 코드입니다.

정보보호학과 2020111323 김지원
정보보호학과 2021111325 김해린
정보보호학과 2021111336 송다은(팀 대표)
정보보호학과 2021111694 이가연

 */

package com.android.bookdiary

// DailyChoiceFragment 내의 아이템을 클릭할 수 있도록 하는 Handler
interface DailyClickHandler {
    fun clickedBookItem(book: DailyChoiceData)
}