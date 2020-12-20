import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICreneauHoraire } from 'app/shared/model/creneau-horaire.model';
import { CreneauHoraireService } from './creneau-horaire.service';

@Component({
  templateUrl: './creneau-horaire-delete-dialog.component.html',
})
export class CreneauHoraireDeleteDialogComponent {
  creneauHoraire?: ICreneauHoraire;

  constructor(
    protected creneauHoraireService: CreneauHoraireService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.creneauHoraireService.delete(id).subscribe(() => {
      this.eventManager.broadcast('creneauHoraireListModification');
      this.activeModal.close();
    });
  }
}
