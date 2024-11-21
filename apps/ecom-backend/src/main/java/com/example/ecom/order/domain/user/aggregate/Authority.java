package com.example.ecom.order.domain.user.aggregate;

import com.example.ecom.order.domain.user.vo.AuthorityName;
import com.example.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public class Authority {

  private AuthorityName name;

  public Authority(AuthorityName authorityName) {
    Assert.notNull("name",authorityName);
    this.name = authorityName;
  }

  public AuthorityName getAuthorityName() {
    return name;
  }
}
