package via.rider.interview.rideservice.proposal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class Proposal(
    val pickupTime: Date,
    val price: String,
    val id: UUID = UUID.randomUUID()
): Parcelable