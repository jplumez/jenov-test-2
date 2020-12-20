import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVaccination, Vaccination } from 'app/shared/model/vaccination.model';
import { VaccinationService } from './vaccination.service';
import { VaccinationComponent } from './vaccination.component';
import { VaccinationDetailComponent } from './vaccination-detail.component';
import { VaccinationUpdateComponent } from './vaccination-update.component';

@Injectable({ providedIn: 'root' })
export class VaccinationResolve implements Resolve<IVaccination> {
  constructor(private service: VaccinationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVaccination> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vaccination: HttpResponse<Vaccination>) => {
          if (vaccination.body) {
            return of(vaccination.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vaccination());
  }
}

export const vaccinationRoute: Routes = [
  {
    path: '',
    component: VaccinationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.vaccination.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VaccinationDetailComponent,
    resolve: {
      vaccination: VaccinationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.vaccination.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VaccinationUpdateComponent,
    resolve: {
      vaccination: VaccinationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.vaccination.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VaccinationUpdateComponent,
    resolve: {
      vaccination: VaccinationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.vaccination.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
