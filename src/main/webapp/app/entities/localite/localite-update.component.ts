import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILocalite, Localite } from 'app/shared/model/localite.model';
import { LocaliteService } from './localite.service';

@Component({
  selector: 'jhi-localite-update',
  templateUrl: './localite-update.component.html',
})
export class LocaliteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    npa: [],
    commune: [],
  });

  constructor(protected localiteService: LocaliteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localite }) => {
      this.updateForm(localite);
    });
  }

  updateForm(localite: ILocalite): void {
    this.editForm.patchValue({
      id: localite.id,
      npa: localite.npa,
      commune: localite.commune,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const localite = this.createFromForm();
    if (localite.id !== undefined) {
      this.subscribeToSaveResponse(this.localiteService.update(localite));
    } else {
      this.subscribeToSaveResponse(this.localiteService.create(localite));
    }
  }

  private createFromForm(): ILocalite {
    return {
      ...new Localite(),
      id: this.editForm.get(['id'])!.value,
      npa: this.editForm.get(['npa'])!.value,
      commune: this.editForm.get(['commune'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalite>>): void {
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
