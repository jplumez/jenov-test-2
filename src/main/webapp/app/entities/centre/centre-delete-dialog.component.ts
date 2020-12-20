import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICentre } from 'app/shared/model/centre.model';
import { CentreService } from './centre.service';

@Component({
  templateUrl: './centre-delete-dialog.component.html',
})
export class CentreDeleteDialogComponent {
  centre?: ICentre;

  constructor(protected centreService: CentreService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.centreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('centreListModification');
      this.activeModal.close();
    });
  }
}
