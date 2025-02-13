package via.rider.interview.rideservice.proposal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.layout.layout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import via.rider.interview.rideservice.R

class ProposalsFragment : Fragment() {

    private val viewModel: ProposalsViewModel by activityViewModels()
    private lateinit var adapter: ProposalAdapter
    private lateinit var proposalsRecyclerView: RecyclerView
    private lateinit var routeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proposals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        proposalsRecyclerView = view.findViewById(R.id.proposals_recycler_view)
        routeTextView = view.findViewById(R.id.route_text_view)

        adapter = ProposalAdapter()
        proposalsRecyclerView.adapter = adapter
        proposalsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val proposals = arguments?.getParcelableArray("proposals")?.filterIsInstance<Proposal>()
        if (proposals != null) {
            viewModel.setProposals(proposals)
        }

        viewModel.proposals.asLiveData().observe(viewLifecycleOwner, Observer { proposals ->
            adapter.submitList(proposals)
        })
    }
}