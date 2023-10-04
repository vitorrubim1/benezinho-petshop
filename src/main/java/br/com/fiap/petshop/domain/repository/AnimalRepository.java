package br.com.fiap.petshop.domain.repository;

import br.com.fiap.petshop.Main;
import br.com.fiap.petshop.domain.entity.Telefone;
import br.com.fiap.petshop.domain.entity.animal.Animal;
import br.com.fiap.petshop.infra.database.EntityManagerFactoryProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AnimalRepository implements Repository<Animal, Long> {

    private static final AtomicReference<AnimalRepository> instance = new AtomicReference<>();
    private EntityManager manager;
    private EntityManagerFactory factory;

    private AnimalRepository(EntityManager manager) {
        this.manager = manager;
    }

    public static AnimalRepository build(EntityManager manager) {
        AnimalRepository result = instance.get();
        if (Objects.isNull( result )) {
            AnimalRepository repo = new AnimalRepository( manager );
            if (instance.compareAndSet( null, repo )) {
                result = repo;
            } else {
                result = instance.get();
            }
        }
        return result;
    }


    @Override
    public List<Animal> findAll() {
        List<Animal> animais = manager.createQuery("FROM Animal").getResultList();
        manager.close();

        return animais;
    }

    @Override
    public Animal findById(Long id) {
        Animal animal = manager.find(Animal.class, id);
        manager.close();

        return animal;
    }

    @Override
    public List<Animal> findByTexto(String texto) {
        String jpql = "FROM Animal a WHERE Lower(a.nome) =: texto";
        EntityManager manager = factory.createEntityManager();

        Query query = manager.createQuery(jpql);
        query.setParameter("texto", texto);
        List<Animal> list = query.getResultList();

        manager.close();

        return list;
    }

    @Override
    public Animal persist(Animal animal) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(animal);
        manager.getTransaction().commit();

        return animal;
    }

    @Override
    public Animal update(Animal animal) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Animal existingAnimal = manager.find(Animal.class, animal.getId());

        if (existingAnimal == null) {
            manager.getTransaction().rollback();
            manager.close();
            throw new EntityNotFoundException("Animal não encontrado com a chave primária: " + animal.getId());
        }

        Animal updatedAnimal = manager.merge(animal);
        manager.getTransaction().commit();
        manager.close();

        return updatedAnimal;
    }

    @Override
    public boolean delete(Animal animal) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        animal = manager.merge(animal);
        manager.remove(animal);
        manager.getTransaction().commit();
        manager.close();

        return true;
    }
}
