import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from './questionnaire.service';
import { QuestionnaireDeleteDialogComponent } from './questionnaire-delete-dialog.component';

@Component({
  selector: 'jhi-questionnaire',
  templateUrl: './questionnaire.component.html',
})
export class QuestionnaireComponent implements OnInit, OnDestroy {
  questionnaires?: IQuestionnaire[];
  eventSubscriber?: Subscription;

  constructor(
    protected questionnaireService: QuestionnaireService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.questionnaireService.query().subscribe((res: HttpResponse<IQuestionnaire[]>) => (this.questionnaires = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQuestionnaires();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuestionnaire): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQuestionnaires(): void {
    this.eventSubscriber = this.eventManager.subscribe('questionnaireListModification', () => this.loadAll());
  }

  delete(questionnaire: IQuestionnaire): void {
    const modalRef = this.modalService.open(QuestionnaireDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questionnaire = questionnaire;
  }
}
