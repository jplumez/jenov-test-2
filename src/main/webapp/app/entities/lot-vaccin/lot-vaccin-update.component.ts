import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILotVaccin, LotVaccin } from 'app/shared/model/lot-vaccin.model';
import { LotVaccinService } from './lot-vaccin.service';
import { ICentre } from 'app/shared/model/centre.model';
import { CentreService } from 'app/entities/centre/centre.service';

@Component({
  selector: 'jhi-lot-vaccin-update',
  templateUrl: './lot-vaccin-update.component.html',
})
export class LotVaccinUpdateComponent implements OnInit {
  isSaving = false;
  centres: ICentre[] = [];

  editForm = this.fb.group({
    id: [],
    stockInitial: [],
    stockActuel: [],
    heureInventaire: [],
    centre: [],
  });

  constructor(
    protected lotVaccinService: LotVaccinService,
    protected centreService: CentreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lotVaccin }) => {
      if (!lotVaccin.id) {
        const today = moment().startOf('day');
        lotVaccin.heureInventaire = today;
      }

      this.updateForm(lotVaccin);

      this.centreService.query().subscribe((res: HttpResponse<ICentre[]>) => (this.centres = res.body || []));
    });
  }

  updateForm(lotVaccin: ILotVaccin): void {
    this.editForm.patchValue({
      id: lotVaccin.id,
      stockInitial: lotVaccin.stockInitial,
      stockActuel: lotVaccin.stockActuel,
      heureInventaire: lotVaccin.heureInventaire ? lotVaccin.heureInventaire.format(DATE_TIME_FORMAT) : null,
      centre: lotVaccin.centre,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lotVaccin = this.createFromForm();
    if (lotVaccin.id !== undefined) {
      this.subscribeToSaveResponse(this.lotVaccinService.update(lotVaccin));
    } else {
      this.subscribeToSaveResponse(this.lotVaccinService.create(lotVaccin));
    }
  }

  private createFromForm(): ILotVaccin {
    return {
      ...new LotVaccin(),
      id: this.editForm.get(['id'])!.value,
      stockInitial: this.editForm.get(['stockInitial'])!.value,
      stockActuel: this.editForm.get(['stockActuel'])!.value,
      heureInventaire: this.editForm.get(['heureInventaire'])!.value
        ? moment(this.editForm.get(['heureInventaire'])!.value, DATE_TIME_FORMAT)
        : undefined,
      centre: this.editForm.get(['centre'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILotVaccin>>): void {
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
