import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMustaphaSharedModule } from '../../shared';
import {
    CompteService,
    ComptePopupService,
    CompteComponent,
    CompteDetailComponent,
    CompteDialogComponent,
    ComptePopupComponent,
    CompteDeletePopupComponent,
    CompteDeleteDialogComponent,
    compteRoute,
    comptePopupRoute,
} from './';

const ENTITY_STATES = [
    ...compteRoute,
    ...comptePopupRoute,
];

@NgModule({
    imports: [
        TestMustaphaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompteComponent,
        CompteDetailComponent,
        CompteDialogComponent,
        CompteDeleteDialogComponent,
        ComptePopupComponent,
        CompteDeletePopupComponent,
    ],
    entryComponents: [
        CompteComponent,
        CompteDialogComponent,
        ComptePopupComponent,
        CompteDeleteDialogComponent,
        CompteDeletePopupComponent,
    ],
    providers: [
        CompteService,
        ComptePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestMustaphaCompteModule {}
