package edu.uic.cs478.s2026.project3app2;

import java.util.ArrayList;
import java.util.List;

/**
 * Static data store for Chicago attractions and restaurants.
 * In a production app this would come from a remote API or database.
 */
public class ChicagoData {

    // ---------------------------------------------------------------
    // Attractions
    // ---------------------------------------------------------------
    public static List<ChicagoPlace> getAttractions() {
        List<ChicagoPlace> list = new ArrayList<>();

        list.add(new ChicagoPlace(
                "Lincoln Park Zoo",
                "One of the oldest zoos in North America — and free admission!",
                "https://www.lpzoo.org"
        ));

        list.add(new ChicagoPlace(
                "Navy Pier",
                "Chicago's most visited landmark — rides, dining, fireworks & more.",
                "https://www.navypier.org"
        ));

        list.add(new ChicagoPlace(
                "Museum of Science and Industry",
                "The largest science museum in the Western Hemisphere.",
                "https://www.msichicago.org"
        ));

        list.add(new ChicagoPlace(
                "The Art Institute of Chicago",
                "World-renowned art museum housing over 300,000 works.",
                "https://www.artic.edu"
        ));

        list.add(new ChicagoPlace(
                "360 CHICAGO (TILT!)",
                "Thrilling observation deck on the 94th floor of 875 N. Michigan Ave.",
                "https://www.360chicago.com"
        ));

        list.add(new ChicagoPlace(
                "Millennium Park",
                "Home to Cloud Gate (\"The Bean\") and the Jay Pritzker Pavilion.",
                "https://www.chicago.gov/city/en/depts/dca/supp_info/millennium_park.html"
        ));

        list.add(new ChicagoPlace(
                "Shedd Aquarium",
                "One of the world's largest indoor aquariums with 32,000+ animals.",
                "https://www.sheddaquarium.org"
        ));

        return list;
    }

    // ---------------------------------------------------------------
    // Restaurants
    // ---------------------------------------------------------------
    public static List<ChicagoPlace> getRestaurants() {
        List<ChicagoPlace> list = new ArrayList<>();

        list.add(new ChicagoPlace(
                "Alinea",
                "Three-Michelin-star molecular gastronomy experience in Lincoln Park.",
                "https://www.alinearestaurant.com"
        ));

        list.add(new ChicagoPlace(
                "Lou Malnati's Pizzeria",
                "Chicago's most iconic deep-dish pizza since 1971.",
                "https://www.loumalnatis.com"
        ));

        list.add(new ChicagoPlace(
                "Girl & the Goat",
                "Stephanie Izard's celebrated West Loop restaurant.",
                "https://www.girlandthegoat.com"
        ));

        list.add(new ChicagoPlace(
                "Portillo's",
                "Famous Chicago-style hot dogs, Italian beef & chocolate cake shake.",
                "https://www.portillos.com"
        ));

        list.add(new ChicagoPlace(
                "RPM Italian",
                "Upscale Italian dining in the heart of River North.",
                "https://www.rpmrestaurants.com/rpm-italian-chicago"
        ));

        list.add(new ChicagoPlace(
                "The Publican",
                "Rustic farmhouse-style restaurant in the West Loop.",
                "https://www.thepublicanrestaurant.com"
        ));

        list.add(new ChicagoPlace(
                "Gibsons Bar & Steakhouse",
                "Chicago's legendary steakhouse on the Gold Coast since 1989.",
                "https://www.gibsonssteakhouse.com"
        ));

        return list;
    }
}
