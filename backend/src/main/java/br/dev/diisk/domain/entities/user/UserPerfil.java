package br.dev.diisk.domain.entities.user;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.dev.diisk.domain.enums.user.UserPermissionEnum;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_perfils", indexes = {
        @Index(name = "index_id", columnList = "id")
})
public class UserPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer level;

    @ElementCollection(targetClass = UserPermissionEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_perfil_permissions", joinColumns = @JoinColumn(name = "perfil_id", foreignKey = @ForeignKey(name = "FK_user_permission_enum")))
    @Column(name = "permission")
    @Fetch(FetchMode.JOIN)
    private List<UserPermissionEnum> permissions;

}
