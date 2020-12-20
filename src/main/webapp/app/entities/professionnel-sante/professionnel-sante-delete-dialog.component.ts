import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfessionnelSante } from 'app/shared/model/professionnel-sante.model';
import { ProfessionnelSanteService } from './professionnel-sante.service';

@Component({
  templateUrl: './professionnel-sante-delete-dialog.component.html',
})
export class ProfessionnelSanteDeleteDialogComponent {
  professionnelSante?: IProfessionnelSante;

  constructor(
    protected professionnelSanteService: ProfessionnelSanteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.professionnelSanteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('professionnelSanteListModification');
      this.activeModal.close();
    });
  }
}
