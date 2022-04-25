package a114w6077dicoding.develops.moviecatalog.adapter

import a114w6077dicoding.develops.moviecatalog.R
import a114w6077dicoding.develops.moviecatalog.data.TvResults
import a114w6077dicoding.develops.moviecatalog.databinding.ItemListBinding
import a114w6077dicoding.develops.moviecatalog.ui.detail.TvDetailActivity
import a114w6077dicoding.develops.moviecatalog.utils.Constanta
import a114w6077dicoding.develops.moviecatalog.utils.TvDiffCallback
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvShowCatalogueAdapter: RecyclerView.Adapter<TvShowCatalogueAdapter.TvShowViewHolder>(){

    private var tvShows = ArrayList<TvResults>()

    fun setTvShows(listTvCatalogue: List<TvResults>){
        val diffCallback = TvDiffCallback(this.tvShows, listTvCatalogue)
        val diffResults = DiffUtil.calculateDiff(diffCallback)
        this.tvShows.clear()
        this.tvShows.addAll(listTvCatalogue)
        diffResults.dispatchUpdatesTo(this)
    }

    class TvShowViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TvShowViewHolder,
        position: Int) {
        val tv = tvShows[position]
        holder.binding.apply {
            tvItemTitle.text = tv.original_name
            tvItemReleasedDate.text = tv.first_air_date
            Glide.with(holder.itemView.context)
                .load(Constanta.IMAGE + tv.poster_path)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(imagePoster)
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, TvDetailActivity::class.java)
            intent.putExtra(TvDetailActivity.EXTRA_DATA_ID, tv.id)
            intent.putExtra(TvDetailActivity.EXTRA_TITLE, tv.original_name)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tvShows.size


}
