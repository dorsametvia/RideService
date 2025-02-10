package via.rider.interview.rideservice.proposal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ViewModel
class ProposalsViewModel : ViewModel() {
    private val _proposals = MutableStateFlow<List<Proposal>>(emptyList())
    val proposals: StateFlow<List<Proposal>> = _proposals.asStateFlow()

    fun setProposals(newProposals: List<Proposal>) {
        _proposals.value = newProposals
    }
}
