import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompteComponent } from './compte.component';
import { CompteDetailComponent } from './compte-detail.component';
import { ComptePopupComponent } from './compte-dialog.component';
import { CompteDeletePopupComponent } from './compte-delete-dialog.component';

export const compteRoute: Routes = [
    {
        path: 'compte',
        component: CompteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'compte/:id',
        component: CompteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const comptePopupRoute: Routes = [
    {
        path: 'compte-new',
        component: ComptePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compte/:id/edit',
        component: ComptePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compte/:id/delete',
        component: CompteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testMustaphaApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
