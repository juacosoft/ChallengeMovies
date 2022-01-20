package com.jmdev.challengemovies.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import challengemovies.databinding.VideoplayerFragmentBinding


import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

class VideoPlayerFragment : Fragment(),YouTubePlayerListener {

    private var _binding: VideoplayerFragmentBinding?=null
    private val binding get() = _binding!!
    private val TAG="YoutubeVideoPlayer"
    lateinit var urlVideo:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= VideoplayerFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        urlVideo=arguments?.getString("urlvideo")!!
        binding.youtubeVideoplayer.enableAutomaticInitialization=false
        binding.youtubeVideoplayer.initialize(this,true)
        binding.videoplayerBack.setOnClickListener{
            Navigation.findNavController(it).popBackStack()

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubeVideoplayer.release()
    }

    override fun onApiChange(youTubePlayer: YouTubePlayer) {
        Log.d(TAG,"$youTubePlayer")
    }

    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

        Log.d(TAG,"second: $second")
    }

    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
        Log.d(TAG,"$error")
        binding.lyYoutubeerror.visibility=View.VISIBLE

    }

    override fun onPlaybackQualityChange(
        youTubePlayer: YouTubePlayer,
        playbackQuality: PlayerConstants.PlaybackQuality
    ) {
        Log.d(TAG,"$playbackQuality")
    }

    override fun onPlaybackRateChange(
        youTubePlayer: YouTubePlayer,
        playbackRate: PlayerConstants.PlaybackRate
    ) {
        Log.d(TAG,"$playbackRate")
    }

    override fun onReady(youTubePlayer: YouTubePlayer) {
        youTubePlayer.loadVideo(urlVideo, 0F)
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
        Log.d(TAG,"$state")
    }

    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
        Log.d(TAG,"$duration")
    }

    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
        Log.d(TAG,videoId)
    }

    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
        Log.d(TAG,"$loadedFraction")
    }
}