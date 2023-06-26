package com.elves.dscatalog.services.validation;

import com.elves.dscatalog.dto.UserUpdateDTO;
import com.elves.dscatalog.exceptions.StandardError;
import com.elves.dscatalog.model.User;
import com.elves.dscatalog.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerMapping;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<StandardError> list = new ArrayList<>();

        User user = repository.findByEmail(dto.getEmail());
        if (user != null && userId != user.getId()) {
            StandardError error = StandardError.builder().timestamp(Instant.now()).error("Email já existe")
                    .status(HttpStatus.UNPROCESSABLE_ENTITY.value()).build();
            list.add(error);
        }

        for (StandardError e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getStatus().toString()).addPropertyNode(e.getError())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
