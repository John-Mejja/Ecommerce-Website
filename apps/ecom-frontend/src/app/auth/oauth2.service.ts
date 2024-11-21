import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { CreateQueryResult, injectQuery } from '@tanstack/angular-query-experimental';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { ConnectedUser } from '../shared/model/user.model';
import { firstValueFrom, Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Oauth2Service {

  http = inject(HttpClient);

  oidcSecurityService = inject(OidcSecurityService);

  connectedUserQuery: CreateQueryResult<ConnectedUser> | undefined;

  notConnected = 'NOT_CONNECTED';

  constructor() {
    // Initialize connectedUserQuery by calling fetch method
    this.connectedUserQuery = this.fetch();
  }

  fetch(): CreateQueryResult<ConnectedUser> {
    return injectQuery(() => ({
      queryKey: ['connected-user'],
      queryFn: () => firstValueFrom(this.fetchUserHttp(false)),
    }));
  }

  fetchUserHttp(forceResync: boolean): Observable<ConnectedUser> {
    
    const params = new HttpParams().set('forceResync', forceResync);
    return this.http.get<ConnectedUser>(`${environment.apiUrl}/users/authenticated`, { params})
  }

  login(): void {
    console.log("Kuna shida mahali")
    this.oidcSecurityService.authorize();
  }

  logout(): void {
    this.oidcSecurityService.logoff().subscribe();
  }

  initAuthentication(): void {
    this.oidcSecurityService.checkAuth().subscribe((authinfo) => {
      if (authinfo.isAuthenticated) {

        console.log('connected' + authinfo.accessToken)
      } else {
        console.log('not connected')
      }
    });
  }

  hasAnyAuthorities(connectedUser: ConnectedUser, authorities: Array<string> | string): boolean {
    if (!Array.isArray(authorities)) {
      authorities = [authorities];
    }
    if(connectedUser.authorities) {
      return connectedUser.authorities.some((authority: string) => authorities.includes(authority));
    } else {
      return false;
    }
  }
}
