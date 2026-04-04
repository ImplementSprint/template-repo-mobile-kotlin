package com.implementsprint.mobile

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SampleKotestSpec :
    FunSpec({
        test("template sanity check") {
            (2 + 2) shouldBe 4
        }
    })
