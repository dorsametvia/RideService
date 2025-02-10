package via.rider.interview.rideservice.proposal

import java.util.Date
import java.util.UUID

data class Proposal(
    val pickupTime: Date,
    val price: String,
    val id: UUID = UUID.randomUUID()
)