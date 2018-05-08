package dao.bean;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dao.entity.CategoryEntity;
import dao.local.CategoryDaoLocal;
import notification.factory.local.NotificationFactory;

@Stateless
@TransactionAttribute
public class CategoryDaoBean implements CategoryDaoLocal {
	
	@PersistenceContext
	private EntityManager entityManager;

	public void create(CategoryEntity entity) {
		entity.setActive(true);
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.persist(entity);
		this.entityManager.flush();

	}

	public CategoryEntity get(long id) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<CategoryEntity> query = builder.createQuery(CategoryEntity.class);
		Root<CategoryEntity> category = query.from(CategoryEntity.class);
		
		query.select(category).where(builder.equal(category.get("id"), (int) id));		
				
		return this.entityManager.createQuery(query).getSingleResult();
		
	}

	public void update(CategoryEntity entity) {
		entity.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.merge(entity);

	}

	public void delete(long id) {
		CategoryEntity category = get(Integer.valueOf((int)id));
		category.setActive(false);
		category.setDateLastUpdate(new Date(System.currentTimeMillis()));
		this.entityManager.merge(category);

	}

	public List<CategoryEntity> getCategories() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		
		CriteriaQuery<CategoryEntity> query = builder.createQuery(CategoryEntity.class);
		Root<CategoryEntity> category = query.from(CategoryEntity.class);
		
		query.select(category).where(builder.equal(category.get("active"),true));
				
		return this.entityManager.createQuery(query).getResultList();
	}

	@Override
	public Date lastDateUpdate() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<CategoryEntity> query = builder.createQuery(CategoryEntity.class);
		Root<CategoryEntity> root = query.from(CategoryEntity.class);
		
		//query.select(root).where(builder.greatest(builder.in(root.get("dateLastUpdate"))));
		query.select(root);
		query.orderBy(builder.desc(root.get("dateLastUpdate")));
		
		List<CategoryEntity> entities = this.entityManager.createQuery(query).getResultList();
		if(!entities.isEmpty()) {
			CategoryEntity entity = entities.get(0);		
			return entity.getDateLastUpdate();
		} else {
			return new Date(0);
		}
	}


	@Override
	public NotificationFactory getNotificationFactory() {

		return null;
	}

}
