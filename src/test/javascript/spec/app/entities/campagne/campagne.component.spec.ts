import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JenovTest2TestModule } from '../../../test.module';
import { CampagneComponent } from 'app/entities/campagne/campagne.component';
import { CampagneService } from 'app/entities/campagne/campagne.service';
import { Campagne } from 'app/shared/model/campagne.model';

describe('Component Tests', () => {
  describe('Campagne Management Component', () => {
    let comp: CampagneComponent;
    let fixture: ComponentFixture<CampagneComponent>;
    let service: CampagneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [CampagneComponent],
      })
        .overrideTemplate(CampagneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CampagneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampagneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Campagne(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.campagnes && comp.campagnes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
