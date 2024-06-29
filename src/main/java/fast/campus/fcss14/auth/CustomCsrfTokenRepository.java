package fast.campus.fcss14.auth;

import fast.campus.fcss14.repository.TokenJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
        String identifier = request.getHeader("X-IDENTIFIER");

        // 클라이언트 ID 를 기반으로 데이터베이스에서 토큰값을 찾음
        Optional<Token> existingToken = tokenJpaRepository.findTokenByIdentifier(identifier);

        // 클라이언트 ID 가 존재하면 새로 생성된 토큰값으로 업데이트
        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            token.updateToken(csrfToken.getToken());
        } else { // 존재하지 않으면 새로운 레코드 생성
            Token token = new Token(identifier, csrfToken.getToken());
            tokenJpaRepository.save(token);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = tokenJpaRepository.findTokenByIdentifier(identifier);

        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
        }

        return null;
    }
}
