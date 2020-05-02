package com.example.sweethome.ui.cctv

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sweethome.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter


class CctvFragment : Fragment() {

    private lateinit var cctvViewModel: CctvViewModel
    private lateinit var exoPlayerView: PlayerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cctvViewModel =
            ViewModelProvider(this).get(CctvViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cctv, container, false)
        exoPlayerView = root.findViewById(R.id.cctvPlayer)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()

        val videoTrackSelectionFactory: TrackSelection.Factory =
            AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        val player: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        exoPlayerView.player = player


        val rtmpDataSourceFactory = RtmpDataSourceFactory()
        val videoSource: MediaSource = ExtractorMediaSource.Factory(rtmpDataSourceFactory)
            .createMediaSource(Uri.parse("rtmp://192.168.43.31/live/cctv"))

        player.prepare(videoSource)
        player.playWhenReady = true

    }


}
