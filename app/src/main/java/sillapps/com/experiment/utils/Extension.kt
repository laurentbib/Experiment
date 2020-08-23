package sillapps.com.experiment.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import sillapps.com.experiment.R
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.random.Random

fun Activity.showSnackbar(msgRes: Int) {
    Snackbar.make(findViewById<CoordinatorLayout>(R.id.main_coordinator), getString(msgRes), Snackbar.LENGTH_LONG).show()
}

fun Fragment.waitForTransition(targetView: View) {
    postponeEnterTransition()
    targetView.doOnPreDraw { startPostponedEnterTransition() }
}

fun ViewGroup.inflate(layoutId: Int): View = LayoutInflater.from(context).inflate(layoutId, this, false)

fun AppCompatImageView.loadImg(url: String, requestListener: RequestListener<Drawable>? = null, withPlaceHolder: Boolean = false) {
    Glide.with(context)
        .load(url)
        .listener(requestListener)
        .placeholder(if (withPlaceHolder) CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 30f
            start()
        } else null)
        .error(R.drawable.ic_cloud_off)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .apply(RequestOptions().dontTransform())
        .into(this)
}

fun AppCompatImageView.fadeIn(duration: Long): ViewPropertyAnimator = animate().setDuration(duration).alpha(1f)

fun ShimmerFrameLayout.beginShimmerAnim() {
    if (!isShimmerStarted) startShimmer()
    show()
}

fun ShimmerFrameLayout.endShimmerAnim() {
    hide()
    if (isShimmerStarted) stopShimmer()
}

fun View.show() {
    animate().alpha(1.0f)
}

fun View.hide() {
    animate().alpha(0.0f)
}

fun uselessError(north: AppCompatImageView, east: AppCompatImageView, west: AppCompatImageView) {
    val factor = 0.8f
    val triple = when (random(0, 2)) {
        0 -> Triple(north, "translationY", (north.translationY + (north.height * factor)))
        1 -> Triple(east, "translationX", (east.translationX + (-east.width * factor)))
        2 -> Triple(west, "translationX", (west.translationX + (west.width * factor)))
        else -> null
    }
    triple?.run {
        ObjectAnimator.ofFloat(first, second, third).apply {
            interpolator = DecelerateInterpolator()
            duration = ANIM_DELAY
            repeatMode = ValueAnimator.REVERSE
            repeatCount = 1
        }.start()
    }
}

fun random(start: Int = 1, end: Int = 30) = Random.nextInt(start, end)

@ExperimentalContracts
fun calledOnce(run: () -> Unit) {
    contract {
        callsInPlace(run, InvocationKind.EXACTLY_ONCE)
    }
    run()
}


