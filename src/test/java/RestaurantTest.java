import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void init() {
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai", openingTime, closingTime);
    }

    public void addItemsToMenu() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime mockedTime = LocalTime.parse("11:00:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(mockedTime);
        assertEquals(true, spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime mockedTime = LocalTime.parse("23:00:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(mockedTime);
        assertEquals(false, spiedRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {

        addItemsToMenu();
        int initialMenuSize = restaurant.getMenu().size();

        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        addItemsToMenu();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        addItemsToMenu();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>CALCULATE PRICE FOR THE SELECTED ITEMS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void total_price_of_selected_items_should_match_the_sum_of_individual_item_price() {

        addItemsToMenu();

        ArrayList<String> selectedItems = new ArrayList<>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");

        int totalPrice = restaurant.calculateTotalPriceOfSelectedItems(selectedItems);
        assertEquals(388, totalPrice); // 119 + 269 = 388
    }

    @Test
    public void total_returned_value_should_be_0_when_no_item_is_selected() {

        addItemsToMenu();

        int totalPrice = restaurant.calculateTotalPriceOfSelectedItems(null);
        assertEquals(0, totalPrice);
        totalPrice = restaurant.calculateTotalPriceOfSelectedItems(new ArrayList<>());
        assertEquals(0, totalPrice);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<CALCULATE PRICE FOR THE SELECTED ITEMS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}