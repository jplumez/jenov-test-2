import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { ProfessionnelSanteUpdateComponent } from 'app/entities/professionnel-sante/professionnel-sante-update.component';
import { ProfessionnelSanteService } from 'app/entities/professionnel-sante/professionnel-sante.service';
import { ProfessionnelSante } from 'app/shared/model/professionnel-sante.model';

describe('Component Tests', () => {
  describe('ProfessionnelSante Management Update Component', () => {
    let comp: ProfessionnelSanteUpdateComponent;
    let fixture: ComponentFixture<ProfessionnelSanteUpdateComponent>;
    let service: ProfessionnelSanteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [ProfessionnelSanteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProfessionnelSanteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionnelSanteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionnelSanteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfessionnelSante(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfessionnelSante();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
