package io.upstartz.dao;

import io.upstartz.model.StartupCompany;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

public class StartupCompanyDAO {
    @Resource
    private SessionFactory sessionFactory;

    public List<StartupCompany> listTopUpvotes(final int limit) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StartupCompany.class);
        criteria.add(Restrictions.gt("upvotes", 0));
        criteria.addOrder(Order.desc("upvotes"));
        criteria.setFetchSize(limit);
        criteria.setMaxResults(limit);
        return criteria.list();
    }

    public List<StartupCompany> listTopDownvotes(final int limit) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StartupCompany.class);
        criteria.add(Restrictions.gt("downvotes", 0));
        criteria.addOrder(Order.desc("downvotes"));
        criteria.setFetchSize(limit);
        criteria.setMaxResults(limit);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public StartupCompany loadRandom() {
        final Query query = sessionFactory.getCurrentSession().createQuery("FROM StartupCompany ORDER BY rand()");
        query.setMaxResults(1);
        return (StartupCompany)query.uniqueResult();
    }

    @Transactional(readOnly = true)
    public StartupCompany load(final long id) {
        return (StartupCompany) sessionFactory.getCurrentSession().get(StartupCompany.class, id);
    }

    @Transactional
    public void upvote(final long id) {
        final StartupCompany sc = load(id);
        sc.addUpvote();
        save(sc);
    }

    @Transactional
    public void downvote(final long id) {
        final StartupCompany sc = load(id);
        sc.addDownvote();
        save(sc);
    }

    @Transactional
    public void save(final StartupCompany startupCompany) {
        sessionFactory.getCurrentSession().save(startupCompany);
    }
}
