package via.rider.interview.rideservice.bookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import via.rider.interview.rideservice.R

class BookingFragment : Fragment() {

    private val viewModel: BookingViewModel by activityViewModels()
    private val passengerCount: MutableMap<String, Int> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmnet_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookRideButton: Button = view.findViewById(R.id.bookRideButton)
        val passengerTypes = viewModel.passengerTypes

        val list = view.findViewById<RecyclerView>(R.id.passengersList)
        list.adapter = PassengerAdapter(passengerCount, passengerTypes,
            onIncrease = {
                this.passengerCount[it] = (this.passengerCount[it] ?: 0) + 1
                list.adapter?.notifyDataSetChanged()
            },
            onDecrease = {
                if ((this.passengerCount[it] ?: 0) > 0) {
                    this.passengerCount[it] = (this.passengerCount[it] ?: 0) - 1
                    list.adapter?.notifyDataSetChanged()
                }
            })

        list.layoutManager = LinearLayoutManager(requireContext())

        bookRideButton.setOnClickListener {
            val passengers = mapOf("Adult" to 1)
            viewModel.bookRide("PickUp", "DropOff", passengers) { proposals ->
                val action =
                    BookingFragmentDirections.actionBookingFragmentToProposalsFragment(proposals.toTypedArray())
                findNavController().navigate(action)
            }
        }
    }
}

class PassengerAdapter(
    private val passengerCount: Map<String, Int>,
    private val passengerTypes: List<String>,
    private val onIncrease: (String) -> Unit,
    private val onDecrease: (String) -> Unit
) :
    RecyclerView.Adapter<PassengerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_passenger_type, parent, false)
        return PassengerViewHolder(view, onIncrease, onDecrease)
    }

    override fun getItemCount(): Int = passengerTypes.size

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) =
        holder.bind(passengerTypes[position], passengerCount[passengerTypes[position]] ?: 0)

}

class PassengerViewHolder(
    itemView: View,
    private val onIncrease: (String) -> Unit,
    private val onDecrease: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val passengerTypeText: TextView =
        itemView.findViewById<TextView>(R.id.passengerTypeText)
    private val decreaseButton: ImageButton =
        itemView.findViewById<ImageButton>(R.id.decreaseButton).apply {
            setColorFilter(resources.getColor(R.color.black))
        }
    private val increaseButton: ImageButton =
        itemView.findViewById<ImageButton>(R.id.increaseButton).apply {
            setColorFilter(resources.getColor(R.color.black))
        }

    fun bind(passengerType: String, amount: Int) {
        passengerTypeText.text = "$passengerType: $amount"
        decreaseButton.setOnClickListener { onDecrease(passengerType) }
        increaseButton.setOnClickListener { onIncrease(passengerType) }
    }
}
