package org.abapps.app.domain.entities

import io.realm.kotlin.internal.interop.CodeDescription

data class Item(
    val itemID: Long = 10000003869,
    val itemCode: Int = 3465,
    val upc: Int = 654,
    val alu: Int = 654,
    val name: String = "طقم ملايه بولي قطن",
    val name2: String = "uhukhkjhk",
    val description: String = " خسعيابستي بمتس بهتسي بمتسيمنب تسيمن بتمينتب منسيت بمنيست بمنتسيم بنتيسمن تبمن",
    val styleId: Int = 0,
    val styleName: String = "احمر",
    val onHand: Int = 51,
    val freeCard: Boolean = true,
    val freeCardPrice: Int = 10,
    val price: Int = 1000,
)
