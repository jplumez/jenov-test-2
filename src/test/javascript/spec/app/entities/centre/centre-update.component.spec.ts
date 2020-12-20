import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { CentreUpdateComponent } from 'app/entities/centre/centre-update.component';
import { CentreService } from 'app/entities/centre/centre.service';
import { Centre } from 'app/shared/model/centre.model';

describe('Component Tests', () => {
  describe('Centre Management Update Component', () => {
    let comp: CentreUpdateComponent;
    let fixture: ComponentFixture<CentreUpdateComponent>;
    let service: CentreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [CentreUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CentreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CentreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CentreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Centre(123);
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
        const entity = new Centre();
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
