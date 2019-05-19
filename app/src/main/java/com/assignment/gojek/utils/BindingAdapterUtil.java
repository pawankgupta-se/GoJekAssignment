package com.assignment.gojek.utils;


import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.assignment.gojek.R;
import com.crashlytics.android.Crashlytics;
import com.facebook.shimmer.ShimmerFrameLayout;

import timber.log.Timber;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class BindingAdapterUtil {
	@BindingAdapter("imageUrl")
	public static void loadImage(ImageView view, String imageUrl) {
		Glide.with(view.getContext())
				.load(imageUrl)
				.listener(new RequestListener<Drawable>() {
					@Override
					public boolean onLoadFailed(@Nullable GlideException e, Object model,
												Target<Drawable> target,
												boolean isFirstResource) {
						view.post(() -> view
								.setImageDrawable(ContextCompat.getDrawable(view.getContext(),
										R.drawable.ic_default_image)));
						view.setScaleType(ImageView.ScaleType.CENTER_CROP);
						return false;
					}

					@Override
					public boolean onResourceReady(Drawable resource, Object model,
												   Target<Drawable> target,
												   DataSource dataSource, boolean isFirstResource) {
						return false;
					}
				})
				.apply(new RequestOptions()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.centerCrop()
						.circleCrop())
				.into(view);
	}

	@BindingAdapter("progress")
	public static void showProgress(ShimmerFrameLayout progressView, boolean show) {
		if (show) {
			progressView.setVisibility(View.VISIBLE);
			progressView.startShimmerAnimation();
		} else {
			progressView.setVisibility(View.GONE);
			progressView.stopShimmerAnimation();
		}
	}

	@BindingAdapter("drawableTint")
	public static void showProgress(TextView view, String tint) {
		try {
			int color = Color.parseColor(tint);
			for (Drawable drawable : view.getCompoundDrawables()) {
				if (drawable != null) {
					drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
				}
			}
		} catch (NullPointerException e) {
			Timber.e(e);
		} catch (Exception e) {
			Timber.e(e);
		}
	}
}
