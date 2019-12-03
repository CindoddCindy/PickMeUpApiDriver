package cindy.sirihpinang.repository;

import java.util.Date;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import cindy.sirihpinang.model.Driver;

@Singleton
public class DriverRepository implements DriverRepositoryInterface {

    @PersistenceContext
    private EntityManager manager;

    public DriverRepository(@CurrentSession EntityManager manager){
        this.manager = manager;
    }

    @Override
    @Transactional(readOnly = true)
    public Long size() {
        Long count = manager.createQuery("select count(*) from Driver where deleted_at IS NULL", Long.class).getSingleResult();
        return count;
    }

    @Override
    @Transactional
    public List<Driver> findAll(int page, int limit) {
        TypedQuery<Driver> query = manager
                .createQuery("from Driver where deleted_at IS NULL", Driver.class)
                .setFirstResult(page > 1 ? page * limit - limit : 0)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Driver findById(@NotNull Long id) {
        Driver query = manager.find(Driver.class, id);
        return query;
    }

    @Override
    @Transactional
    public boolean save(@NotNull Driver driver) {
        try {
            manager.persist(driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean update(@NotNull Long id, String name,String email, String password, String driverid, String drivercomname) {
        try {

            Driver c = manager.find(Driver.class, id);
            if (name.equals("-")==false) c.setName(name);
            if (email.equals("-")==false) c.setEmail(email);
            if (password.equals("-")==false) c.setPassword(password);
            if (driverid.equals("-")==false) c.setDriverId(driverid);
            if (drivercompname.equals("-")==false) c.setDriverComName(drivercompname);

          //  if (grade != 0) c.setGrade(grade);
            c.setUpdated_At(new Date());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean destroy(@NotNull Long id) {
        try {
            Driver c = manager.find(Driver.class, id);
            c.setDeleted_At(new Date());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}