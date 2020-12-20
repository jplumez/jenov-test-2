import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JenovTest2SharedModule } from 'app/shared/shared.module';
import { LocaliteComponent } from './localite.component';
import { LocaliteDetailComponent } from './localite-detail.component';
import { LocaliteUpdateComponent } from './localite-update.component';
import { LocaliteDeleteDialogComponent } from './localite-delete-dialog.component';
import { localiteRoute } from './localite.route';

@NgModule({
  imports: [JenovTest2SharedModule, RouterModule.forChild(localiteRoute)],
  declarations: [LocaliteComponent, LocaliteDetailComponent, LocaliteUpdateComponent, LocaliteDeleteDialogComponent],
  entryComponents: [LocaliteDeleteDialogComponent],
})
export class JenovTest2LocaliteModule {}
