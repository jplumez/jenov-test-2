import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JenovTest2TestModule } from '../../../test.module';
import { VaccinationComponent } from 'app/entities/vaccination/vaccination.component';
import { VaccinationService } from 'app/entities/vaccination/vaccination.service';
import { Vaccination } from 'app/shared/model/vaccination.model';

describe('Component Tests', () => {
  describe('Vaccination Management Component', () => {
    let comp: VaccinationComponent;
    let fixture: ComponentFixture<VaccinationComponent>;
    let service: VaccinationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [VaccinationComponent],
      })
        .overrideTemplate(VaccinationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VaccinationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VaccinationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Vaccination(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vaccinations && comp.vaccinations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
