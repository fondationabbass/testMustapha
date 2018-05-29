import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMustaphaSharedModule } from '../../shared';
import {
    EcheanceService,
    EcheancePopupService,
    EcheanceComponent,
    EcheanceDetailComponent,
    EcheanceDialogComponent,
    EcheancePopupComponent,
    EcheanceDeletePopupComponent,
    EcheanceDeleteDialogComponent,
    echeanceRoute,
    echeancePopupRoute,
} from './';

const ENTITY_STATES = [
    ...echeanceRoute,
    ...echeancePopupRoute,
];

@NgModule({
    imports: [
        TestMustaphaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EcheanceComponent,
        EcheanceDetailComponent,
        EcheanceDialogComponent,
        EcheanceDeleteDialogComponent,
        EcheancePopupComponent,
        EcheanceDeletePopupComponent,
    ],
    entryComponents: [
        EcheanceComponent,
        EcheanceDialogComponent,
        EcheancePopupComponent,
        EcheanceDeleteDialogComponent,
        EcheanceDeletePopupComponent,
    ],
    providers: [
        EcheanceService,
        EcheancePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestMustaphaEcheanceModule {}
