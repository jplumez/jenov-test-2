import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocalite } from 'app/shared/model/localite.model';
import { LocaliteService } from './localite.service';
import { LocaliteDeleteDialogComponent } from './localite-delete-dialog.component';

@Component({
  selector: 'jhi-localite',
  templateUrl: './localite.component.html',
})
export class LocaliteComponent implements OnInit, OnDestroy {
  localites?: ILocalite[];
  eventSubscriber?: Subscription;

  constructor(protected localiteService: LocaliteService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.localiteService.query().subscribe((res: HttpResponse<ILocalite[]>) => (this.localites = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLocalites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILocalite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLocalites(): void {
    this.eventSubscriber = this.eventManager.subscribe('localiteListModification', () => this.loadAll());
  }

  delete(localite: ILocalite): void {
    const modalRef = this.modalService.open(LocaliteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.localite = localite;
  }
}
