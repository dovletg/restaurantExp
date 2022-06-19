package com.lkdrestaurantserver.services.cart;



import com.lkdrestaurantserver.dto.CartDto;
import com.lkdrestaurantserver.dto.MealDto;
import com.lkdrestaurantserver.entity.User;
import com.lkdrestaurantserver.responce.GeneralResponse;

import java.util.List;

public interface CartService {

    void createCart(User user);

    GeneralResponse getCartByUser(Long userId);

    void deleteMealFromCart(Long id,Long cartId);

    GeneralResponse addMealInCart(MealDto mealDto, Long userId);

    GeneralResponse placeOrder(CartDto cartDto, Long userId);

    GeneralResponse updateMealQuantity(Long idMeal, int quantity);
}
