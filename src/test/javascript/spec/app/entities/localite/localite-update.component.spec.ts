import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { LocaliteUpdateComponent } from 'app/entities/localite/localite-update.component';
import { LocaliteService } from 'app/entities/localite/localite.service';
import { Localite } from 'app/shared/model/localite.model';

describe('Component Tests', () => {
  describe('Localite Management Update Component', () => {
    let comp: LocaliteUpdateComponent;
    let fixture: ComponentFixture<LocaliteUpdateComponent>;
    let service: LocaliteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [LocaliteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LocaliteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocaliteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocaliteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Localite(123);
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
        const entity = new Localite();
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
