package project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.handlerExceptions.UnauthorizationException401;
import project.models.Person;
import project.models.Role;
import project.models.Token;
import project.repositories.PersonRepository;
import project.repositories.TokenRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Данный класс работает с нашим токеном*/
@Slf4j
@Component
public class TokenProvider
{
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PersonRepository personRepository;

    @Value("${jwt.token.secret}")
    private String secret; // секретное слово из application.yml


    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public TokenProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes()); // шифрование токена перед запуском класса
    }

    public String createToken(String email){ //создание токена

        Claims claims = Jwts.claims().setSubject(email); //создаем клайм
        Date now = new Date();
        return Jwts.builder()       // создаем токен
                .setClaims(claims)  // установка клайм
                .setIssuedAt(now)   // установка даты создания
                .signWith(SignatureAlgorithm.HS256, secret) //хэширование секретного кода
                .compact();
    }

    /** Получение аутентификации по токену*/
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /** Получение email по токену*/
    public String getUserEmail(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    /** Получение токена из header запроса*/
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    /** Получение емейла из запроса*/
    public String getEmailByRequest(HttpServletRequest request){
        return getUserEmail(resolveToken(request));
    }

    /** Получение Person по запросу*/
    public Person getPersonByRequest(HttpServletRequest request) throws UnauthorizationException401
    {
        Optional<Person> person = personRepository.findByEmail(getEmailByRequest(request));
        if(person.isPresent()){
            return person.get();
        }
        throw new UnauthorizationException401();
    }

    /** Валидация токена*/
    public boolean validateToken(String token) {
        Optional<Token> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            Token jwtToken = optionalToken.get();
            Calendar date = jwtToken.getDateCreated();
            date.add(Calendar.MONTH, 1);
            if (Calendar.getInstance().before(date)) {
                jwtToken.setDateCreated(Calendar.getInstance());
                tokenRepository.save(jwtToken);
                return true;
            }
            tokenRepository.delete(jwtToken);
            log.info("УДАЛЕН ТОКЕН!");
            return false;
        }
        log.info("Токен НЕ найден в базе");
        return false;
    }

    public List<String> getRoleName(List<Role> personRole){
        List<String> result = new ArrayList<>();

        personRole.forEach(role -> result.add(role.getName()));
        return result;
    }
}
