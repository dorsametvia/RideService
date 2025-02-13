package via.rider.interview.rideservice.bookings

import androidx.activity.result.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import via.rider.interview.rideservice.proposal.Proposal
import via.rider.interview.rideservice.service.RideService

class BookingViewModel : ViewModel() {
    val passengerTypes = listOf("Adult", "Child", "Senior")

    private val _proposals = MutableStateFlow<List<Proposal>>(emptyList())
    val proposals: StateFlow<List<Proposal>> = _proposals.asStateFlow()

    val proposalsLiveData: LiveData<List<Proposal>> = _proposals.asLiveData()

    fun bookRide(
        pickUpLocation: String,
        dropOffLocation: String,
        passengers: Map<String, Int>,
        completion: (List<Proposal>) -> Unit
    ) {
        viewModelScope.launch {
            val fetchedProposals = RideService.shared.fetchRides(
                forUser = pickUpLocation,
                dropOffLocation = dropOffLocation,
                passengers = passengers
            )
            _proposals.value = fetchedProposals
            completion(fetchedProposals)
        }
    }
}