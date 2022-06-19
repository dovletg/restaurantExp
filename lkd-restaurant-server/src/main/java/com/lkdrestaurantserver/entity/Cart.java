package com.lkdrestaurantserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lkdrestaurantserver.dto.CartDto;
import com.lkdrestaurantserver.enums.CartStatus;
import com.lkdrestaurantserver.enums.UserRole;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Meal> meals;

    private CartStatus cartStatus;

    private Long noPeople;
    private Long noTable;
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;


    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CartStatus getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(CartStatus cartStatus) {
        this.cartStatus = cartStatus;
    }

    public Long getNoPeople() {
        return noPeople;
    }

    public void setNoPeople(Long noPeople) {
        this.noPeople = noPeople;
    }

    public Long getNoTable() {
        return noTable;
    }

    public void setNoTable(Long noTable) {
        this.noTable = noTable;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CartDto getDto(){
        CartDto cartDto = new CartDto();
        cartDto.setId(id);
        cartDto.setUserId(user.getId());

        return cartDto;
    }
}
