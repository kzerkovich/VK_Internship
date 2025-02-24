package com.kzerk.vk_internship

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.mediarouter.app.MediaRouteButton
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.SessionManager
import com.google.android.gms.cast.framework.SessionManagerListener


class MainActivity : AppCompatActivity() {
	private var castSession: CastSession? = null
	private lateinit var castContext: CastContext
	private lateinit var sessionManager: SessionManager
	private val sessionManagerListener: SessionManagerListenerImpl =
		SessionManagerListenerImpl()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		castContext = CastContext.getSharedInstance(this)
		sessionManager = castContext.sessionManager
		sessionManager.addSessionManagerListener(sessionManagerListener, CastSession::class.java)

		val mMediaRouteButton: MediaRouteButton = findViewById(R.id.media_route_button);
		CastButtonFactory.setUpMediaRouteButton(applicationContext, mMediaRouteButton);
	}

	private inner class SessionManagerListenerImpl : SessionManagerListener<CastSession> {
		override fun onSessionStarting(p0: CastSession) {}

		override fun onSessionStarted(p0: CastSession, p1: String) {
			playVideo()
		}

		override fun onSessionStartFailed(p0: CastSession, p1: Int) {}

		override fun onSessionSuspended(p0: CastSession, p1: Int) {}

		override fun onSessionResuming(p0: CastSession, p1: String) {}

		override fun onSessionResumed(p0: CastSession, p1: Boolean) {}

		override fun onSessionResumeFailed(p0: CastSession, p1: Int) {}

		override fun onSessionEnding(p0: CastSession) {}

		override fun onSessionEnded(p0: CastSession, p1: Int) {
			finish()
		}
	}

	private fun playVideo() {
		castSession = sessionManager.currentCastSession
		if (castSession != null && castSession!!.isConnected) {
			val mediaMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
			val mediaInfo = MediaInfo.Builder(VIDEO_URL)
				.setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
				.setContentType("video/mp4")
				.setMetadata(mediaMetadata)
				.build()

			val mediaLoadRequestData = MediaLoadRequestData.Builder()
				.setMediaInfo(mediaInfo)
				.setAutoplay(true)
				.build()

			castSession?.remoteMediaClient?.load(
				mediaLoadRequestData
			)
		} else {
			Toast.makeText(
				applicationContext,
				R.string.connect_error,
				Toast.LENGTH_SHORT
			).show()
		}
	}

	override fun onResume() {
		super.onResume()
		castSession = sessionManager.currentCastSession
	}

	override fun onDestroy() {
		super.onDestroy()
		sessionManager.removeSessionManagerListener(
			sessionManagerListener,
			CastSession::class.java
		)
	}

	companion object {
		private const val VIDEO_URL =
			"https://videolink-test.mycdn.me/?pct=1&sig=6QNOvp0y3BE&ct=0&clientType=45&mid=193241622673&type=5"
	}
}