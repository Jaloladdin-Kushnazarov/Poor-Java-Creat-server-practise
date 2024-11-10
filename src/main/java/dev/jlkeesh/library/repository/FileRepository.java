package dev.jlkeesh.library.repository;

import dev.jlkeesh.library.entity.File;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class FileRepository extends AbstractRepository implements GenericRepository<File, UUID> {


    @Override
    public File save(File file) {
        beginTransaction();
        em.persist(file);
        commitTransaction();
        return file;
    }

    @Override
    public File findById(UUID uuid) {
        return null;
    }

    @Override
    public List<File> findAll() {
        return List.of();
    }

    @Override
    public void delete(File entity) {

    }

    @Override
    public void deleteById(UUID uuid) {

    }

    public File findByGeneratedName(String generatedName) {
        return em.createQuery("from File f where f.generatedName = ?1", File.class)
                .setParameter(1, generatedName)
                .getSingleResult();

    }
}
