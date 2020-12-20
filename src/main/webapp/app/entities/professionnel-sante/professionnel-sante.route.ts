import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProfessionnelSante, ProfessionnelSante } from 'app/shared/model/professionnel-sante.model';
import { ProfessionnelSanteService } from './professionnel-sante.service';
import { ProfessionnelSanteComponent } from './professionnel-sante.component';
import { ProfessionnelSanteDetailComponent } from './professionnel-sante-detail.component';
import { ProfessionnelSanteUpdateComponent } from './professionnel-sante-update.component';

@Injectable({ providedIn: 'root' })
export class ProfessionnelSanteResolve implements Resolve<IProfessionnelSante> {
  constructor(private service: ProfessionnelSanteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfessionnelSante> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((professionnelSante: HttpResponse<ProfessionnelSante>) => {
          if (professionnelSante.body) {
            return of(professionnelSante.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProfessionnelSante());
  }
}

export const professionnelSanteRoute: Routes = [
  {
    path: '',
    component: ProfessionnelSanteComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.professionnelSante.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProfessionnelSanteDetailComponent,
    resolve: {
      professionnelSante: ProfessionnelSanteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.professionnelSante.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProfessionnelSanteUpdateComponent,
    resolve: {
      professionnelSante: ProfessionnelSanteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.professionnelSante.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProfessionnelSanteUpdateComponent,
    resolve: {
      professionnelSante: ProfessionnelSanteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.professionnelSante.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
