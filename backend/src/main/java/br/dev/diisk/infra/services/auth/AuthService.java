package br.dev.diisk.infra.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.auth.IAuthService;
import br.dev.diisk.application.interfaces.auth.ITokenService;
import br.dev.diisk.application.dtos.auth.LoginRequest;
import br.dev.diisk.application.dtos.auth.RegisterRequest;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.entities.user.UserPerfil;
import br.dev.diisk.domain.repositories.user.IUserPerfilRepository;
import br.dev.diisk.domain.repositories.user.IUserRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserPerfilRepository userPerfilRepository;

    @Autowired
    private ITokenService tokenService;

    @Override
    public String login(LoginRequest dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(),
                dto.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return token;
    }

    @Override
    public String renew(User user) {
        String token = tokenService.generateToken(user);
        return token;
    }

    @Override
    @Transactional
    public User register(RegisterRequest dto) {
        String className = RegisterRequest.class.getSimpleName();
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new ValueAlreadyInDatabaseException("email", className);

        UserPerfil defaultUserPerfil = userPerfilRepository.findByName("Default");
        if (defaultUserPerfil == null)
            throw new DbValueNotFoundException("name", className);

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());

        User newUser = new User(dto.getName(), dto.getEmail(), encryptedPassword, defaultUserPerfil);
        userRepository.save(newUser);

        return newUser;
    }

}
