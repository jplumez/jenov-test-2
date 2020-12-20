import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPatient, Patient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';
import { IVaccination } from 'app/shared/model/vaccination.model';
import { VaccinationService } from 'app/entities/vaccination/vaccination.service';
import { ILocalite } from 'app/shared/model/localite.model';
import { LocaliteService } from 'app/entities/localite/localite.service';

type SelectableEntity = IVaccination | ILocalite;

@Component({
  selector: 'jhi-patient-update',
  templateUrl: './patient-update.component.html',
})
export class PatientUpdateComponent implements OnInit {
  isSaving = false;
  vaccinations: IVaccination[] = [];
  localites: ILocalite[] = [];
  dateNaissanceDp: any;

  editForm = this.fb.group({
    id: [],
    noAvs: [null, [Validators.required]],
    dateNaissance: [],
    nom: [],
    prenom: [],
    sexe: [],
    adresse: [],
    telephone: [],
    email: [],
    numeroAssure: [],
    groupeRisque: [],
    vaccination: [],
    localite: [],
  });

  constructor(
    protected patientService: PatientService,
    protected vaccinationService: VaccinationService,
    protected localiteService: LocaliteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patient }) => {
      this.updateForm(patient);

      this.vaccinationService.query().subscribe((res: HttpResponse<IVaccination[]>) => (this.vaccinations = res.body || []));

      this.localiteService.query().subscribe((res: HttpResponse<ILocalite[]>) => (this.localites = res.body || []));
    });
  }

  updateForm(patient: IPatient): void {
    this.editForm.patchValue({
      id: patient.id,
      noAvs: patient.noAvs,
      dateNaissance: patient.dateNaissance,
      nom: patient.nom,
      prenom: patient.prenom,
      sexe: patient.sexe,
      adresse: patient.adresse,
      telephone: patient.telephone,
      email: patient.email,
      numeroAssure: patient.numeroAssure,
      groupeRisque: patient.groupeRisque,
      vaccination: patient.vaccination,
      localite: patient.localite,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patient = this.createFromForm();
    if (patient.id !== undefined) {
      this.subscribeToSaveResponse(this.patientService.update(patient));
    } else {
      this.subscribeToSaveResponse(this.patientService.create(patient));
    }
  }

  private createFromForm(): IPatient {
    return {
      ...new Patient(),
      id: this.editForm.get(['id'])!.value,
      noAvs: this.editForm.get(['noAvs'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      email: this.editForm.get(['email'])!.value,
      numeroAssure: this.editForm.get(['numeroAssure'])!.value,
      groupeRisque: this.editForm.get(['groupeRisque'])!.value,
      vaccination: this.editForm.get(['vaccination'])!.value,
      localite: this.editForm.get(['localite'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>): void {
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
