import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITiquete, Tiquete } from 'app/shared/model/tiquete.model';
import { TiqueteService } from './tiquete.service';
import { TiqueteComponent } from './tiquete.component';
import { TiqueteDetailComponent } from './tiquete-detail.component';
import { TiqueteUpdateComponent } from './tiquete-update.component';

@Injectable({ providedIn: 'root' })
export class TiqueteResolve implements Resolve<ITiquete> {
  constructor(private service: TiqueteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITiquete> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tiquete: HttpResponse<Tiquete>) => {
          if (tiquete.body) {
            return of(tiquete.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tiquete());
  }
}

export const tiqueteRoute: Routes = [
  {
    path: '',
    component: TiqueteComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.tiquete.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TiqueteDetailComponent,
    resolve: {
      tiquete: TiqueteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.tiquete.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TiqueteUpdateComponent,
    resolve: {
      tiquete: TiqueteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.tiquete.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TiqueteUpdateComponent,
    resolve: {
      tiquete: TiqueteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.tiquete.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
