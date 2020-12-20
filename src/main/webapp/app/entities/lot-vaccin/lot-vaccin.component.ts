import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILotVaccin } from 'app/shared/model/lot-vaccin.model';
import { LotVaccinService } from './lot-vaccin.service';
import { LotVaccinDeleteDialogComponent } from './lot-vaccin-delete-dialog.component';

@Component({
  selector: 'jhi-lot-vaccin',
  templateUrl: './lot-vaccin.component.html',
})
export class LotVaccinComponent implements OnInit, OnDestroy {
  lotVaccins?: ILotVaccin[];
  eventSubscriber?: Subscription;

  constructor(protected lotVaccinService: LotVaccinService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.lotVaccinService.query().subscribe((res: HttpResponse<ILotVaccin[]>) => (this.lotVaccins = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLotVaccins();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILotVaccin): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLotVaccins(): void {
    this.eventSubscriber = this.eventManager.subscribe('lotVaccinListModification', () => this.loadAll());
  }

  delete(lotVaccin: ILotVaccin): void {
    const modalRef = this.modalService.open(LotVaccinDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lotVaccin = lotVaccin;
  }
}
