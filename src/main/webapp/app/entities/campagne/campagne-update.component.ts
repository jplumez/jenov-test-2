import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICampagne, Campagne } from 'app/shared/model/campagne.model';
import { CampagneService } from './campagne.service';
import { ICentre } from 'app/shared/model/centre.model';
import { CentreService } from 'app/entities/centre/centre.service';

@Component({
  selector: 'jhi-campagne-update',
  templateUrl: './campagne-update.component.html',
})
export class CampagneUpdateComponent implements OnInit {
  isSaving = false;
  centres: ICentre[] = [];
  debutDp: any;
  finDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [],
    description: [],
    debut: [],
    fin: [],
    nbJoursRappel: [],
    remarques: [],
    centre: [],
  });

  constructor(
    protected campagneService: CampagneService,
    protected centreService: CentreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campagne }) => {
      this.updateForm(campagne);

      this.centreService.query().subscribe((res: HttpResponse<ICentre[]>) => (this.centres = res.body || []));
    });
  }

  updateForm(campagne: ICampagne): void {
    this.editForm.patchValue({
      id: campagne.id,
      nom: campagne.nom,
      description: campagne.description,
      debut: campagne.debut,
      fin: campagne.fin,
      nbJoursRappel: campagne.nbJoursRappel,
      remarques: campagne.remarques,
      centre: campagne.centre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const campagne = this.createFromForm();
    if (campagne.id !== undefined) {
      this.subscribeToSaveResponse(this.campagneService.update(campagne));
    } else {
      this.subscribeToSaveResponse(this.campagneService.create(campagne));
    }
  }

  private createFromForm(): ICampagne {
    return {
      ...new Campagne(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      debut: this.editForm.get(['debut'])!.value,
      fin: this.editForm.get(['fin'])!.value,
      nbJoursRappel: this.editForm.get(['nbJoursRappel'])!.value,
      remarques: this.editForm.get(['remarques'])!.value,
      centre: this.editForm.get(['centre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICampagne>>): void {
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

  trackById(index: number, item: ICentre): any {
    return item.id;
  }
}
