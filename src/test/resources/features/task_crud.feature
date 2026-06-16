Feature: Gestion CRUD des tâches

  Background:
    Given la base de données est vide

  # ─── Création ───────────────────────────────────────────────────────────────

  Scenario: Créer une tâche avec succès
    When je crée une tâche avec le libellé "Acheter du pain"
    Then la réponse a le statut 200
    And la tâche a le libellé "Acheter du pain"
    And la tâche est non complétée

  Scenario: Créer une tâche avec un libellé vide
    When je crée une tâche avec le libellé ""
    Then la réponse a le statut 400

  # ─── Lecture ────────────────────────────────────────────────────────────────

  Scenario: Récupérer toutes les tâches
    Given une tâche avec le libellé "Tâche 1" existe
    And une tâche avec le libellé "Tâche 2" existe
    When je récupère toutes les tâches
    Then la réponse a le statut 200
    And la liste contient 2 tâches

  Scenario: Récupérer toutes les tâches quand la liste est vide
    When je récupère toutes les tâches
    Then la réponse a le statut 200
    And la liste contient 0 tâches

  Scenario: Récupérer une tâche par son ID
    Given une tâche avec le libellé "Ma tâche importante" existe
    When je récupère la tâche par son ID
    Then la réponse a le statut 200
    And la tâche a le libellé "Ma tâche importante"

  Scenario: Récupérer une tâche avec un ID inexistant
    When je récupère la tâche avec l'ID 9999
    Then la réponse a le statut 404

  # ─── Mise à jour ────────────────────────────────────────────────────────────

  Scenario: Marquer une tâche comme complétée
    Given une tâche avec le libellé "Tâche à compléter" existe
    When je mets à jour le statut de la tâche à "true"
    Then la réponse a le statut 200
    And la tâche est complétée

  Scenario: Marquer une tâche comme non complétée
    Given une tâche avec le libellé "Tâche à rouvrir" existe
    When je mets à jour le statut de la tâche à "false"
    Then la réponse a le statut 200
    And la tâche est non complétée

  Scenario: Mettre à jour le statut d'une tâche inexistante
    When je mets à jour le statut de la tâche avec l'ID 9999 à "true"
    Then la réponse a le statut 404

  # ─── Suppression ────────────────────────────────────────────────────────────

  Scenario: Supprimer une tâche existante
    Given une tâche avec le libellé "Tâche à supprimer" existe
    When je supprime la tâche
    Then la réponse a le statut 204

  Scenario: Supprimer une tâche inexistante
    When je supprime la tâche avec l'ID 9999
    Then la réponse a le statut 404
