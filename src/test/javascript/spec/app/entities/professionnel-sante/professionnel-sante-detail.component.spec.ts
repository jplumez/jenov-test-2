import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { ProfessionnelSanteDetailComponent } from 'app/entities/professionnel-sante/professionnel-sante-detail.component';
import { ProfessionnelSante } from 'app/shared/model/professionnel-sante.model';

describe('Component Tests', () => {
  describe('ProfessionnelSante Management Detail Component', () => {
    let comp: ProfessionnelSanteDetailComponent;
    let fixture: ComponentFixture<ProfessionnelSanteDetailComponent>;
    const route = ({ data: of({ professionnelSante: new ProfessionnelSante(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [ProfessionnelSanteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProfessionnelSanteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionnelSanteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load professionnelSante on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.professionnelSante).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
