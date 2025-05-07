package com.mask.compose.enums

import androidx.annotation.StringRes
import com.mask.compose.R

/**
 * 动画类型
 *
 * Create by lishilin on 2025-05-07
 */
enum class AnimationType(@StringRes val textResId: Int) {

    AnimatedVisibility(R.string.animation_animated_visibility),
    AnimateContentSize(R.string.animation_animate_content_size),
    AnimateAsState(R.string.animation_animate_as_state),
    InfiniteTransition(R.string.animation_infinite_transition),

}