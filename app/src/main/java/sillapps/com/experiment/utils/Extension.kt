package sillapps.com.experiment.utils

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

fun Fragment.waitForTransition(targetView: View) {
    postponeEnterTransition()
    targetView.doOnPreDraw { startPostponedEnterTransition() }
}

fun AppCompatImageView.loadImg(url: String, requestListener: RequestListener<Drawable>? = null) {
    Glide.with(context)
        .load(url)
        .listener(requestListener)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .apply(RequestOptions().dontTransform())
        .into(this)
}