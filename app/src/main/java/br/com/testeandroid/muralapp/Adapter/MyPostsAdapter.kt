package br.com.testeandroid.muralapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import br.com.testeandroid.muralapp.Posts
import br.com.testeandroid.muralapp.R
import kotlinx.android.synthetic.main.layout_postsitem.view.*

class MyPostsAdapter(private val context: Context, private val postsList: MutableList<Posts>) :
    RecyclerView.Adapter<MyPostsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView =
            LayoutInflater.from(context).inflate(R.layout.layout_postsitem, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_title.text = postsList[position].title
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_title: TextView

        init {
            txt_title = itemView.txt_title
        }

    }
}