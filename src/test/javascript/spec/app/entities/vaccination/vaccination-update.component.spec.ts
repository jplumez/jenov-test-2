import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { VaccinationUpdateComponent } from 'app/entities/vaccination/vaccination-update.component';
import { VaccinationService } from 'app/entities/vaccination/vaccination.service';
import { Vaccination } from 'app/shared/model/vaccination.model';

describe('Component Tests', () => {
  describe('Vaccination Management Update Component', () => {
    let comp: VaccinationUpdateComponent;
    let fixture: ComponentFixture<VaccinationUpdateComponent>;
    let service: VaccinationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [VaccinationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VaccinationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VaccinationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VaccinationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vaccination(123);
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
        const entity = new Vaccination();
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
