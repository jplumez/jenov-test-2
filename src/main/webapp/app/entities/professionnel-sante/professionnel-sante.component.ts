import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProfessionnelSante } from 'app/shared/model/professionnel-sante.model';
import { ProfessionnelSanteService } from './professionnel-sante.service';
import { ProfessionnelSanteDeleteDialogComponent } from './professionnel-sante-delete-dialog.component';

@Component({
  selector: 'jhi-professionnel-sante',
  templateUrl: './professionnel-sante.component.html',
})
export class ProfessionnelSanteComponent implements OnInit, OnDestroy {
  professionnelSantes?: IProfessionnelSante[];
  eventSubscriber?: Subscription;

  constructor(
    protected professionnelSanteService: ProfessionnelSanteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.professionnelSanteService
      .query()
      .subscribe((res: HttpResponse<IProfessionnelSante[]>) => (this.professionnelSantes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProfessionnelSantes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProfessionnelSante): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProfessionnelSantes(): void {
    this.eventSubscriber = this.eventManager.subscribe('professionnelSanteListModification', () => this.loadAll());
  }

  delete(professionnelSante: IProfessionnelSante): void {
    const modalRef = this.modalService.open(ProfessionnelSanteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.professionnelSante = professionnelSante;
  }
}
