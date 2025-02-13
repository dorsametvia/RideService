package via.rider.interview.rideservice.proposal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.intl.Locale
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import via.rider.interview.rideservice.R
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.text.format

class ProposalAdapter : RecyclerView.Adapter<ProposalAdapter.ProposalViewHolder>() {

    private val proposals: MutableList<Proposal> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProposalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_proposal, parent, false)
        return ProposalViewHolder(view)
    }

    override fun getItemCount(): Int = proposals.size

    override fun onBindViewHolder(holder: ProposalViewHolder, position: Int) {
        val proposal = proposals[position]
        holder.bind(proposal)
    }

    fun submitList(proposals: List<Proposal>?) {
        if (proposals == null) return
        this.proposals.clear()
        this.proposals.addAll(proposals)
        notifyDataSetChanged()
    }

    class ProposalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pickupTimeTextView: TextView = itemView.findViewById(R.id.pickup_time_text_view)
        private val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)

        @SuppressLint("SimpleDateFormat")
        fun bind(proposal: Proposal) {
            pickupTimeTextView.text = formatTime(proposal.pickupTime)
            priceTextView.text = proposal.price
        }
    }
}