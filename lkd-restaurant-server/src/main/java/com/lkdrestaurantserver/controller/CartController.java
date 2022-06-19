package com.lkdrestaurantserver.controller;

import com.lkdrestaurantserver.dto.CartDto;
import com.lkdrestaurantserver.dto.MealDto;
import com.lkdrestaurantserver.responce.GeneralResponse;
import com.lkdrestaurantserver.services.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("{userId}")
    public GeneralResponse addItemInCart(@RequestBody MealDto mealDto, @PathVariable Long userId) {
        GeneralResponse response = new GeneralResponse();
        try {
            return cartService.addMealInCart(mealDto,userId);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }

    @GetMapping("{userId}")
    public GeneralResponse getCartByUser(@PathVariable Long userId) {
        GeneralResponse response = new GeneralResponse();
        try {
             return cartService.getCartByUser(userId);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }

    @PostMapping("place/{userId}")
    public GeneralResponse placeOrder(@RequestBody CartDto cartDto, @PathVariable Long userId) {
        GeneralResponse response = new GeneralResponse();
        try {
            return cartService.placeOrder(cartDto,userId);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }

    @GetMapping("updateQuantity/{idMeal}/{quantity}")
    public GeneralResponse updateQuantity(@PathVariable Long idMeal,@PathVariable int quantity) {
        GeneralResponse response = new GeneralResponse();
        try {
            return cartService.updateMealQuantity(idMeal,quantity);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }

    @DeleteMapping("{id}/{cartId}")
    public GeneralResponse deleteMealFromCart(@PathVariable Long id, @PathVariable Long cartId) {
        GeneralResponse response = new GeneralResponse();
        try {
            cartService.deleteMealFromCart(id,cartId);
            response.setStatus(HttpStatus.OK);
            return response;
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }
}
