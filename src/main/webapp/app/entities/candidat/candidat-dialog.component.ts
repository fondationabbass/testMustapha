import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Candidat } from './candidat.model';
import { CandidatPopupService } from './candidat-popup.service';
import { CandidatService } from './candidat.service';
import { Client, ClientService } from '../client';

@Component({
    selector: 'jhi-candidat-dialog',
    templateUrl: './candidat-dialog.component.html'
})
export class CandidatDialogComponent implements OnInit {

    candidat: Candidat;
    isSaving: boolean;

    candidats: Client[];
    dateNaissanceDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private candidatService: CandidatService,
        private clientService: ClientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.clientService
            .query({filter: 'candidat-is-null'})
            .subscribe((res: HttpResponse<Client[]>) => {
                if (!this.candidat.candidat || !this.candidat.candidat.id) {
                    this.candidats = res.body;
                } else {
                    this.clientService
                        .find(this.candidat.candidat.id)
                        .subscribe((subRes: HttpResponse<Client>) => {
                            this.candidats = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.candidat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candidatService.update(this.candidat));
        } else {
            this.subscribeToSaveResponse(
                this.candidatService.create(this.candidat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Candidat>>) {
        result.subscribe((res: HttpResponse<Candidat>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Candidat) {
        this.eventManager.broadcast({ name: 'candidatListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackClientById(index: number, item: Client) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-candidat-popup',
    template: ''
})
export class CandidatPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidatPopupService: CandidatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candidatPopupService
                    .open(CandidatDialogComponent as Component, params['id']);
            } else {
                this.candidatPopupService
                    .open(CandidatDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
