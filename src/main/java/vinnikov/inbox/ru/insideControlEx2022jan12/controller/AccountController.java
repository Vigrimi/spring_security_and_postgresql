package vinnikov.inbox.ru.insideControlEx2022jan12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import vinnikov.inbox.ru.insideControlEx2022jan12.entity.MessageMyUser;
import vinnikov.inbox.ru.insideControlEx2022jan12.entity.MyUser;
import vinnikov.inbox.ru.insideControlEx2022jan12.repository.Last10MsgsRepository;
import vinnikov.inbox.ru.insideControlEx2022jan12.repository.MessageRepository;
import vinnikov.inbox.ru.insideControlEx2022jan12.repository.RoleRepository;
import vinnikov.inbox.ru.insideControlEx2022jan12.repository.UserRepository;
import vinnikov.inbox.ru.insideControlEx2022jan12.service.MessageService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller // если класс отмечен аннот-й @Controller и метод возвращает тип данных String - return "login"; , то
// это возвращает html файл login.html из папки resources/templates
public class AccountController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MessageRepository messageRepository;
    private PasswordEncoder encoder;
    private MessageService messageService;

    @Autowired
    public AccountController(UserRepository userRepository, RoleRepository roleRepository
            , MessageRepository messageRepository, PasswordEncoder encoder, MessageService messageService)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.messageRepository = messageRepository;
        this.encoder = encoder;
        this.messageService = messageService;
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @GetMapping("/registration") // когда на сайт придёт запрос /registration методом Гет, то Контроллер
    // возвращает хтмл страничку - return "registration"; - файл registration.html
    public String getRegistrationForm(Model model) // тип данных Model используется, чтобы передать полученные
    // данные в хтмл-страничку. Чтобы переданные Моделом данные вывести в хтмл - применяется СпрингФреймворк
    // spring-boot-starter-thymeleaf
    {
        model.addAttribute("title", new MyUser()); // в круглых скобках пара ключ, значение
        return "registration";
    }

    @PostMapping("/registration-handler") // регистрация пользователя, связь с хтмл registration.html
    // form th:action="@{/registration-handler}" method="post"
    public String registrationProcessing(MyUser user, Model model) // User user соберётся логин+пароль из того что
    // введут в хтмл registration.html
    {
        System.out.println(user);
        MyUser fromDB = userRepository.findByLogin(user.getLogin()); // из введённых лог+пароль берём логин и смотрим
        // его в БД и если такой логин там уже есть (значит fromDB будет не НАЛ) - значит надо придумывать новый логин
        System.out.println("--------------fromDB" + fromDB);
        if (fromDB != null)
        {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "registration";
        }

        user.setRole(roleRepository.findByName("ROLE_USER")); // если логин новый, то добавляем роль
        System.out.println("---------------user" + user);
        user.setPassword(encoder.encode(user.getPassword())); // кодируем пароль
        //System.out.println("!!!!!!!!!!!!кодируем пароль:" + user.getPassword() );
        userRepository.save(user); // и потом сохраняем польз-ля с закодированным паролем
        return "redirect:/login"; // перенаправляем на форму входа, после того как регистрация прошла удачно
    }

    @GetMapping("/user/account") // для расширенной работы с пользовательскими сессиями есть
    // пакет session api spring boot
    public String userAccount(Principal principal)
    {
        //System.out.println("----------вернёт имя авторизованного польз-ля");
        System.out.println(principal.getName()); // вернёт имя авторизованного польз-ля

        //System.out.println("-----------пароль???");
        //System.out.println(userRepository.findByLogin("try").getPassword());

        return "account";
    }

    @PostMapping("/message-handler")
    public String messagingProcessing(/*MyUser user, */String message_my_user, Principal principal) // User user соберётся логин+пароль из того что
    // введут в хтмл registration.html
    {
        System.out.println("--/message-handler------principal.getName()->" + principal.getName());
        System.out.println("--/message-handler------message_my_user->" + message_my_user);

        if(message_my_user.equalsIgnoreCase("history 10"))
        {
            // собрать последние 10 сообщений
            long qtyMsgsInDataBase = messageRepository.count();
            System.out.println("---------qtyMsgsInDataBase:" + qtyMsgsInDataBase);
            System.out.println("---------начало сохранить последние 10 сообщений");
            messageService.saveLast10MsgsFm(qtyMsgsInDataBase);
            System.out.println("---------конец сохранить последние 10 сообщений");
            return "redirect:/last-ten-messages";
        }

        else
        {
            MessageMyUser mmy = new MessageMyUser();
            int fromDB_User_id = userRepository.findByLogin(principal.getName()).getUser_id();
            mmy.setUser_id(fromDB_User_id);
            mmy.setMessage_my_user(message_my_user);
            System.out.println("--------mmy->" + mmy);
            messageRepository.save(mmy);
        }

        return "account"; // перенаправляем на форму входа, после того как регистрация прошла удачно
    }

    @GetMapping("/last-ten-messages")
    public String getLastTenMsgs() {
        return "last-ten-messages";
    }
}