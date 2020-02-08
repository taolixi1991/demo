package com.wuhandata.wxncovblackboard.common.constant

enum class PatientStatus(value: Int) {
    RECOVERED(0),
    CONFIRMED_SEVERE(1),
    CONFIRMED_MILD(2),
    SUSPECTED_SEVERE(3),
    SUSPECTED_MILD(4),
    POSSIBLE(5),
    INCONTRACT(6)
}
