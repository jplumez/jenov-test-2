import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVaccination } from 'app/shared/model/vaccination.model';

@Component({
  selector: 'jhi-vaccination-detail',
  templateUrl: './vaccination-detail.component.html',
})
export class VaccinationDetailComponent implements OnInit {
  vaccination: IVaccination | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vaccination }) => (this.vaccination = vaccination));
  }

  previousState(): void {
    window.history.back();
  }
}
