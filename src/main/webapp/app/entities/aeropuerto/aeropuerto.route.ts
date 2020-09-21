import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAeropuerto, Aeropuerto } from 'app/shared/model/aeropuerto.model';
import { AeropuertoService } from './aeropuerto.service';
import { AeropuertoComponent } from './aeropuerto.component';
import { AeropuertoDetailComponent } from './aeropuerto-detail.component';
import { AeropuertoUpdateComponent } from './aeropuerto-update.component';

@Injectable({ providedIn: 'root' })
export class AeropuertoResolve implements Resolve<IAeropuerto> {
  constructor(private service: AeropuertoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAeropuerto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aeropuerto: HttpResponse<Aeropuerto>) => {
          if (aeropuerto.body) {
            return of(aeropuerto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Aeropuerto());
  }
}

export const aeropuertoRoute: Routes = [
  {
    path: '',
    component: AeropuertoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.aeropuerto.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AeropuertoDetailComponent,
    resolve: {
      aeropuerto: AeropuertoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.aeropuerto.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AeropuertoUpdateComponent,
    resolve: {
      aeropuerto: AeropuertoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.aeropuerto.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AeropuertoUpdateComponent,
    resolve: {
      aeropuerto: AeropuertoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.aeropuerto.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
