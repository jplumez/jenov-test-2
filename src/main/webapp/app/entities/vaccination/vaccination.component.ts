import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVaccination } from 'app/shared/model/vaccination.model';
import { VaccinationService } from './vaccination.service';
import { VaccinationDeleteDialogComponent } from './vaccination-delete-dialog.component';

@Component({
  selector: 'jhi-vaccination',
  templateUrl: './vaccination.component.html',
})
export class VaccinationComponent implements OnInit, OnDestroy {
  vaccinations?: IVaccination[];
  eventSubscriber?: Subscription;

  constructor(
    protected vaccinationService: VaccinationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.vaccinationService.query().subscribe((res: HttpResponse<IVaccination[]>) => (this.vaccinations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVaccinations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVaccination): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVaccinations(): void {
    this.eventSubscriber = this.eventManager.subscribe('vaccinationListModification', () => this.loadAll());
  }

  delete(vaccination: IVaccination): void {
    const modalRef = this.modalService.open(VaccinationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vaccination = vaccination;
  }
}
