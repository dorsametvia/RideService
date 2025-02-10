package via.rider.interview.rideservice.proposal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProposalsView(route: String, proposals: List<Proposal>, viewModel: ProposalsViewModel = viewModel<ProposalsViewModel>().apply {
    setProposals(proposals)
}) {
    val proposals by viewModel.proposals.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Available Rides for $route",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(proposals.sortedBy { it.pickupTime }) { ride ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formatTime(ride.pickupTime),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = ride.price,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

// Helper function to format the time
fun formatTime(date: Date): String {
    val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    return formatter.format(date)
}

// Preview
@Preview(showBackground = true)
@Composable
fun ProposalsViewPreview() {
    val previewProposals = listOf(
        Proposal(
            pickupTime = Date(System.currentTimeMillis() + 60 * 5 * 1000),
            price = "$20"
        ),
        Proposal(
            pickupTime = Date(System.currentTimeMillis() + 60 * 10 * 1000),
            price = "$20"
        ),
        Proposal(
            pickupTime = Date(System.currentTimeMillis() + 60 * 15 * 1000),
            price = "$20"
        )
    )

    val viewModel = ProposalsViewModel()
    viewModel.setProposals(previewProposals)

    ProposalsView(route = "Home to Work", previewProposals, viewModel = viewModel)
}