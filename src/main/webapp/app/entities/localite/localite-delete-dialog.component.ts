import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocalite } from 'app/shared/model/localite.model';
import { LocaliteService } from './localite.service';

@Component({
  templateUrl: './localite-delete-dialog.component.html',
})
export class LocaliteDeleteDialogComponent {
  localite?: ILocalite;

  constructor(protected localiteService: LocaliteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.localiteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('localiteListModification');
      this.activeModal.close();
    });
  }
}
