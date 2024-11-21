package com.example.ecom.order.infrastructure.secondary.entity;

import com.example.ecom.order.domain.user.aggregate.Authority;
import com.example.ecom.order.domain.user.aggregate.AuthorityBuilder;
import com.example.ecom.order.domain.user.vo.AuthorityName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authority")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityEntity implements Serializable {

  @NotNull
  @Size(max = 50)
  @Id
  @Column(length = 50)
  private String name;

  public static Set<AuthorityEntity> from(Set<Authority> authorities) { 
    return authorities.stream()
      .map(authority -> AuthorityEntityBuilder.authorityEntity()
        .name(authority.getAuthorityName().name()).build())
      .collect(Collectors.toSet());
  }

  public static Set<Authority> toDomain(Set<AuthorityEntity> authorityEntities) {
    return authorityEntities.stream()
      .map(authorityEntity -> AuthorityBuilder.authority().name(new AuthorityName(authorityEntity.getName())).build())
      .collect(Collectors.toSet());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthorityEntity that = (AuthorityEntity) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
