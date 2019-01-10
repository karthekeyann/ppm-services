package com.cft.hogan.platform.ppm.api.dao.mm.tda;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cft.hogan.platform.ppm.api.dao.mm.ImportTaskReviewDetailsDAO;
import com.cft.hogan.platform.ppm.api.dao.mm.ImportTaskReviewDetailsDAO_I;
import com.cft.hogan.platform.ppm.api.entity.mm.ImportTaskReviewDetailEntity;
import com.cft.hogan.platform.ppm.api.util.Constants;


@Repository
@Transactional("transactionManagerTDA")
public class ImportTaskReviewDetailsDAO_TDA extends ImportTaskReviewDetailsDAO implements ImportTaskReviewDetailsDAO_I{

	@Autowired
	@PersistenceContext(unitName = Constants.DATASOURCE_TDA)
	private EntityManager entityManager;

	public List<ImportTaskReviewDetailEntity> findByImportTaskUUID(String importTaskUUID) {
		return findByImportTaskUUID(importTaskUUID, entityManager);
	}

	public List<ImportTaskReviewDetailEntity> findPsetKeyByImportTaskUUIDAndStatus(String importTaskUUID, String status) {

		return findPsetKeyByImportTaskUUIDAndStatus(importTaskUUID, status, entityManager);
	}

	public int Update(ImportTaskReviewDetailEntity importTaskReviewDetail) {

		return Update(importTaskReviewDetail, entityManager);
	}

	public int save(List<ImportTaskReviewDetailEntity> reviewList) {

		return save(reviewList, entityManager);
	}

}
