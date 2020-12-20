import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILotVaccin } from 'app/shared/model/lot-vaccin.model';
import { LotVaccinService } from './lot-vaccin.service';

@Component({
  templateUrl: './lot-vaccin-delete-dialog.component.html',
})
export class LotVaccinDeleteDialogComponent {
  lotVaccin?: ILotVaccin;

  constructor(protected lotVaccinService: LotVaccinService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lotVaccinService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lotVaccinListModification');
      this.activeModal.close();
    });
  }
}
