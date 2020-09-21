import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvion, Avion } from 'app/shared/model/avion.model';
import { AvionService } from './avion.service';
import { AvionComponent } from './avion.component';
import { AvionDetailComponent } from './avion-detail.component';
import { AvionUpdateComponent } from './avion-update.component';

@Injectable({ providedIn: 'root' })
export class AvionResolve implements Resolve<IAvion> {
  constructor(private service: AvionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((avion: HttpResponse<Avion>) => {
          if (avion.body) {
            return of(avion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Avion());
  }
}

export const avionRoute: Routes = [
  {
    path: '',
    component: AvionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.avion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvionDetailComponent,
    resolve: {
      avion: AvionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.avion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvionUpdateComponent,
    resolve: {
      avion: AvionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.avion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvionUpdateComponent,
    resolve: {
      avion: AvionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'firstApp.avion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
