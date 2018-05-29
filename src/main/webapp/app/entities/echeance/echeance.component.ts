import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Echeance } from './echeance.model';
import { EcheanceService } from './echeance.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-echeance',
    templateUrl: './echeance.component.html'
})
export class EcheanceComponent implements OnInit, OnDestroy {
echeances: Echeance[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private echeanceService: EcheanceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.echeanceService.query().subscribe(
            (res: HttpResponse<Echeance[]>) => {
                this.echeances = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEcheances();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Echeance) {
        return item.id;
    }
    registerChangeInEcheances() {
        this.eventSubscriber = this.eventManager.subscribe('echeanceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
