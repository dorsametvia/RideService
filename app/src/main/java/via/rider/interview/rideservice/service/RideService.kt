package via.rider.interview.rideservice.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import via.rider.interview.rideservice.proposal.Proposal
import java.util.Date

class RideService {
    companion object {
        val shared = RideService()
    }

    suspend fun fetchRides(
        forUser: String,
        dropOffLocation: String,
        passengers: Map<String, Int>
    ): List<Proposal> = withContext(Dispatchers.Default) {
        // Simulate a network delay
        delay(1000) // 1 second delay

        // Return a list of proposals
        listOf(
            Proposal(
                pickupTime = Date(System.currentTimeMillis() + 60 * 5 * 1000), // 5 minutes from now
                price = "$20"
            ),
            Proposal(
                pickupTime = Date(System.currentTimeMillis() + 60 * 10 * 1000), // 10 minutes from now
                price = "$20"
            ),
            Proposal(
                pickupTime = Date(System.currentTimeMillis() + 60 * 15 * 1000), // 15 minutes from now
                price = "$20"
            )
        )
    }
}