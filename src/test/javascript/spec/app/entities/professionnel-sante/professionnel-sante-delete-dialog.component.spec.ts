import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JenovTest2TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ProfessionnelSanteDeleteDialogComponent } from 'app/entities/professionnel-sante/professionnel-sante-delete-dialog.component';
import { ProfessionnelSanteService } from 'app/entities/professionnel-sante/professionnel-sante.service';

describe('Component Tests', () => {
  describe('ProfessionnelSante Management Delete Component', () => {
    let comp: ProfessionnelSanteDeleteDialogComponent;
    let fixture: ComponentFixture<ProfessionnelSanteDeleteDialogComponent>;
    let service: ProfessionnelSanteService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [ProfessionnelSanteDeleteDialogComponent],
      })
        .overrideTemplate(ProfessionnelSanteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionnelSanteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionnelSanteService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
