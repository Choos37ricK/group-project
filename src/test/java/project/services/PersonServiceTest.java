package project.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import project.dto.requestDto.LoginRequestDto;
import project.dto.responseDto.PersonDtoWithToken;
import project.dto.responseDto.ResponseDto;
import project.handlerExceptions.BadRequestException400;
import project.models.Person;
import project.repositories.PersonRepository;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource("/test.properties")
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PasswordEncoder encoder;

    @MockBean
    private PersonRepository personRepository;

    private static final ObjectMapper om = new ObjectMapper();

    @Test
    @SneakyThrows
    public void login() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("ilyxa043@gmail.com", "qweasdzxc");

        Person person = new Person();
        person.setEmail("ilyxa043@gmail.com");
        person.setPassword(encoder.encode(loginRequestDto.getPassword()));
//        personRepository.save(person);

        Mockito.doReturn(Optional.of(person)).when(personRepository).findPersonByEmail(person.getEmail());

        ResponseDto responseDto = personService.login(loginRequestDto);

        PersonDtoWithToken personDto = (PersonDtoWithToken) responseDto.getData();
        //ResponseDto<PersonDtoWithToken> person = new ResponseDto<>();

//        Mockito.verify(personRepository, Mockito.times(1))
//                .findPersonByEmail(loginRequestDto.getEmail());

        Assert.assertEquals(loginRequestDto.getEmail(), personDto.getEmail());
    }

    @Test(expected = BadRequestException400.class)
    public void loginError() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("ilyxa@gmail.com", "qweasdzxc");

        ResponseDto responseDto = personService.login(loginRequestDto);
    }
}