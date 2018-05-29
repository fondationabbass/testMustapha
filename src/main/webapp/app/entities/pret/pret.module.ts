import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMustaphaSharedModule } from '../../shared';
import {
    PretService,
    PretPopupService,
    PretComponent,
    PretDetailComponent,
    PretDialogComponent,
    PretPopupComponent,
    PretDeletePopupComponent,
    PretDeleteDialogComponent,
    pretRoute,
    pretPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pretRoute,
    ...pretPopupRoute,
];

@NgModule({
    imports: [
        TestMustaphaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PretComponent,
        PretDetailComponent,
        PretDialogComponent,
        PretDeleteDialogComponent,
        PretPopupComponent,
        PretDeletePopupComponent,
    ],
    entryComponents: [
        PretComponent,
        PretDialogComponent,
        PretPopupComponent,
        PretDeleteDialogComponent,
        PretDeletePopupComponent,
    ],
    providers: [
        PretService,
        PretPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TestMustaphaPretModule {}
