import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICampagne } from 'app/shared/model/campagne.model';
import { CampagneService } from './campagne.service';
import { CampagneDeleteDialogComponent } from './campagne-delete-dialog.component';

@Component({
  selector: 'jhi-campagne',
  templateUrl: './campagne.component.html',
})
export class CampagneComponent implements OnInit, OnDestroy {
  campagnes?: ICampagne[];
  eventSubscriber?: Subscription;

  constructor(protected campagneService: CampagneService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.campagneService.query().subscribe((res: HttpResponse<ICampagne[]>) => (this.campagnes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCampagnes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICampagne): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCampagnes(): void {
    this.eventSubscriber = this.eventManager.subscribe('campagneListModification', () => this.loadAll());
  }

  delete(campagne: ICampagne): void {
    const modalRef = this.modalService.open(CampagneDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.campagne = campagne;
  }
}
