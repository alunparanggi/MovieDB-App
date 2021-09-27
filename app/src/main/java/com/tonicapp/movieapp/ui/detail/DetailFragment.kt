package com.tonicapp.movieapp.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tonicapp.movieapp.R
import com.tonicapp.movieapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail){

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel:DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel
        val binding = FragmentDetailBinding.bind(view)

        binding.apply {
            val movie = args.movie
            Glide.with(this@DetailFragment)
                .load("${movie.baseUrl}${movie.poster_path}")
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        tvDescription.isVisible = true
                        tvMovieTitle.isVisible = true
                        return false
                    }

                })
                .into(ivMoviePoster)

            var _isChecked = false
            CoroutineScope(Dispatchers.IO).launch{
                val count = viewModel.checkMovie(movie.id)
                withContext(Dispatchers.Main){
                    if (count > 0){
                        toggleFavorite.isChecked = true
                        _isChecked = true
                    }else{
                        toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }

            tvDescription.text = movie.overview
            tvMovieTitle.text = movie.original_title

            toggleFavorite.setOnClickListener {
                _isChecked = !_isChecked
                if (_isChecked){
                    viewModel.addToFavorite(movie)
                } else{
                    viewModel.removeFromFavorite(movie.id)
                }
                toggleFavorite.isChecked = _isChecked
            }

        }
    }

}