package week3.assignment

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> = this.allDrivers.filter{ driver -> this.trips.none{ it.driver == driver} }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> = this.allPassengers
    .associateWith { this.trips.map { trip -> trip.passengers }.count { passList -> it in passList } }
    .filter { (_, value) -> value >= minTrips }
    .keys

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> = this.allPassengers
    .filter { passenger -> this.trips.count{ trip -> trip.driver == driver && passenger in trip.passengers} > 1 }
    .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> = this.allPassengers
    .associateWith { this.trips.filter { trip -> it in trip.passengers }.partition { trip -> trip.discount != null } }
    .filter { (_, value) -> value.first.size > value.second.size }
    .keys

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    // maxBy is deprecated however Coursera does not work with maxByOrNull. Use maxByOrNull
    val (timeRange, _) = this.trips.groupBy { it.duration / 10 }.maxBy { it.value.size } ?: return null

    val rangeStart = (timeRange.toString() + "0").toInt()

    return IntRange(rangeStart, rangeStart + 9);
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if(this.trips.isEmpty()) {
        return false
    }

    val driversAndCost : Map<Driver, Double> = this.allDrivers.associateWith { this.trips.filter { trip -> trip.driver == it }.sumByDouble { trip -> trip.cost } }
    val driversAndCostSortedDescending : List<Pair<Driver, Double>> = driversAndCost.toList().sortedByDescending { (_, v) -> v }

    val totalCost: Double = driversAndCost.values.sum()

    val top20Income: Double = driversAndCostSortedDescending.take((this.allDrivers.size * 0.2).toInt()).stream().mapToDouble { it.second }.sum()

    return top20Income >= totalCost*0.8
}
