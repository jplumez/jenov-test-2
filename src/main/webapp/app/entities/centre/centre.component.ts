import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICentre } from 'app/shared/model/centre.model';
import { CentreService } from './centre.service';
import { CentreDeleteDialogComponent } from './centre-delete-dialog.component';

@Component({
  selector: 'jhi-centre',
  templateUrl: './centre.component.html',
})
export class CentreComponent implements OnInit, OnDestroy {
  centres?: ICentre[];
  eventSubscriber?: Subscription;

  constructor(protected centreService: CentreService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.centreService.query().subscribe((res: HttpResponse<ICentre[]>) => (this.centres = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCentres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICentre): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCentres(): void {
    this.eventSubscriber = this.eventManager.subscribe('centreListModification', () => this.loadAll());
  }

  delete(centre: ICentre): void {
    const modalRef = this.modalService.open(CentreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.centre = centre;
  }
}
