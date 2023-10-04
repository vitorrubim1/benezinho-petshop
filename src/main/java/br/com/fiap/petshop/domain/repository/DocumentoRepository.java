package br.com.fiap.petshop.domain.repository;

import br.com.fiap.petshop.domain.entity.Documento;
import br.com.fiap.petshop.domain.entity.animal.Animal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class DocumentoRepository implements Repository<Documento, Long> {

    private static final AtomicReference<DocumentoRepository> instance = new AtomicReference<>();
    private EntityManager manager;
    private EntityManagerFactory factory;

    private DocumentoRepository(EntityManager manager) {
        this.manager = manager;
    }

    public static DocumentoRepository build(EntityManager manager) {
        DocumentoRepository result = instance.get();
        if (Objects.isNull( result )) {
            DocumentoRepository repo = new DocumentoRepository( manager );
            if (instance.compareAndSet( null, repo )) {
                result = repo;
            } else {
                result = instance.get();
            }
        }
        return result;
    }


    @Override
    public List<Documento> findAll() {
        List<Documento> documentos = manager.createQuery("FROM Documento").getResultList();
        manager.close();

        return documentos;
    }

    @Override
    public Documento findById(Long id) {
        Documento documento = manager.find(Documento.class, id);
        manager.close();

        return documento;
    }

    @Override
    public List<Documento> findByTexto(String texto) {
        String jpql = "FROM Documento d WHERE Lower(d.numero) =: texto";
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(jpql);
        query.setParameter("texto", texto);
        List<Documento> list = query.getResultList();

        manager.close();

        return list;
    }

    @Override
    public Documento persist(Documento documento) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(documento);
        manager.getTransaction().commit();

        return documento;
    }

    @Override
    public Documento update(Documento documento) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Documento existingDocument = manager.find(Documento.class, documento.getId());

        if (existingDocument == null) {
            manager.getTransaction().rollback();
            manager.close();
            throw new EntityNotFoundException("Documento não encontrado com a chave primária: " + documento.getId());
        }

        Documento updatedDocument = manager.merge(documento);
        manager.getTransaction().commit();
        manager.close();

        return updatedDocument;
    }

    @Override
    public boolean delete(Documento documento) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.remove(documento);
        manager.getTransaction().commit();
        manager.close();

        return true;
    }
}
