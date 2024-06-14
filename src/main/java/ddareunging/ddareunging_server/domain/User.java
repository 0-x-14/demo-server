package ddareunging.ddareunging_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id

    private Long userId;
    private String user_name;
    private String nickname;
    private String email;
    private String profile_image;
    private Integer user_status;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    public static User createUser(String username, String nickname, String email, String profile_image, Integer user_status) {
        User user = new User();
        user.setUser_name(username);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setProfile_image(profile_image);
        user.setUser_status(user_status);
        return user;
    }

}