package com.bdi.fondation.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bdi.fondation.domain.Compte;

import com.bdi.fondation.repository.CompteRepository;
import com.bdi.fondation.web.rest.errors.BadRequestAlertException;
import com.bdi.fondation.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Compte.
 */
@RestController
@RequestMapping("/api")
public class CompteResource {

    private final Logger log = LoggerFactory.getLogger(CompteResource.class);

    private static final String ENTITY_NAME = "compte";

    private final CompteRepository compteRepository;

    public CompteResource(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    /**
     * POST  /comptes : Create a new compte.
     *
     * @param compte the compte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compte, or with status 400 (Bad Request) if the compte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comptes")
    @Timed
    public ResponseEntity<Compte> createCompte(@RequestBody Compte compte) throws URISyntaxException {
        log.debug("REST request to save Compte : {}", compte);
        if (compte.getId() != null) {
            throw new BadRequestAlertException("A new compte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Compte result = compteRepository.save(compte);
        return ResponseEntity.created(new URI("/api/comptes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comptes : Updates an existing compte.
     *
     * @param compte the compte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compte,
     * or with status 400 (Bad Request) if the compte is not valid,
     * or with status 500 (Internal Server Error) if the compte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comptes")
    @Timed
    public ResponseEntity<Compte> updateCompte(@RequestBody Compte compte) throws URISyntaxException {
        log.debug("REST request to update Compte : {}", compte);
        if (compte.getId() == null) {
            return createCompte(compte);
        }
        Compte result = compteRepository.save(compte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comptes : get all the comptes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comptes in body
     */
    @GetMapping("/comptes")
    @Timed
    public List<Compte> getAllComptes() {
        log.debug("REST request to get all Comptes");
        return compteRepository.findAll();
        }

    /**
     * GET  /comptes/:id : get the "id" compte.
     *
     * @param id the id of the compte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compte, or with status 404 (Not Found)
     */
    @GetMapping("/comptes/{id}")
    @Timed
    public ResponseEntity<Compte> getCompte(@PathVariable Long id) {
        log.debug("REST request to get Compte : {}", id);
        Compte compte = compteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compte));
    }

    /**
     * DELETE  /comptes/:id : delete the "id" compte.
     *
     * @param id the id of the compte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comptes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        log.debug("REST request to delete Compte : {}", id);
        compteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
