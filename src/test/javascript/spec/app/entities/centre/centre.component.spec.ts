import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JenovTest2TestModule } from '../../../test.module';
import { CentreComponent } from 'app/entities/centre/centre.component';
import { CentreService } from 'app/entities/centre/centre.service';
import { Centre } from 'app/shared/model/centre.model';

describe('Component Tests', () => {
  describe('Centre Management Component', () => {
    let comp: CentreComponent;
    let fixture: ComponentFixture<CentreComponent>;
    let service: CentreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [CentreComponent],
      })
        .overrideTemplate(CentreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CentreComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CentreService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Centre(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.centres && comp.centres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
