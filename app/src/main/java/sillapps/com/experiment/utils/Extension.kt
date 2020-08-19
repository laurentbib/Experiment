package sillapps.com.experiment.utils

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.random.Random

@ExperimentalContracts
fun calledOnce(run: () -> Unit) {
    contract {
        callsInPlace(run, InvocationKind.EXACTLY_ONCE)
    }
    run()
}

fun Fragment.waitForTransition(targetView: View) {
    postponeEnterTransition()
    targetView.doOnPreDraw { startPostponedEnterTransition() }
}

fun random(start: Int = 1, end: Int = 30) = Random.nextInt(start, end)

fun ViewGroup.inflate(layoutId: Int): View = LayoutInflater.from(context).inflate(layoutId, this, false)

fun AppCompatImageView.loadImg(url: String, requestListener: RequestListener<Drawable>? = null) {
    Glide.with(context)
        .load(url)
        .listener(requestListener)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .apply(RequestOptions().dontTransform())
        .into(this)
}

fun AppCompatImageView.fadeIn(duration: Long): ViewPropertyAnimator = animate().setDuration(duration).alpha(1f)

