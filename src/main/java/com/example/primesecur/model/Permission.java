package com.example.primesecur.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "t_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseModel implements GrantedAuthority {

    @Column(name = "role")
    private String role;



    @Override
    public String getAuthority() {
        return this.role;
    }
}
