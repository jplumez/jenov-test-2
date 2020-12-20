import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { LotVaccinUpdateComponent } from 'app/entities/lot-vaccin/lot-vaccin-update.component';
import { LotVaccinService } from 'app/entities/lot-vaccin/lot-vaccin.service';
import { LotVaccin } from 'app/shared/model/lot-vaccin.model';

describe('Component Tests', () => {
  describe('LotVaccin Management Update Component', () => {
    let comp: LotVaccinUpdateComponent;
    let fixture: ComponentFixture<LotVaccinUpdateComponent>;
    let service: LotVaccinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [LotVaccinUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LotVaccinUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LotVaccinUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LotVaccinService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LotVaccin(123);
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
        const entity = new LotVaccin();
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
