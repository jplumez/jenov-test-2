import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICentre, Centre } from 'app/shared/model/centre.model';
import { CentreService } from './centre.service';

@Component({
  selector: 'jhi-centre-update',
  templateUrl: './centre-update.component.html',
})
export class CentreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    localite: [null, [Validators.required]],
    description: [],
  });

  constructor(protected centreService: CentreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centre }) => {
      this.updateForm(centre);
    });
  }

  updateForm(centre: ICentre): void {
    this.editForm.patchValue({
      id: centre.id,
      code: centre.code,
      localite: centre.localite,
      description: centre.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centre = this.createFromForm();
    if (centre.id !== undefined) {
      this.subscribeToSaveResponse(this.centreService.update(centre));
    } else {
      this.subscribeToSaveResponse(this.centreService.create(centre));
    }
  }

  private createFromForm(): ICentre {
    return {
      ...new Centre(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      localite: this.editForm.get(['localite'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentre>>): void {
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
}
