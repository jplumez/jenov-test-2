import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfessionnelSante } from 'app/shared/model/professionnel-sante.model';

@Component({
  selector: 'jhi-professionnel-sante-detail',
  templateUrl: './professionnel-sante-detail.component.html',
})
export class ProfessionnelSanteDetailComponent implements OnInit {
  professionnelSante: IProfessionnelSante | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ professionnelSante }) => (this.professionnelSante = professionnelSante));
  }

  previousState(): void {
    window.history.back();
  }
}
