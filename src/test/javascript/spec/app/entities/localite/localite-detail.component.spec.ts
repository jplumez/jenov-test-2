import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { LocaliteDetailComponent } from 'app/entities/localite/localite-detail.component';
import { Localite } from 'app/shared/model/localite.model';

describe('Component Tests', () => {
  describe('Localite Management Detail Component', () => {
    let comp: LocaliteDetailComponent;
    let fixture: ComponentFixture<LocaliteDetailComponent>;
    const route = ({ data: of({ localite: new Localite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [LocaliteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LocaliteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocaliteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load localite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.localite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
