import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JenovTest2SharedModule } from 'app/shared/shared.module';
import { QuestionnaireComponent } from './questionnaire.component';
import { QuestionnaireDetailComponent } from './questionnaire-detail.component';
import { QuestionnaireUpdateComponent } from './questionnaire-update.component';
import { QuestionnaireDeleteDialogComponent } from './questionnaire-delete-dialog.component';
import { questionnaireRoute } from './questionnaire.route';

@NgModule({
  imports: [JenovTest2SharedModule, RouterModule.forChild(questionnaireRoute)],
  declarations: [QuestionnaireComponent, QuestionnaireDetailComponent, QuestionnaireUpdateComponent, QuestionnaireDeleteDialogComponent],
  entryComponents: [QuestionnaireDeleteDialogComponent],
})
export class JenovTest2QuestionnaireModule {}
