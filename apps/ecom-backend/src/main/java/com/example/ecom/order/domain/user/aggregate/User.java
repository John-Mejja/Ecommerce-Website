package com.example.ecom.order.domain.user.aggregate;

import com.example.ecom.order.domain.user.vo.*;
import com.example.ecom.shared.error.domain.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jilt.Builder;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
public class User {

  private UserLastName userLastName;

  private UserFirstName userFirstName;

  private UserEmail userEmail;

  private UserPublicId userPublicId;

  private UserImageUrl userImageUrl;

  private Set<Authority> authorities;

  private Long dbId;

  private UserAddress userAddress;

  private Instant lastModifiedDate;

  private Instant createdDate;


  private void assertMandatoryFields() {
    Assert.notNull("lastname", userLastName);
    Assert.notNull("firstname", userFirstName);
    Assert.notNull("email", userEmail);
    Assert.notNull("authorities", authorities);
  }

  public void updateFromUser(User user) {
    this.userEmail = user.userEmail;
    this.userImageUrl = user.userImageUrl;
    this.userFirstName = user.userFirstName;
    this.userLastName = user.userLastName;
  }

  public void initFieldForSignup() {
    this.userPublicId = new UserPublicId(UUID.randomUUID());
  }

  public static User fromTokenAttributes(Map<String, Object> attributes, List<String> rolesFromAccessToken){

    UserBuilder userBuilder = UserBuilder.user();

    if (attributes.containsKey("preferred_email")) {
      userBuilder.userEmail(new UserEmail(attributes.get("preferred_email").toString()));
    }

    if (attributes.containsKey("last_name")) {
      userBuilder.userLastName(new UserLastName(attributes.get("last_name").toString()));
    }

    if (attributes.containsKey("first_name")) {
      userBuilder.userFirstName(new UserFirstName(attributes.get("first_name").toString()));
    }

    if (attributes.containsKey("picture")) {
      userBuilder.userImageUrl(new UserImageUrl(attributes.get("picture").toString()));
    }

    if (attributes.containsKey("last_signed_in")) {
      userBuilder.lastModifiedDate(Instant.parse(attributes.get("last_signed_in").toString()));
    }

    Set<Authority> authorities = rolesFromAccessToken
      .stream()
      .map(authority -> AuthorityBuilder.authority().name(new AuthorityName(authority)).build())
      .collect(Collectors.toSet());

    userBuilder.authorities(authorities);

    return userBuilder.build();

  }

}
