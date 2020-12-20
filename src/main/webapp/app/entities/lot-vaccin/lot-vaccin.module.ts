import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JenovTest2SharedModule } from 'app/shared/shared.module';
import { LotVaccinComponent } from './lot-vaccin.component';
import { LotVaccinDetailComponent } from './lot-vaccin-detail.component';
import { LotVaccinUpdateComponent } from './lot-vaccin-update.component';
import { LotVaccinDeleteDialogComponent } from './lot-vaccin-delete-dialog.component';
import { lotVaccinRoute } from './lot-vaccin.route';

@NgModule({
  imports: [JenovTest2SharedModule, RouterModule.forChild(lotVaccinRoute)],
  declarations: [LotVaccinComponent, LotVaccinDetailComponent, LotVaccinUpdateComponent, LotVaccinDeleteDialogComponent],
  entryComponents: [LotVaccinDeleteDialogComponent],
})
export class JenovTest2LotVaccinModule {}
