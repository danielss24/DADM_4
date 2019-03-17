package com.example.cuatroenraya
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.example.cuatroenraya.Round

class RoundHolder(val textView: TextView): RecyclerView.ViewHolder(textView) {}

/*class RoundHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    lateinit var idTextView: TextView
    lateinit var boardTextView: TextView
    lateinit var dateTextView: TextView
    init {
        idTextView = itemView.findViewById(R.id.list_item_id) as TextView
        boardTextView = itemView.findViewById(R.id.list_item_board) as TextView
        dateTextView = itemView.findViewById(R.id.list_item_date) as TextView
    }
    fun bindRound(round: Round) {
        idTextView.text = round.title
        boardTextView.text = round.board?.tableroToString()
        dateTextView.text = round.date.toString().substring(0,19)
    }
}*/

class RoundAdapter(val rounds: List<Round>): RecyclerView.Adapter<RoundHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundHolder {
        return RoundHolder(TextView(parent.context))
    }
    override fun getItemCount(): Int = rounds.size

    override fun onBindViewHolder(holder: RoundHolder, position: Int) {
        holder.textView.text = "${rounds[position].title} \n $ {rounds[position].date.substringBefore('GMT')} \n"
    }
}

