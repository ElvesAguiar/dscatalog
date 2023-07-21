package com.elves.dscatalog.services;

import com.elves.dscatalog.dto.EmailDTO;
import com.elves.dscatalog.dto.NewPasswordDTO;
import com.elves.dscatalog.model.PasswordRecover;
import com.elves.dscatalog.model.User;
import com.elves.dscatalog.repositories.PasswordRecoverRepository;
import com.elves.dscatalog.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverURI;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;

    public void createRecoverToken(EmailDTO body) {

        User user = userRepository.findByEmail(body.getEmail());
        if (user == null) {
            throw new EntityNotFoundException("Email não encontrado");
        }
        String token = UUID.randomUUID().toString();

        PasswordRecover entity = new PasswordRecover();
        entity.setEmail(body.getEmail());
        entity.setToken(token);
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));

        entity = passwordRecoverRepository.save(entity);

        String text = "Acesse o link para definição de senha \n \n" +
                      recoverURI + token + ". Validade de " + tokenMinutes + " minutos";

        emailService.sendEmail(body.getEmail(), "recuperação de senha", text);
    }

    public void saveNewPassword(NewPasswordDTO body) {
        List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());
        if (result.isEmpty()) throw new EntityNotFoundException("token invalido");
        User user = userRepository.findByEmail(result.get(0).getEmail());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user = userRepository.save(user);
    }
}
