import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVaccination, Vaccination } from 'app/shared/model/vaccination.model';
import { VaccinationService } from './vaccination.service';
import { ICreneauHoraire } from 'app/shared/model/creneau-horaire.model';
import { CreneauHoraireService } from 'app/entities/creneau-horaire/creneau-horaire.service';
import { ILotVaccin } from 'app/shared/model/lot-vaccin.model';
import { LotVaccinService } from 'app/entities/lot-vaccin/lot-vaccin.service';
import { IProfessionnelSante } from 'app/shared/model/professionnel-sante.model';
import { ProfessionnelSanteService } from 'app/entities/professionnel-sante/professionnel-sante.service';

type SelectableEntity = ICreneauHoraire | ILotVaccin | IProfessionnelSante;

@Component({
  selector: 'jhi-vaccination-update',
  templateUrl: './vaccination-update.component.html',
})
export class VaccinationUpdateComponent implements OnInit {
  isSaving = false;
  creneauhoraires: ICreneauHoraire[] = [];
  lotvaccins: ILotVaccin[] = [];
  professionnelsantes: IProfessionnelSante[] = [];

  editForm = this.fb.group({
    id: [],
    dateCreation: [],
    dateRendezVous: [],
    dateVaccin: [],
    creneau: [],
    stockVaccin: [],
    executant: [],
  });

  constructor(
    protected vaccinationService: VaccinationService,
    protected creneauHoraireService: CreneauHoraireService,
    protected lotVaccinService: LotVaccinService,
    protected professionnelSanteService: ProfessionnelSanteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vaccination }) => {
      if (!vaccination.id) {
        const today = moment().startOf('day');
        vaccination.dateCreation = today;
        vaccination.dateRendezVous = today;
        vaccination.dateVaccin = today;
      }

      this.updateForm(vaccination);

      this.creneauHoraireService.query().subscribe((res: HttpResponse<ICreneauHoraire[]>) => (this.creneauhoraires = res.body || []));

      this.lotVaccinService.query().subscribe((res: HttpResponse<ILotVaccin[]>) => (this.lotvaccins = res.body || []));

      this.professionnelSanteService
        .query()
        .subscribe((res: HttpResponse<IProfessionnelSante[]>) => (this.professionnelsantes = res.body || []));
    });
  }

  updateForm(vaccination: IVaccination): void {
    this.editForm.patchValue({
      id: vaccination.id,
      dateCreation: vaccination.dateCreation ? vaccination.dateCreation.format(DATE_TIME_FORMAT) : null,
      dateRendezVous: vaccination.dateRendezVous ? vaccination.dateRendezVous.format(DATE_TIME_FORMAT) : null,
      dateVaccin: vaccination.dateVaccin ? vaccination.dateVaccin.format(DATE_TIME_FORMAT) : null,
      creneau: vaccination.creneau,
      stockVaccin: vaccination.stockVaccin,
      executant: vaccination.executant,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vaccination = this.createFromForm();
    if (vaccination.id !== undefined) {
      this.subscribeToSaveResponse(this.vaccinationService.update(vaccination));
    } else {
      this.subscribeToSaveResponse(this.vaccinationService.create(vaccination));
    }
  }

  private createFromForm(): IVaccination {
    return {
      ...new Vaccination(),
      id: this.editForm.get(['id'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value
        ? moment(this.editForm.get(['dateCreation'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateRendezVous: this.editForm.get(['dateRendezVous'])!.value
        ? moment(this.editForm.get(['dateRendezVous'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateVaccin: this.editForm.get(['dateVaccin'])!.value ? moment(this.editForm.get(['dateVaccin'])!.value, DATE_TIME_FORMAT) : undefined,
      creneau: this.editForm.get(['creneau'])!.value,
      stockVaccin: this.editForm.get(['stockVaccin'])!.value,
      executant: this.editForm.get(['executant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVaccination>>): void {
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
