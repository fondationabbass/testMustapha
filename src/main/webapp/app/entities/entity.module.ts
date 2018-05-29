import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TestMustaphaCandidatModule } from './candidat/candidat.module';
import { TestMustaphaExperienceCandidatModule } from './experience-candidat/experience-candidat.module';
import { TestMustaphaCandidatureModule } from './candidature/candidature.module';
import { TestMustaphaProjetModule } from './projet/projet.module';
import { TestMustaphaVisiteModule } from './visite/visite.module';
import { TestMustaphaSessionProjetModule } from './session-projet/session-projet.module';
import { TestMustaphaClientModule } from './client/client.module';
import { TestMustaphaPretModule } from './pret/pret.module';
import { TestMustaphaGarantieModule } from './garantie/garantie.module';
import { TestMustaphaEcheanceModule } from './echeance/echeance.module';
import { TestMustaphaCompteModule } from './compte/compte.module';
import { TestMustaphaParametrageModule } from './parametrage/parametrage.module';
import { TestMustaphaChapitreModule } from './chapitre/chapitre.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TestMustaphaCandidatModule,
        TestMustaphaExperienceCandidatModule,
        TestMustaphaCandidatureModule,
        TestMustaphaProjetModule,
        TestMustaphaVisiteModule,
        TestMustaphaSessionProjetModule,
        TestMustaphaClientModule,
        TestMustaphaPretModule,
        TestMustaphaGarantieModule,
        TestMustaphaEcheanceModule,
        TestMustaphaCompteModule,
        TestMustaphaParametrageModule,
        TestMustaphaChapitreModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestMustaphaEntityModule {}
