package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.requestDto.RegistrationRequestDto;
import project.handlerExceptions.EntityAlreadyExistException;
import project.handlerExceptions.EntityNotFoundException;
import project.models.NotificationType;
import project.models.Person;
import project.models.VerificationToken;
import project.models.enums.NotificationTypeEnum;
import project.models.enums.RoleEnum;
import project.services.email.EmailService;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Value("${response.host}")
    private String host;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PersonService personService;

    @Autowired
    private NotificationTypeService notificationTypeService;

    @Autowired
    private PersonNotificationSettingsService personNotificationSettingsService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void register(RegistrationRequestDto dto) throws EntityAlreadyExistException {
        Person person = personService.add(dto, roleService.find(RoleEnum.ROLE_USER));
        List<NotificationType> types = notificationTypeService.findByCode(
            NotificationTypeEnum.POST_COMMENT,
            NotificationTypeEnum.COMMENT_COMMENT,
            NotificationTypeEnum.FRIEND_REQUEST,
            NotificationTypeEnum.MESSAGE,
            NotificationTypeEnum.FRIEND_BIRTHDAY
        );
        personNotificationSettingsService.add(person, false, types);
    }

    @Transactional
    public void recoverPassword(String email) throws EntityNotFoundException {
        Person person = personService.findPersonByEmail(email).orElseThrow(
            () -> new EntityNotFoundException("Can't find person with email: " + email)
        );

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, person.getId(), 20);
        verificationTokenService.save(verificationToken);

        String link = "http://" + host + "/change-password?token=" + token;
        String message = String.format("Для восстановления пароля перейдите по ссылке %s", link );
        emailService.send(email, "Password recovery", message);
    }
}