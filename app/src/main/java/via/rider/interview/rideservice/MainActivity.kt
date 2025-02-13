package via.rider.interview.rideservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.type
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.navigation.navArgument
import androidx.navigation.set
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import via.rider.interview.rideservice.bookings.BookingView
import via.rider.interview.rideservice.proposal.Proposal
import via.rider.interview.rideservice.proposal.ProposalsView
import via.rider.interview.rideservice.proposal.ProposalsViewModel
import via.rider.interview.rideservice.ui.theme.RideServiceTheme

class MainActivity : FragmentActivity() {
    // If you want to use Compose, set this to true
    // If you want to use XML, set this to false
    private val isCompose: Boolean = false
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isCompose) {
            setContent {
                MaterialTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        ContentView()
                    }
                }
            }
        } else {
            setContentView(R.layout.activity_main)

            // Get the NavHostFragment from the layout
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            // Get the NavController from the NavHostFragment
            navController = navHostFragment?.navController ?: error("Nav host not found")
        }
    }
}

@Composable
fun ContentView() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = "booking"
    ) {
        composable("booking") {
            BookingView(onNavigateToProposals = { proposals ->
                navigationActions.navigateToProposals(proposals)
            })
        }
        composable(
            route = "proposals/{proposalsJson}",
            arguments = listOf(navArgument("proposalsJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val proposalsJson = backStackEntry.arguments?.getString("proposalsJson")
            val proposals = if (proposalsJson != null) {
                val type = object : TypeToken<List<Proposal>>() {}.type
                Gson().fromJson<List<Proposal>>(proposalsJson, type)
            } else {
                emptyList()
            }
            val route = backStackEntry.savedStateHandle.get<String>("route") ?: ""
            ProposalsView(route = route, proposals)
        }
    }
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateToProposals(proposals: List<Proposal>) {
        val proposalsJson = Gson().toJson(proposals)
        navController.currentBackStackEntry?.savedStateHandle?.set("route", "some route")
        navController.navigate("proposals/$proposalsJson")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        ContentView()
    }
}