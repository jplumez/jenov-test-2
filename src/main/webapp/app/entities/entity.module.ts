import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'campagne',
        loadChildren: () => import('./campagne/campagne.module').then(m => m.JenovTest2CampagneModule),
      },
      {
        path: 'questionnaire',
        loadChildren: () => import('./questionnaire/questionnaire.module').then(m => m.JenovTest2QuestionnaireModule),
      },
      {
        path: 'localite',
        loadChildren: () => import('./localite/localite.module').then(m => m.JenovTest2LocaliteModule),
      },
      {
        path: 'vaccination',
        loadChildren: () => import('./vaccination/vaccination.module').then(m => m.JenovTest2VaccinationModule),
      },
      {
        path: 'patient',
        loadChildren: () => import('./patient/patient.module').then(m => m.JenovTest2PatientModule),
      },
      {
        path: 'centre',
        loadChildren: () => import('./centre/centre.module').then(m => m.JenovTest2CentreModule),
      },
      {
        path: 'creneau-horaire',
        loadChildren: () => import('./creneau-horaire/creneau-horaire.module').then(m => m.JenovTest2CreneauHoraireModule),
      },
      {
        path: 'lot-vaccin',
        loadChildren: () => import('./lot-vaccin/lot-vaccin.module').then(m => m.JenovTest2LotVaccinModule),
      },
      {
        path: 'professionnel-sante',
        loadChildren: () => import('./professionnel-sante/professionnel-sante.module').then(m => m.JenovTest2ProfessionnelSanteModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JenovTest2EntityModule {}
