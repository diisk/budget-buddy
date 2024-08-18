package br.dev.diisk.application.cases.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.auth.RegisterRequest;
import br.dev.diisk.application.dtos.fund_storage.AddFundStorageRequest;
import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.exceptions.persistence.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.auth.IAuthRegisterCase;
import br.dev.diisk.application.interfaces.fund_storage.IAddFundStorageCase;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.entities.user.UserPerfil;
import br.dev.diisk.domain.repositories.user.IUserPerfilRepository;
import br.dev.diisk.domain.repositories.user.IUserRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthRegisterCase implements IAuthRegisterCase {

    private final IUserRepository userRepository;
    private final IUserPerfilRepository userPerfilRepository;
    private final IAddFundStorageCase addFundStorageCase;

    public AuthRegisterCase(IUserRepository userRepository, IUserPerfilRepository userPerfilRepository,
            IAddFundStorageCase addFundStorageCase) {
        this.userRepository = userRepository;
        this.userPerfilRepository = userPerfilRepository;
        this.addFundStorageCase = addFundStorageCase;
    }

    @Override
    @Transactional
    public User execute(RegisterRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new ValueAlreadyInDatabaseException(User.class, "email");

        UserPerfil defaultUserPerfil = userPerfilRepository.findByName("Default");
        if (defaultUserPerfil == null)
            throw new DbValueNotFoundException(UserPerfil.class, "name");

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());

        User newUser = new User(dto.getName(), dto.getEmail(), encryptedPassword, defaultUserPerfil);
        userRepository.save(newUser);
        addFundStorageCase.execute(
                new AddFundStorageRequest("Conta corrente", false),
                newUser);
        return newUser;
    }

}
