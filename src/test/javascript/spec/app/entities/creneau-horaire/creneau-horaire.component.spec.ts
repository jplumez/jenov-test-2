import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JenovTest2TestModule } from '../../../test.module';
import { CreneauHoraireComponent } from 'app/entities/creneau-horaire/creneau-horaire.component';
import { CreneauHoraireService } from 'app/entities/creneau-horaire/creneau-horaire.service';
import { CreneauHoraire } from 'app/shared/model/creneau-horaire.model';

describe('Component Tests', () => {
  describe('CreneauHoraire Management Component', () => {
    let comp: CreneauHoraireComponent;
    let fixture: ComponentFixture<CreneauHoraireComponent>;
    let service: CreneauHoraireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [CreneauHoraireComponent],
      })
        .overrideTemplate(CreneauHoraireComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CreneauHoraireComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CreneauHoraireService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CreneauHoraire(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.creneauHoraires && comp.creneauHoraires[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
