package dao.local;

import java.util.List;

import javax.ejb.Local;

import dao.entity.CategoryEntity;

@Local
public interface CategoryDaoLocal extends CommonDao<CategoryEntity> {
	
	public List<CategoryEntity> getCategories();

}