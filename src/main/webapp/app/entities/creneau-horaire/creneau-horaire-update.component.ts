import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICreneauHoraire, CreneauHoraire } from 'app/shared/model/creneau-horaire.model';
import { CreneauHoraireService } from './creneau-horaire.service';
import { ICentre } from 'app/shared/model/centre.model';
import { CentreService } from 'app/entities/centre/centre.service';

@Component({
  selector: 'jhi-creneau-horaire-update',
  templateUrl: './creneau-horaire-update.component.html',
})
export class CreneauHoraireUpdateComponent implements OnInit {
  isSaving = false;
  centres: ICentre[] = [];

  editForm = this.fb.group({
    id: [],
    capacite: [],
    heureDebut: [],
    heureFin: [],
    centre: [],
  });

  constructor(
    protected creneauHoraireService: CreneauHoraireService,
    protected centreService: CentreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ creneauHoraire }) => {
      if (!creneauHoraire.id) {
        const today = moment().startOf('day');
        creneauHoraire.heureDebut = today;
        creneauHoraire.heureFin = today;
      }

      this.updateForm(creneauHoraire);

      this.centreService.query().subscribe((res: HttpResponse<ICentre[]>) => (this.centres = res.body || []));
    });
  }

  updateForm(creneauHoraire: ICreneauHoraire): void {
    this.editForm.patchValue({
      id: creneauHoraire.id,
      capacite: creneauHoraire.capacite,
      heureDebut: creneauHoraire.heureDebut ? creneauHoraire.heureDebut.format(DATE_TIME_FORMAT) : null,
      heureFin: creneauHoraire.heureFin ? creneauHoraire.heureFin.format(DATE_TIME_FORMAT) : null,
      centre: creneauHoraire.centre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const creneauHoraire = this.createFromForm();
    if (creneauHoraire.id !== undefined) {
      this.subscribeToSaveResponse(this.creneauHoraireService.update(creneauHoraire));
    } else {
      this.subscribeToSaveResponse(this.creneauHoraireService.create(creneauHoraire));
    }
  }

  private createFromForm(): ICreneauHoraire {
    return {
      ...new CreneauHoraire(),
      id: this.editForm.get(['id'])!.value,
      capacite: this.editForm.get(['capacite'])!.value,
      heureDebut: this.editForm.get(['heureDebut'])!.value ? moment(this.editForm.get(['heureDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      heureFin: this.editForm.get(['heureFin'])!.value ? moment(this.editForm.get(['heureFin'])!.value, DATE_TIME_FORMAT) : undefined,
      centre: this.editForm.get(['centre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreneauHoraire>>): void {
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
