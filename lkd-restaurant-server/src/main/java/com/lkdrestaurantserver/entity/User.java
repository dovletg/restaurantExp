package com.lkdrestaurantserver.entity;




import com.lkdrestaurantserver.dto.UserDto;
import com.lkdrestaurantserver.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private UserRole role;

    @Lob
    private byte[] img;

//    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
//    @JsonIgnore
//    private List<Favorites> favoritesList;


    public User() {
    }

    public User(String email, String password, String name, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDto mapUsertoUserDto() {
        return new UserDto(id, email, name, role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

//    public List<Favorites> getFavoritesList() {
//        return favoritesList;
//    }
//
//    public void setFavoritesList(List<Favorites> favoritesList) {
//        this.favoritesList = favoritesList;
//    }
}
