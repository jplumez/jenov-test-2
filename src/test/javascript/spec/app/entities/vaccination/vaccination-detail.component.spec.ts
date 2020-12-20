import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { VaccinationDetailComponent } from 'app/entities/vaccination/vaccination-detail.component';
import { Vaccination } from 'app/shared/model/vaccination.model';

describe('Component Tests', () => {
  describe('Vaccination Management Detail Component', () => {
    let comp: VaccinationDetailComponent;
    let fixture: ComponentFixture<VaccinationDetailComponent>;
    const route = ({ data: of({ vaccination: new Vaccination(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [VaccinationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VaccinationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VaccinationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vaccination on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vaccination).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
