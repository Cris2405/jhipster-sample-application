import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVuelo, Vuelo } from 'app/shared/model/vuelo.model';
import { VueloService } from './vuelo.service';
import { VueloComponent } from './vuelo.component';
import { VueloDetailComponent } from './vuelo-detail.component';
import { VueloUpdateComponent } from './vuelo-update.component';

@Injectable({ providedIn: 'root' })
export class VueloResolve implements Resolve<IVuelo> {
  constructor(private service: VueloService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVuelo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vuelo: HttpResponse<Vuelo>) => {
          if (vuelo.body) {
            return of(vuelo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vuelo());
  }
}

export const vueloRoute: Routes = [
  {
    path: '',
    component: VueloComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.vuelo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VueloDetailComponent,
    resolve: {
      vuelo: VueloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.vuelo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VueloUpdateComponent,
    resolve: {
      vuelo: VueloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.vuelo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VueloUpdateComponent,
    resolve: {
      vuelo: VueloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.vuelo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
