import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILocalite, Localite } from 'app/shared/model/localite.model';
import { LocaliteService } from './localite.service';
import { LocaliteComponent } from './localite.component';
import { LocaliteDetailComponent } from './localite-detail.component';
import { LocaliteUpdateComponent } from './localite-update.component';

@Injectable({ providedIn: 'root' })
export class LocaliteResolve implements Resolve<ILocalite> {
  constructor(private service: LocaliteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocalite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((localite: HttpResponse<Localite>) => {
          if (localite.body) {
            return of(localite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Localite());
  }
}

export const localiteRoute: Routes = [
  {
    path: '',
    component: LocaliteComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.localite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LocaliteDetailComponent,
    resolve: {
      localite: LocaliteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.localite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LocaliteUpdateComponent,
    resolve: {
      localite: LocaliteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.localite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LocaliteUpdateComponent,
    resolve: {
      localite: LocaliteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.localite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
