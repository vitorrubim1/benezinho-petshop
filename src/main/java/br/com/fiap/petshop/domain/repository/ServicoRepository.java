package br.com.fiap.petshop.domain.repository;

import br.com.fiap.petshop.domain.entity.Documento;
import br.com.fiap.petshop.domain.entity.servico.Servico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ServicoRepository implements Repository<Servico, Long>{

    private static final AtomicReference<ServicoRepository> instance = new AtomicReference<>();
    private EntityManager manager;
    private EntityManagerFactory factory;

    private ServicoRepository(EntityManager manager) {
        this.manager = manager;
    }

    public static ServicoRepository build(EntityManager manager) {
        ServicoRepository result = instance.get();
        if (Objects.isNull( result )) {
            ServicoRepository repo = new ServicoRepository( manager );
            if (instance.compareAndSet( null, repo )) {
                result = repo;
            } else {
                result = instance.get();
            }
        }
        return result;
    }


    @Override
    public List<Servico> findAll() {
        List<Servico> servicos = manager.createQuery("FROM Servico").getResultList();
        manager.close();

        return servicos;
    }

    @Override
    public Servico findById(Long id) {
        Servico servico = manager.find(Servico.class, id);
        manager.close();

        return servico;
    }

    @Override
    public List<Servico> findByTexto(String texto) {
        String jpql = "FROM Servico s WHERE Lower(s.nome) =: texto";
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(jpql);
        query.setParameter("texto", texto);
        List<Servico> list = query.getResultList();

        manager.close();

        return list;
    }

    @Override
    public Servico persist(Servico servico) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(servico);
        manager.getTransaction().commit();

        return servico;
    }

    @Override
    public Servico update(Servico servico) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Servico existingServico = manager.find(Servico.class, servico.getId());

        if (existingServico == null) {
            manager.getTransaction().rollback();
            manager.close();
            throw new EntityNotFoundException("Serviço não encontrado com a chave primária: " + servico.getId());
        }

        Servico updatedServico = manager.merge(servico);
        manager.getTransaction().commit();
        manager.close();

        return updatedServico;
    }

    @Override
    public boolean delete(Servico servico) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.remove(servico);
        manager.getTransaction().commit();
        manager.close();

        return true;
    }
}
