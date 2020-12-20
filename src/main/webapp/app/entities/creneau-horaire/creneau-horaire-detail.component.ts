import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreneauHoraire } from 'app/shared/model/creneau-horaire.model';

@Component({
  selector: 'jhi-creneau-horaire-detail',
  templateUrl: './creneau-horaire-detail.component.html',
})
export class CreneauHoraireDetailComponent implements OnInit {
  creneauHoraire: ICreneauHoraire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ creneauHoraire }) => (this.creneauHoraire = creneauHoraire));
  }

  previousState(): void {
    window.history.back();
  }
}
