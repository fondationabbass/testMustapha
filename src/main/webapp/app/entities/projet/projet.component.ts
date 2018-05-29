import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Projet } from './projet.model';
import { ProjetService } from './projet.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-projet',
    templateUrl: './projet.component.html'
})
export class ProjetComponent implements OnInit, OnDestroy {
projets: Projet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private projetService: ProjetService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.projetService.query().subscribe(
            (res: HttpResponse<Projet[]>) => {
                this.projets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProjets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Projet) {
        return item.id;
    }
    registerChangeInProjets() {
        this.eventSubscriber = this.eventManager.subscribe('projetListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
