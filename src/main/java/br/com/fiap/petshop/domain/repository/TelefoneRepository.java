package br.com.fiap.petshop.domain.repository;

import br.com.fiap.petshop.domain.entity.Telefone;
import br.com.fiap.petshop.domain.entity.servico.Servico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class TelefoneRepository implements Repository<Telefone, Long> {

    private static final AtomicReference<TelefoneRepository> instance = new AtomicReference<>();
    private EntityManager manager;
    private EntityManagerFactory factory;

    private TelefoneRepository(EntityManager manager) {
        this.manager = manager;
    }

    public static TelefoneRepository build(EntityManager manager) {
        TelefoneRepository result = instance.get();
        if (Objects.isNull( result )) {
            TelefoneRepository repo = new TelefoneRepository( manager );
            if (instance.compareAndSet( null, repo )) {
                result = repo;
            } else {
                result = instance.get();
            }
        }
        return result;
    }


    @Override
    public List<Telefone> findAll() {
        List<Telefone> telefones = manager.createQuery("FROM Telefone").getResultList();
        manager.close();

        return telefones;
    }

    @Override
    public Telefone findById(Long id) {
        Telefone telefone = manager.find(Telefone.class, id);
        manager.close();

        return telefone;
    }

    @Override
    public List<Telefone> findByTexto(String texto) {
        String jpql = "FROM Telefone t WHERE Lower(t.numero) =: texto";
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(jpql);
        query.setParameter("texto", texto);
        List<Telefone> list = query.getResultList();

        manager.close();

        return list;
    }

    @Override
    public Telefone persist(Telefone telefone) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(telefone);
        manager.getTransaction().commit();

        return telefone;
    }

    @Override
    public Telefone update(Telefone telefone) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Telefone existingTelefone = manager.find(Telefone.class, telefone.getId());

        if (existingTelefone == null) {
            manager.getTransaction().rollback();
            manager.close();
            throw new EntityNotFoundException("Telefone não encontrado com a chave primária: " + telefone.getId());
        }

        Telefone updatedTelefone = manager.merge(telefone);
        manager.getTransaction().commit();
        manager.close();

        return updatedTelefone;
    }

    @Override
    public boolean delete(Telefone telefone) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Telefone existingTelefone = manager.find(Telefone.class, telefone.getId());

        if (existingTelefone == null) {
            manager.getTransaction().rollback();
            manager.close();
            return false;
        }

        manager.remove(existingTelefone);
        manager.getTransaction().commit();
        manager.close();

        return true;
    }
}
