import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JenovTest2TestModule } from '../../../test.module';
import { LotVaccinComponent } from 'app/entities/lot-vaccin/lot-vaccin.component';
import { LotVaccinService } from 'app/entities/lot-vaccin/lot-vaccin.service';
import { LotVaccin } from 'app/shared/model/lot-vaccin.model';

describe('Component Tests', () => {
  describe('LotVaccin Management Component', () => {
    let comp: LotVaccinComponent;
    let fixture: ComponentFixture<LotVaccinComponent>;
    let service: LotVaccinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [LotVaccinComponent],
      })
        .overrideTemplate(LotVaccinComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LotVaccinComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LotVaccinService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LotVaccin(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lotVaccins && comp.lotVaccins[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
