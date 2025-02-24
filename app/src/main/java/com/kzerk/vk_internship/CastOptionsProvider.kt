package com.kzerk.vk_internship

import android.content.Context
import com.google.android.gms.cast.CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID
import com.google.android.gms.cast.framework.CastOptions
import com.google.android.gms.cast.framework.CastOptions.Builder
import com.google.android.gms.cast.framework.OptionsProvider
import com.google.android.gms.cast.framework.SessionProvider

class CastOptionsProvider : OptionsProvider {
	override fun getCastOptions(context: Context): CastOptions {
		return Builder()
			.setReceiverApplicationId(DEFAULT_MEDIA_RECEIVER_APPLICATION_ID)
			.build()
	}

	override fun getAdditionalSessionProviders(p0: Context): MutableList<SessionProvider>? {
		return null
	}

}