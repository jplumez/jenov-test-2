import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionnaire, Questionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from './questionnaire.service';
import { ICampagne } from 'app/shared/model/campagne.model';
import { CampagneService } from 'app/entities/campagne/campagne.service';

@Component({
  selector: 'jhi-questionnaire-update',
  templateUrl: './questionnaire-update.component.html',
})
export class QuestionnaireUpdateComponent implements OnInit {
  isSaving = false;
  campagnes: ICampagne[] = [];

  editForm = this.fb.group({
    id: [],
    question: [],
    campagne: [],
  });

  constructor(
    protected questionnaireService: QuestionnaireService,
    protected campagneService: CampagneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionnaire }) => {
      this.updateForm(questionnaire);

      this.campagneService.query().subscribe((res: HttpResponse<ICampagne[]>) => (this.campagnes = res.body || []));
    });
  }

  updateForm(questionnaire: IQuestionnaire): void {
    this.editForm.patchValue({
      id: questionnaire.id,
      question: questionnaire.question,
      campagne: questionnaire.campagne,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionnaire = this.createFromForm();
    if (questionnaire.id !== undefined) {
      this.subscribeToSaveResponse(this.questionnaireService.update(questionnaire));
    } else {
      this.subscribeToSaveResponse(this.questionnaireService.create(questionnaire));
    }
  }

  private createFromForm(): IQuestionnaire {
    return {
      ...new Questionnaire(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      campagne: this.editForm.get(['campagne'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionnaire>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICampagne): any {
    return item.id;
  }
}
