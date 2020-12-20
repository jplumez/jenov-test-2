import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { LotVaccinDetailComponent } from 'app/entities/lot-vaccin/lot-vaccin-detail.component';
import { LotVaccin } from 'app/shared/model/lot-vaccin.model';

describe('Component Tests', () => {
  describe('LotVaccin Management Detail Component', () => {
    let comp: LotVaccinDetailComponent;
    let fixture: ComponentFixture<LotVaccinDetailComponent>;
    const route = ({ data: of({ lotVaccin: new LotVaccin(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [LotVaccinDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LotVaccinDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LotVaccinDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lotVaccin on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lotVaccin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
