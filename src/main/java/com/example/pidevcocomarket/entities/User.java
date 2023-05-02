package com.example.pidevcocomarket.entities;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class User implements java.io.Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String company;
    private String address;
    @Enumerated(EnumType.STRING)
    private StatusUser statusUser = StatusUser.ACTIVE;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    @Lob
    @Column(columnDefinition = "BLOB")
    private String logo;
    private Integer fidelity;
    private int code;
    private boolean isSubscribed;

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<ChatBox> chatBoxes;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="vendor")
    @JsonIgnore
    @ToString.Exclude
    private Set<Contract> contrats;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="user")
    @JsonIgnore
    @ToString.Exclude
    private Set<Boutique> boutiques;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="livreur")
    @JsonIgnore
    @ToString.Exclude
    private Set<Livraison> livraisons;

    //SPRING SECURITY//
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(role.name()));
        return list;
    }

    @Override
    @JsonIgnore

    public String getUsername() {return email;}
    @Override
    public String getPassword() {
        return password;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {return true;}
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {return true;}
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {return true;}
    @JsonIgnore
    @Override
    public boolean isEnabled() {return true;}


}
