# Rider App - Cost Estimation

## Overview

This project is a simplified rider application that allows users to book a ride by specifying a pick-up address, a drop-off address, and the number of passengers. The core feature of this application is to calculate and display the estimated cost of the ride before the user requests a booking.

**Note:** This project focuses on the core logic of cost calculation and presentation. The design and layout are not the primary focus here. Developers are free to implement the cost display in a way that best suits their approach, as long as it's clear and functional.

## Features

*   **Ride Booking:** Users can input a pick-up address, a drop-off address, and the number of passengers.
*   **Passenger Types:** The app supports three passenger types:
    *   Adult
    *   Child
    *   Senior
*   **Cost Estimation:** The app calculates the estimated cost based on the number of passengers of each type.
*   **Cost Display:** The estimated cost is displayed to the user before they confirm the ride booking.

## Cost Calculation

The cost of the ride is calculated based on the following rates per passenger type:

*   **Adult:** $10 per adult
*   **Child:** $2 per child
*   **Senior:** $4 per senior

The total cost is the sum of the costs for each passenger type.

**Example:**

If a user enters:

*   2 Adults
*   1 Child
*   1 Senior

The total cost would be calculated as follows:
```
(2 \* $10) + (1 \* $2) + (1 \* $4) = $20 + $2 + $4 = $26
```
## Implementation Details

*   **Address Input:** For this example, the pick-up and drop-off addresses are simple text inputs. There is no address search or validation implemented.
*   **Passenger Count:** Users can specify the number of passengers for each type.
*   **Cost Presentation:** The estimated cost should be displayed above the "Book Ride" button. The exact placement and styling are left to the developer's discretion.
*   **Book Ride Button:** The "Book Ride" button triggers the ride request process. The details of this process are not within the scope of this example.

## Development Notes

*   **Design Flexibility:** The primary focus of this project is the cost calculation logic. Developers have complete freedom in how they choose to present the cost to the user.
*   **No External APIs:** This project does not require any external APIs for address lookup or other services.
*   **Error Handling:** Basic error handling should be implemented to handle invalid inputs (e.g., negative passenger counts).
*   **Future Enhancements:** This project can be extended to include features like:
    *   Address search and validation.
    *   Real-time cost updates based on distance or traffic.
    *   Payment integration.
    *   Ride tracking.

## Getting Started

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Build and run the application.
