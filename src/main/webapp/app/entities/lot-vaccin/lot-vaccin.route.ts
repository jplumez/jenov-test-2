import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILotVaccin, LotVaccin } from 'app/shared/model/lot-vaccin.model';
import { LotVaccinService } from './lot-vaccin.service';
import { LotVaccinComponent } from './lot-vaccin.component';
import { LotVaccinDetailComponent } from './lot-vaccin-detail.component';
import { LotVaccinUpdateComponent } from './lot-vaccin-update.component';

@Injectable({ providedIn: 'root' })
export class LotVaccinResolve implements Resolve<ILotVaccin> {
  constructor(private service: LotVaccinService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILotVaccin> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lotVaccin: HttpResponse<LotVaccin>) => {
          if (lotVaccin.body) {
            return of(lotVaccin.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LotVaccin());
  }
}

export const lotVaccinRoute: Routes = [
  {
    path: '',
    component: LotVaccinComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.lotVaccin.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LotVaccinDetailComponent,
    resolve: {
      lotVaccin: LotVaccinResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.lotVaccin.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LotVaccinUpdateComponent,
    resolve: {
      lotVaccin: LotVaccinResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.lotVaccin.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LotVaccinUpdateComponent,
    resolve: {
      lotVaccin: LotVaccinResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.lotVaccin.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
