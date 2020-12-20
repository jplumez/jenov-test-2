import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICreneauHoraire } from 'app/shared/model/creneau-horaire.model';
import { CreneauHoraireService } from './creneau-horaire.service';
import { CreneauHoraireDeleteDialogComponent } from './creneau-horaire-delete-dialog.component';

@Component({
  selector: 'jhi-creneau-horaire',
  templateUrl: './creneau-horaire.component.html',
})
export class CreneauHoraireComponent implements OnInit, OnDestroy {
  creneauHoraires?: ICreneauHoraire[];
  eventSubscriber?: Subscription;

  constructor(
    protected creneauHoraireService: CreneauHoraireService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.creneauHoraireService.query().subscribe((res: HttpResponse<ICreneauHoraire[]>) => (this.creneauHoraires = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCreneauHoraires();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICreneauHoraire): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCreneauHoraires(): void {
    this.eventSubscriber = this.eventManager.subscribe('creneauHoraireListModification', () => this.loadAll());
  }

  delete(creneauHoraire: ICreneauHoraire): void {
    const modalRef = this.modalService.open(CreneauHoraireDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.creneauHoraire = creneauHoraire;
  }
}
