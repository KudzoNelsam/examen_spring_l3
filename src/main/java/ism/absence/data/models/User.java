package ism.absence.data.models;

import ism.absence.data.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Personne implements UserDetails {
    @Id
    private String id;
    private String username;
    private String password;
    private String photoUrl;
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> role.name());
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}