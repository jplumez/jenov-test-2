import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVaccination } from 'app/shared/model/vaccination.model';
import { VaccinationService } from './vaccination.service';

@Component({
  templateUrl: './vaccination-delete-dialog.component.html',
})
export class VaccinationDeleteDialogComponent {
  vaccination?: IVaccination;

  constructor(
    protected vaccinationService: VaccinationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vaccinationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vaccinationListModification');
      this.activeModal.close();
    });
  }
}
