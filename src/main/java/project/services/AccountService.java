package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.requestDto.RegistrationRequestDto;
import project.handlerExceptions.EntityAlreadyExistException;
import project.models.NotificationType;
import project.models.Person;
import project.models.enums.NotificationTypeEnum;
import project.models.enums.RoleEnum;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PersonService personService;

    @Autowired
    private NotificationTypeService notificationTypeService;

    @Autowired
    private PersonNotificationSettingsService personNotificationSettingsService;

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
}
