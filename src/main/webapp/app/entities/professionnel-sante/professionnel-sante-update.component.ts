import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProfessionnelSante, ProfessionnelSante } from 'app/shared/model/professionnel-sante.model';
import { ProfessionnelSanteService } from './professionnel-sante.service';
import { ILocalite } from 'app/shared/model/localite.model';
import { LocaliteService } from 'app/entities/localite/localite.service';
import { ICentre } from 'app/shared/model/centre.model';
import { CentreService } from 'app/entities/centre/centre.service';

type SelectableEntity = ILocalite | ICentre;

@Component({
  selector: 'jhi-professionnel-sante-update',
  templateUrl: './professionnel-sante-update.component.html',
})
export class ProfessionnelSanteUpdateComponent implements OnInit {
  isSaving = false;
  localites: ILocalite[] = [];
  centres: ICentre[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    localite: [],
    centre: [],
  });

  constructor(
    protected professionnelSanteService: ProfessionnelSanteService,
    protected localiteService: LocaliteService,
    protected centreService: CentreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ professionnelSante }) => {
      this.updateForm(professionnelSante);

      this.localiteService.query().subscribe((res: HttpResponse<ILocalite[]>) => (this.localites = res.body || []));

      this.centreService.query().subscribe((res: HttpResponse<ICentre[]>) => (this.centres = res.body || []));
    });
  }

  updateForm(professionnelSante: IProfessionnelSante): void {
    this.editForm.patchValue({
      id: professionnelSante.id,
      nom: professionnelSante.nom,
      localite: professionnelSante.localite,
      centre: professionnelSante.centre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const professionnelSante = this.createFromForm();
    if (professionnelSante.id !== undefined) {
      this.subscribeToSaveResponse(this.professionnelSanteService.update(professionnelSante));
    } else {
      this.subscribeToSaveResponse(this.professionnelSanteService.create(professionnelSante));
    }
  }

  private createFromForm(): IProfessionnelSante {
    return {
      ...new ProfessionnelSante(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      localite: this.editForm.get(['localite'])!.value,
      centre: this.editForm.get(['centre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessionnelSante>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
