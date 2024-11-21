package com.example.ecom.order.infrastructure.secondary.entity;

import com.example.ecom.order.domain.user.aggregate.User;
import com.example.ecom.order.domain.user.aggregate.UserBuilder;
import com.example.ecom.order.domain.user.vo.*;
import com.example.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "ecommerce_user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
  @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "email")
  private String email;

  @Column(name = "image_url" )
  private String imageURL;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "address_street")
  private String addressStreet;

  @Column(name = "address_city")
  private String addressCity;

  @Column(name = "address_zip_code")
  private String addressZipCode;

  @Column(name  = "address_country")
  private String addressCountry;

  @Column(name = "last_seen")
  private Instant lastSeen;

  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(
    name = "user_authority",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
  )
  private Set<AuthorityEntity> authorities = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserEntity that = (UserEntity) o;
    return Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(publicId);
  }

  public void updateFromUser(User user) {
    this.email = user.getUserEmail().value();
    this.lastName = user.getUserLastName().value();
    this.firstName = user.getUserFirstName().value();
    this.imageURL = user.getUserImageUrl().value();
    this.lastSeen = user.getLastModifiedDate();
  }

  public static UserEntity from(User user) {
    UserEntityBuilder userEntityBuilder = UserEntityBuilder.userEntity();

    if (user.getUserImageUrl() != null) {
      userEntityBuilder.imageURL(user.getUserImageUrl().value());
    }

    if (user.getUserPublicId() != null) {
      userEntityBuilder.publicId(user.getUserPublicId().value());
    }

    if (user.getUserAddress() != null) {
      userEntityBuilder.addressCity(user.getUserAddress().city());
      userEntityBuilder.addressCountry(user.getUserAddress().country());
      userEntityBuilder.addressZipCode(user.getUserAddress().zipCode());
      userEntityBuilder.addressStreet(user.getUserAddress().street());
    }

    return userEntityBuilder
      .authorities(AuthorityEntity.from(user.getAuthorities()))
      .email(user.getUserEmail().value())
      .firstName(user.getUserFirstName().value())
      .lastName(user.getUserLastName().value())
      .lastSeen(user.getLastModifiedDate())
      .id(user.getDbId())
      .build();
  }

  public static User toDomain(UserEntity userEntity) {
    UserBuilder userBuilder = UserBuilder.user();

    if(userEntity.getImageURL() != null) {
      userBuilder.userImageUrl(new UserImageUrl(userEntity.getImageURL()));
    }

    if(userEntity.getAddressStreet() != null) {
      userBuilder.userAddress(
        UserAddressBuilder.userAddress()
          .city(userEntity.getAddressCity())
          .country(userEntity.getAddressCountry())
          .zipCode(userEntity.getAddressZipCode())
          .street(userEntity.getAddressStreet())
          .build());
    }

    return userBuilder
      .userEmail(new UserEmail(userEntity.getEmail()))
      .userLastName(new UserLastName(userEntity.getLastName()))
      .userFirstName(new UserFirstName(userEntity.getFirstName()))
      .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
      .userPublicId(new UserPublicId(userEntity.getPublicId()))
      .createdDate(userEntity.getCreatedDate())
      .dbId(userEntity.getId())
      .build();
  }

  public static Set<UserEntity> from(List<User> users) {
    return users.stream()
      .map(UserEntity::from)
      .collect(Collectors.toSet());
  }


  public static Set<User> toDomain(List<UserEntity> users) {
    return users.stream()
      .map(UserEntity::toDomain)
      .collect(Collectors.toSet());
  }
}
