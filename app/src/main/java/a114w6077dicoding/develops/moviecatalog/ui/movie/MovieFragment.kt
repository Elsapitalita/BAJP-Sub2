package a114w6077dicoding.develops.moviecatalog.ui.movie

import a114w6077dicoding.develops.moviecatalog.ViewModelFactory
import a114w6077dicoding.develops.moviecatalog.adapter.MovieCatalogueAdapter
import a114w6077dicoding.develops.moviecatalog.databinding.FragmentMovieBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val fragmentMovieBinding get()= _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity!=null){
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            val movieAdapter = MovieCatalogueAdapter()
            fragmentMovieBinding.pbMovie.visibility = View.VISIBLE

            viewModel.getMovie().observe(viewLifecycleOwner, {
                fragmentMovieBinding.pbMovie.visibility = View.GONE
                movieAdapter.setMovie(it)
            })

            with(fragmentMovieBinding.rvMovie){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}