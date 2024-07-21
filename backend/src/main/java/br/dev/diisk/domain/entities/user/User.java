package br.dev.diisk.domain.entities.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.dev.diisk.domain.entities.RastreableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users", indexes = {
        @Index(name = "index_register_id", columnList = "register_id"),
        @Index(name = "idnex_perfil_id", columnList = "perfil_id"),
})
@NoArgsConstructor
public class User extends RastreableEntity implements UserDetails {

    private String name;
    private String email;
    private String password;

    @ManyToOne(optional = true)
    @JoinColumn(name = "register_id", foreignKey = @ForeignKey(name = "FK_users_register_id"))
    private User register;

    @ManyToOne
    @JoinColumn(name = "perfil_id", foreignKey = @ForeignKey(name = "FK_users_perfil_id"))
    private UserPerfil perfil;

    public User(String name, String email, String password, UserPerfil perfil, User register) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.perfil = perfil;
        this.register = register;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfil.getPermissions();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
