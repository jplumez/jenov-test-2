import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICentre, Centre } from 'app/shared/model/centre.model';
import { CentreService } from './centre.service';
import { CentreComponent } from './centre.component';
import { CentreDetailComponent } from './centre-detail.component';
import { CentreUpdateComponent } from './centre-update.component';

@Injectable({ providedIn: 'root' })
export class CentreResolve implements Resolve<ICentre> {
  constructor(private service: CentreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICentre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((centre: HttpResponse<Centre>) => {
          if (centre.body) {
            return of(centre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Centre());
  }
}

export const centreRoute: Routes = [
  {
    path: '',
    component: CentreComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.centre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CentreDetailComponent,
    resolve: {
      centre: CentreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.centre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CentreUpdateComponent,
    resolve: {
      centre: CentreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.centre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CentreUpdateComponent,
    resolve: {
      centre: CentreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.centre.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
