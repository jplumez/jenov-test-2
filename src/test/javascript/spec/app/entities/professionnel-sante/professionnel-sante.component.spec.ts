import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JenovTest2TestModule } from '../../../test.module';
import { ProfessionnelSanteComponent } from 'app/entities/professionnel-sante/professionnel-sante.component';
import { ProfessionnelSanteService } from 'app/entities/professionnel-sante/professionnel-sante.service';
import { ProfessionnelSante } from 'app/shared/model/professionnel-sante.model';

describe('Component Tests', () => {
  describe('ProfessionnelSante Management Component', () => {
    let comp: ProfessionnelSanteComponent;
    let fixture: ComponentFixture<ProfessionnelSanteComponent>;
    let service: ProfessionnelSanteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [ProfessionnelSanteComponent],
      })
        .overrideTemplate(ProfessionnelSanteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionnelSanteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionnelSanteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProfessionnelSante(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.professionnelSantes && comp.professionnelSantes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
