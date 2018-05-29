import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMustaphaSharedModule } from '../../shared';
import {
    ProjetService,
    ProjetPopupService,
    ProjetComponent,
    ProjetDetailComponent,
    ProjetDialogComponent,
    ProjetPopupComponent,
    ProjetDeletePopupComponent,
    ProjetDeleteDialogComponent,
    projetRoute,
    projetPopupRoute,
} from './';

const ENTITY_STATES = [
    ...projetRoute,
    ...projetPopupRoute,
];

@NgModule({
    imports: [
        TestMustaphaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProjetComponent,
        ProjetDetailComponent,
        ProjetDialogComponent,
        ProjetDeleteDialogComponent,
        ProjetPopupComponent,
        ProjetDeletePopupComponent,
    ],
    entryComponents: [
        ProjetComponent,
        ProjetDialogComponent,
        ProjetPopupComponent,
        ProjetDeleteDialogComponent,
        ProjetDeletePopupComponent,
    ],
    providers: [
        ProjetService,
        ProjetPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestMustaphaProjetModule {}
