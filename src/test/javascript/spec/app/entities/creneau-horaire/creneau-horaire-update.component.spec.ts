import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { CreneauHoraireUpdateComponent } from 'app/entities/creneau-horaire/creneau-horaire-update.component';
import { CreneauHoraireService } from 'app/entities/creneau-horaire/creneau-horaire.service';
import { CreneauHoraire } from 'app/shared/model/creneau-horaire.model';

describe('Component Tests', () => {
  describe('CreneauHoraire Management Update Component', () => {
    let comp: CreneauHoraireUpdateComponent;
    let fixture: ComponentFixture<CreneauHoraireUpdateComponent>;
    let service: CreneauHoraireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [CreneauHoraireUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CreneauHoraireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CreneauHoraireUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CreneauHoraireService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CreneauHoraire(123);
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
        const entity = new CreneauHoraire();
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
