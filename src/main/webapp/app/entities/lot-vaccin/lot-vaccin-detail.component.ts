import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILotVaccin } from 'app/shared/model/lot-vaccin.model';

@Component({
  selector: 'jhi-lot-vaccin-detail',
  templateUrl: './lot-vaccin-detail.component.html',
})
export class LotVaccinDetailComponent implements OnInit {
  lotVaccin: ILotVaccin | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lotVaccin }) => (this.lotVaccin = lotVaccin));
  }

  previousState(): void {
    window.history.back();
  }
}
