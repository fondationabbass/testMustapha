import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EcheanceComponent } from './echeance.component';
import { EcheanceDetailComponent } from './echeance-detail.component';
import { EcheancePopupComponent } from './echeance-dialog.component';
import { EcheanceDeletePopupComponent } from './echeance-delete-dialog.component';

export const echeanceRoute: Routes = [
    {
        path: 'echeance',
        component: EcheanceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'echeance/:id',
        component: EcheanceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const echeancePopupRoute: Routes = [
    {
        path: 'echeance-new',
        component: EcheancePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'echeance/:id/edit',
        component: EcheancePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'echeance/:id/delete',
        component: EcheanceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.echeance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
