package fast.campus.fcss14.auth;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String identifier; // 클라이언트의 식별자

    @Column
    private String token; // 애플리케이션이 클라이언트를 위해 생성한 CSRF 토큰값

    public Token(String identifier, String token) {
        this.identifier = identifier;
        this.token = token;
    }

    public Token() {

    }

    public void updateToken(String token) {
        this.token = token;
    }
}
