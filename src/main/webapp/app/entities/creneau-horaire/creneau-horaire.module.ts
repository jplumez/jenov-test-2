import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JenovTest2SharedModule } from 'app/shared/shared.module';
import { CreneauHoraireComponent } from './creneau-horaire.component';
import { CreneauHoraireDetailComponent } from './creneau-horaire-detail.component';
import { CreneauHoraireUpdateComponent } from './creneau-horaire-update.component';
import { CreneauHoraireDeleteDialogComponent } from './creneau-horaire-delete-dialog.component';
import { creneauHoraireRoute } from './creneau-horaire.route';

@NgModule({
  imports: [JenovTest2SharedModule, RouterModule.forChild(creneauHoraireRoute)],
  declarations: [
    CreneauHoraireComponent,
    CreneauHoraireDetailComponent,
    CreneauHoraireUpdateComponent,
    CreneauHoraireDeleteDialogComponent,
  ],
  entryComponents: [CreneauHoraireDeleteDialogComponent],
})
export class JenovTest2CreneauHoraireModule {}
