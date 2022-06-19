package com.lkdrestaurantserver.services.cart;

import com.lkdrestaurantserver.dto.CartDto;
import com.lkdrestaurantserver.dto.MealDto;
import com.lkdrestaurantserver.entity.Cart;
import com.lkdrestaurantserver.entity.Meal;
import com.lkdrestaurantserver.entity.User;
import com.lkdrestaurantserver.enums.CartStatus;
import com.lkdrestaurantserver.repo.CartRepo;
import com.lkdrestaurantserver.repo.MealRepo;
import com.lkdrestaurantserver.repo.UserRepo;
import com.lkdrestaurantserver.responce.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MealRepo mealRepo;

    @Override
    public GeneralResponse addMealInCart(MealDto mealDto, Long userId) {
        GeneralResponse response = new GeneralResponse();
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            Optional<Cart> optionalCart = cartRepo.findByUserAndCartStatus(userOptional.get(), CartStatus.DRAFT);
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();
                boolean itemExists = cart.getMeals().stream().anyMatch(meal -> meal.getMealId().equals(mealDto.getMealId()));
                if (!itemExists) {
                    Meal meal = new Meal();
                    meal.setMealId(mealDto.getMealId());
                    meal.setImg(mealDto.getImg());
                    meal.setQuantity(mealDto.getQuantity());
                    meal.setTitle(mealDto.getTitle());
                    mealRepo.save(meal);

                    if (cart.getMeals().size() == 0) {
                        ArrayList<Meal> arrayList = new ArrayList();
                        arrayList.add(meal);
                        cart.setMeals(arrayList);
                    } else {
                        cart.getMeals().add(meal);
                    }
                    cartRepo.save(cart);
                    response.setMessage("Added in Successfully");
                    response.setStatus(HttpStatus.CREATED);
                } else {
                    response.setMessage("Already Added In Cart");
                    response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                response.setMessage("Cart Not Found");
            }
        } else {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE);
            response.setMessage("User Not Found");
        }
        return response;
    }

    @Override
    public GeneralResponse placeOrder(CartDto cartDto, Long userId) {
        GeneralResponse response = new GeneralResponse();
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            Optional<Cart> optionalCart = cartRepo.findByUserAndCartStatus(userOptional.get(), CartStatus.DRAFT);
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();

                cart.setNoPeople(cartDto.getNoPeople());
                cart.setNoTable(cartDto.getNoTable());
                cart.setDate(cartDto.getDate());
                cart.setCartStatus(CartStatus.ORDERED);

                cartRepo.save(cart);
                createCart(userOptional.get());

                response.setStatus(HttpStatus.CREATED);
                response.setMessage("Order Placed");
            } else {
                response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                response.setMessage("Cart Not Found");
            }
        } else {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE);
            response.setMessage("User Not Found");
        }
        return response;
    }

    @Override
    public void createCart(User user) {
        if (!cartRepo.existsByUserAndCartStatus(user, CartStatus.DRAFT)) {
            Cart cart = new Cart();
            cart.setCartStatus(CartStatus.DRAFT);
            cart.setUser(user);

            cartRepo.save(cart);
        }
    }

    @Override
    public GeneralResponse getCartByUser(Long userId) {
        GeneralResponse response = new GeneralResponse();
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            Optional<Cart> optionalCart = cartRepo.findByUserAndCartStatus(userOptional.get(), CartStatus.DRAFT);
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();

                CartDto cartDto = cart.getDto();
                List<MealDto> mealDtoList = new ArrayList<>();
                cart.getMeals().forEach(meal -> {
                    MealDto mealDto = new MealDto();
                    mealDto.setImg(meal.getImg());
                    mealDto.setMealId(meal.getMealId());
                    mealDto.setQuantity(meal.getQuantity());
                    mealDto.setTitle(meal.getTitle());
                    mealDto.setId(meal.getId());

                    mealDtoList.add(mealDto);
                });
                cartDto.setMealDtoList(mealDtoList);
                response.setStatus(HttpStatus.OK);
                response.setData(cartDto);
            } else {
                response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                response.setMessage("Cart Not Found");
            }
        } else {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE);
            response.setMessage("User Not Found");
        }
        return response;
    }

    @Override
    public GeneralResponse updateMealQuantity(Long idMeal, int quantity) {
        GeneralResponse response = new GeneralResponse();
        Optional<Meal> optionalMeal = mealRepo.findById(idMeal);
        if(optionalMeal.isPresent()){

            optionalMeal.get().setQuantity(quantity);
            mealRepo.save(optionalMeal.get());

            response.setStatus(HttpStatus.OK);
            response.setMessage("Quantity Updated Successfully");
        } else {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE);
            response.setMessage("Meal Not Found");
        }
        return response;
    }

    @Override
    public void deleteMealFromCart(Long id,Long cartId) {
        Optional<Cart> optionalCart = cartRepo.findById(cartId);
        if(optionalCart.isPresent()){
           List<Meal> mealList = optionalCart.get().getMeals().stream().filter(meal -> meal.getId() != id).collect(Collectors.toList());

           optionalCart.get().setMeals(mealList);
           cartRepo.save(optionalCart.get());
        }
    }
}
