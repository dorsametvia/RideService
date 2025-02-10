package via.rider.interview.rideservice.bookings

import androidx.activity.result.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Stepper
import via.rider.interview.rideservice.proposal.Proposal
import kotlin.collections.toMutableMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingView(
    onNavigateToProposals: (List<Proposal>) -> Unit,
    viewModel: BookingViewModel = viewModel()
) {
    var pickUpLocation by remember { mutableStateOf("") }
    var dropOffLocation by remember { mutableStateOf("") }
    var passengers by remember { mutableStateOf(mapOf<String, Int>()) }
    val passengerTypes = viewModel.passengerTypes
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Book a ride",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = pickUpLocation,
                onValueChange = { pickUpLocation = it },
                label = { Text("Pick-up Location") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            OutlinedTextField(
                value = dropOffLocation,
                onValueChange = { dropOffLocation = it },
                label = { Text("Drop-off Location") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Passengers",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Divider(modifier = Modifier.padding(bottom = 8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(passengerTypes) { type ->
                    var passengerCount by remember { mutableIntStateOf(passengers[type] ?: 0) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("$type: $passengerCount")
                        Row {
                            IconButton(onClick = {
                                if (passengerCount > 0) {
                                    passengerCount--
                                    passengers = passengers.toMutableMap().apply { this[type] = passengerCount }
                                }
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Remove")
                            }
                            IconButton(onClick = {
                                passengerCount++
                                passengers = passengers.toMutableMap().apply { this[type] = passengerCount }
                            }) {
                                Icon(Icons.Filled.Add, contentDescription = "Add")
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.bookRide(
                            pickUpLocation = pickUpLocation,
                            dropOffLocation = dropOffLocation,
                            passengers = passengers
                        ) { proposals ->
                            onNavigateToProposals(proposals)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Book Ride", color = Color.White)
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun BookingViewPreview() {
    Surface {
        BookingView(onNavigateToProposals = {})
    }
}