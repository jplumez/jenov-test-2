import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JenovTest2TestModule } from '../../../test.module';
import { CreneauHoraireDetailComponent } from 'app/entities/creneau-horaire/creneau-horaire-detail.component';
import { CreneauHoraire } from 'app/shared/model/creneau-horaire.model';

describe('Component Tests', () => {
  describe('CreneauHoraire Management Detail Component', () => {
    let comp: CreneauHoraireDetailComponent;
    let fixture: ComponentFixture<CreneauHoraireDetailComponent>;
    const route = ({ data: of({ creneauHoraire: new CreneauHoraire(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JenovTest2TestModule],
        declarations: [CreneauHoraireDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CreneauHoraireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CreneauHoraireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load creneauHoraire on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.creneauHoraire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
