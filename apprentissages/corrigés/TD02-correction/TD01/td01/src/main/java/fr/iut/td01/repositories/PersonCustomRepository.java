package fr.iut.td01.repositories;

/**
 * Custom interface for repository of persons
 */
public interface PersonCustomRepository {
    /**
     * Add one year to all person's age
     */
    void updateAgeAllPersons();
}
