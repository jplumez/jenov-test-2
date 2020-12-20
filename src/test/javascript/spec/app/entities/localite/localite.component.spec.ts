import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JenovTest2TestModule } from '../../../test.module';
import { LocaliteComponent } from 'app/entities/localite/localite.component';
import { LocaliteService } from 'app/entities/localite/localite.service';
import { Localite } from 'app/shared/model/localite.model';

describe('Component Tests', () => {
  describe('Localite Management Component', () => {
    let comp: LocaliteComponent;
    let fixture: ComponentFixture<LocaliteComponent>;
    let service: LocaliteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [LocaliteComponent],
      })
        .overrideTemplate(LocaliteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocaliteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocaliteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Localite(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.localites && comp.localites[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
