package br.dev.diisk.infra.configs.seed;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.dev.diisk.domain.entities.user.UserPerfil;
import br.dev.diisk.domain.enums.user.UserPermissionEnum;
import br.dev.diisk.domain.repositories.user.IUserPerfilRepository;
import jakarta.transaction.Transactional;

@Component
public class PerfilSeed implements ApplicationRunner {

    @Autowired
    private IUserPerfilRepository userPerfilRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        createPerfil("DEFAULT", 0, getDefaultPerfil());
    }

    private Boolean isEqualsPerfilPermissions(UserPerfil perfil, List<UserPermissionEnum> permissions) {
        if (perfil.getPermissions().size() != permissions.size())
            return false;
        for (UserPermissionEnum perm : permissions) {
            if (perfil.getPermissions().stream().noneMatch(perfilPerm -> perfilPerm == perm))
                return false;
        }

        return true;
    }

    private List<UserPermissionEnum> getDefaultPerfil() {
        List<UserPermissionEnum> permissions = new ArrayList<>();
        permissions.add(UserPermissionEnum.RENEW_TOKEN);
        return permissions;
    }

    private void createPerfil(String perfilName, Integer level, List<UserPermissionEnum> permissions) {
        UserPerfil clientePerfil = userPerfilRepository.findByName(perfilName);

        if (clientePerfil == null) {
            userPerfilRepository.save(new UserPerfil(null, perfilName, level, permissions));
        } else {
            if (clientePerfil.getLevel() != level || !isEqualsPerfilPermissions(clientePerfil, permissions)) {
                clientePerfil.setPermissions(permissions);
                clientePerfil.setLevel(level);
                userPerfilRepository.save(clientePerfil);
            }

        }

    }

}
