package com.example.ecom.order.domain.user.service;

import com.example.ecom.order.domain.user.aggregate.User;
import com.example.ecom.order.domain.user.repository.UserRepository;
import com.example.ecom.order.domain.user.vo.UserAddressToUpdate;
import com.example.ecom.order.infrastructure.secondary.service.kinde.KindeService;
import com.example.ecom.shared.authentication.application.AuthenticatedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class UserSynchronizer {

  private static final Logger log = LoggerFactory.getLogger(UserSynchronizer.class);
  private String indicator = "log".repeat(10);

  private final UserRepository userRepository;
  private final KindeService kindeService;

  private static final String UPDATE_AT_KEY = "last_signed_in";

  public UserSynchronizer(UserRepository userRepository, KindeService kindeService) {
    this.userRepository = userRepository;
    this.kindeService = kindeService;
  }

  public void syncWithIdp(Jwt jwtToken, boolean forceResync) {
    Map<String, Object> claims = jwtToken.getClaims();
    List<String> rolesFromToken = AuthenticatedUser.extractRolesFromToken(jwtToken);
    Map<String, Object> userInfo = kindeService.getUserInfo(claims.get("sub").toString());
    User user = User.fromTokenAttributes(userInfo, rolesFromToken);
    Optional<User> existingUser = userRepository.getOneByEmail(user.getUserEmail());
      if (existingUser.isPresent()) {
        if (claims.get(UPDATE_AT_KEY) != null) {
          Instant lastModifiedDate = existingUser.get().getLastModifiedDate();
          Instant idpModifiedDate = Instant.ofEpochSecond(((Integer) claims.get(UPDATE_AT_KEY)));
  
          if (idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
            updateUser(user, existingUser.get());
          }
        }
      } else {
        log.info(indicator.concat(" :Saving a new user, " + user.getUserEmail()));
        user.initFieldForSignup();
        userRepository.save(user);
      }
  }

  private void updateUser(User user, User existingUser) {
    existingUser.updateFromUser(user);
    userRepository.save(existingUser);
  }

  public void updateAddress(UserAddressToUpdate userAddressToUpdate) {
    userRepository.updateAddress(userAddressToUpdate.userPublicId(), userAddressToUpdate.userAddress());
  }
}
