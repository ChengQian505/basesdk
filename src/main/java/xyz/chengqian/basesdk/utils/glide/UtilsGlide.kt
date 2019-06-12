package xyz.chengqian.basesdk.utils.glide

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import xyz.chengqian.basesdk.utils.glide.GlideCircleTransform

/**
 * @author 程前 created on 2018/11/7.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
object UtilsGlide {

    fun loadCircle(context: Activity, imageUrl: String, view: ImageView) {
        Glide.with(context).load(imageUrl).apply(RequestOptions().transform(GlideCircleTransform(context))).into(view)
    }

    fun loadCircle(context: Context, imageUrl: String, view: ImageView) {
        Glide.with(context).load(imageUrl).apply(RequestOptions().transform(GlideCircleTransform(context))).into(view)
    }

    fun loadCircle(context: Context, imageUrl: Int, view: ImageView) {
        Glide.with(context).load(imageUrl).apply(RequestOptions().transform(GlideCircleTransform(context))).into(view)
    }

    fun loadCircle(context: Fragment, imageUrl: String, view: ImageView) {
        Glide.with(context).load(imageUrl).apply(RequestOptions().transform(GlideCircleTransform(context.activity))).into(view)
    }

    fun loadTag(context: Context, imageUrl: String, view: ImageView) {
        if (imageUrl != view.tag)
            Glide.with(context).load(imageUrl)
                    .apply(RequestOptions()
                            .dontAnimate())
                    .into(object : SimpleTarget<Drawable>() {
                        override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                            view.tag = imageUrl
                            view.setImageDrawable(resource);
                        }
                    })
    }
    
    fun loadImg(activity:Activity,imagePath:String,imageView: ImageView){
        Glide.with(activity).load(imagePath).apply(RequestOptions()).into(imageView)
    }
    fun loadImageNoCache(activity:Activity,imagePath:String,imageView: ImageView){
        Glide.with(activity).load(imagePath).apply(RequestOptions()
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)// 不使用磁盘缓存
        ).into(imageView)
    }
    fun loadImg(fragment:Fragment,imagePath:String,imageView: ImageView){
        Glide.with(fragment).load(imagePath).apply(RequestOptions()).into(imageView)
    }
    fun loadImageNoCache(fragment: Fragment, imagePath:String, imageView: ImageView){
        Glide.with(fragment).load(imagePath).apply(RequestOptions()
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)// 不使用磁盘缓存
                ).into(imageView)
    }

}