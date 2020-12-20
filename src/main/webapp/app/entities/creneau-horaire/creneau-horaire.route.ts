import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICreneauHoraire, CreneauHoraire } from 'app/shared/model/creneau-horaire.model';
import { CreneauHoraireService } from './creneau-horaire.service';
import { CreneauHoraireComponent } from './creneau-horaire.component';
import { CreneauHoraireDetailComponent } from './creneau-horaire-detail.component';
import { CreneauHoraireUpdateComponent } from './creneau-horaire-update.component';

@Injectable({ providedIn: 'root' })
export class CreneauHoraireResolve implements Resolve<ICreneauHoraire> {
  constructor(private service: CreneauHoraireService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICreneauHoraire> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((creneauHoraire: HttpResponse<CreneauHoraire>) => {
          if (creneauHoraire.body) {
            return of(creneauHoraire.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CreneauHoraire());
  }
}

export const creneauHoraireRoute: Routes = [
  {
    path: '',
    component: CreneauHoraireComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.creneauHoraire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CreneauHoraireDetailComponent,
    resolve: {
      creneauHoraire: CreneauHoraireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.creneauHoraire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CreneauHoraireUpdateComponent,
    resolve: {
      creneauHoraire: CreneauHoraireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.creneauHoraire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CreneauHoraireUpdateComponent,
    resolve: {
      creneauHoraire: CreneauHoraireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jenovTest2App.creneauHoraire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
