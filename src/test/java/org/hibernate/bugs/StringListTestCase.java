package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Simple test case showing duplicate insert behavior
 */
public class StringListTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	@Test
	public void hhh123Test() throws Exception {
	    StringListParent slp0 = new StringListParent();
	    EntityManager em = entityManagerFactory.createEntityManager();
	    em.getTransaction().begin();
	    em.persist(slp0);
	    em.getTransaction().commit();
	    em.close();

	    em = entityManagerFactory.createEntityManager();
	    em.getTransaction().begin();
	    StringListParent slp1 = em.find(StringListParent.class, slp0.getOID());
	    em.getTransaction().commit();
	    em.close();

	    em = entityManagerFactory.createEntityManager();
	    em.getTransaction().begin();
	    StringListParent slp2 = em.find(StringListParent.class, slp0.getOID());
	    em.getTransaction().commit();
	    em.close();

	    // just making sure
	    assertFalse(slp0 == slp1);
	    assertFalse(slp1 == slp2);

	    slp1.addString("foo");
	    slp1 = store(slp1);

	    slp2.addString("bar");
	    slp2 = store(slp2);
	}

	private StringListParent store(StringListParent bean) {
	    EntityManager em = entityManagerFactory.createEntityManager();
	    em.getTransaction().begin();
	    StringListParent reloadedBean = em.merge(bean);
	    em.getTransaction().commit();
	    em.close();
	    return reloadedBean;
	}

}
