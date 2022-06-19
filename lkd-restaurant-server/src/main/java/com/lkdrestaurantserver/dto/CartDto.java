package com.lkdrestaurantserver.dto;

import java.util.Date;
import java.util.List;

public class CartDto {

    private Long id;
    private List<MealDto> mealDtoList;
    private Long userId;

    private Long noPeople;
    private Long noTable;
    private Date date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public List<MealDto> getMealDtoList() {
        return mealDtoList;
    }

    public void setMealDtoList(List<MealDto> mealDtoList) {
        this.mealDtoList = mealDtoList;
    }
}
