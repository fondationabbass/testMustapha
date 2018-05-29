import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Compte } from './compte.model';
import { CompteService } from './compte.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-compte',
    templateUrl: './compte.component.html'
})
export class CompteComponent implements OnInit, OnDestroy {
comptes: Compte[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private compteService: CompteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.compteService.query().subscribe(
            (res: HttpResponse<Compte[]>) => {
                this.comptes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInComptes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Compte) {
        return item.id;
    }
    registerChangeInComptes() {
        this.eventSubscriber = this.eventManager.subscribe('compteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
