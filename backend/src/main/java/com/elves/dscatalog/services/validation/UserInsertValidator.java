package com.elves.dscatalog.services.validation;

import com.elves.dscatalog.dto.UserInsertDTO;
import com.elves.dscatalog.exceptions.StandardError;
import com.elves.dscatalog.model.User;
import com.elves.dscatalog.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<StandardError> list = new ArrayList<>();

        User user = repository.findByEmail(dto.getEmail());
        if (user != null) {
            StandardError error = StandardError.builder().timestamp(Instant.now()).error("Email já existe")
                    .status(HttpStatus.UNPROCESSABLE_ENTITY.value()).build();
            list.add(error);
        }

        for (StandardError e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getError()).addPropertyNode(e.getStatus().toString())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
