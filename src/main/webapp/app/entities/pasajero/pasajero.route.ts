import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPasajero, Pasajero } from 'app/shared/model/pasajero.model';
import { PasajeroService } from './pasajero.service';
import { PasajeroComponent } from './pasajero.component';
import { PasajeroDetailComponent } from './pasajero-detail.component';
import { PasajeroUpdateComponent } from './pasajero-update.component';

@Injectable({ providedIn: 'root' })
export class PasajeroResolve implements Resolve<IPasajero> {
  constructor(private service: PasajeroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPasajero> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pasajero: HttpResponse<Pasajero>) => {
          if (pasajero.body) {
            return of(pasajero.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pasajero());
  }
}

export const pasajeroRoute: Routes = [
  {
    path: '',
    component: PasajeroComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.pasajero.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PasajeroDetailComponent,
    resolve: {
      pasajero: PasajeroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.pasajero.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PasajeroUpdateComponent,
    resolve: {
      pasajero: PasajeroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.pasajero.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PasajeroUpdateComponent,
    resolve: {
      pasajero: PasajeroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.pasajero.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
