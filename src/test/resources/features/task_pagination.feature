Feature: Pagination des tâches

  Background:
    Given la base de données est vide

  # ─── Pagination générale ─────────────────────────────────────────────────────

  Scenario: Récupérer la première page de tâches
    Given 5 tâches existent
    When je récupère les tâches paginées page 0 taille 3
    Then la réponse a le statut 200
    And la page contient 3 tâches
    And le nombre total d'éléments est 5

  Scenario: Récupérer la dernière page de tâches
    Given 5 tâches existent
    When je récupère les tâches paginées page 1 taille 3
    Then la réponse a le statut 200
    And la page contient 2 tâches
    And le nombre total d'éléments est 5

  Scenario: Récupérer les tâches avec une page size supérieure au total
    Given 3 tâches existent
    When je récupère les tâches paginées page 0 taille 10
    Then la réponse a le statut 200
    And la page contient 3 tâches
    And le nombre total d'éléments est 3

  # ─── Tâches en attente ──────────────────────────────────────────────────────

  Scenario: Récupérer uniquement les tâches non complétées
    Given 3 tâches non complétées existent
    And 2 tâches complétées existent
    When je récupère les tâches en attente page 0 taille 10
    Then la réponse a le statut 200
    And la page contient 3 tâches
    And le nombre total d'éléments est 3

  Scenario: Aucune tâche en attente
    Given 2 tâches complétées existent
    When je récupère les tâches en attente page 0 taille 10
    Then la réponse a le statut 200
    And la page contient 0 tâches
    And le nombre total d'éléments est 0

  # ─── Tri ────────────────────────────────────────────────────────────────────

  Scenario: Trier par un champ non autorisé
    When je récupère les tâches paginées triées par "champInvalide"
    Then la réponse a le statut 400
