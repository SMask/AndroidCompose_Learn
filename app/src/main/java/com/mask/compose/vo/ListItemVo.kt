package com.mask.compose.vo

/**
 * ListItem
 *
 * Create by lishilin on 2025-05-06
 */
data class ListItemVo(
    val id: String?,
    val name: String?,
    val price: Int?, // 单价
    var quantity: Int? // 数量
) {
    val totalPrice
        get() = run {
            val price = price
            val quantity = quantity
            if (price != null && quantity != null) {
                price * quantity
            } else {
                0
            }
        }
}
