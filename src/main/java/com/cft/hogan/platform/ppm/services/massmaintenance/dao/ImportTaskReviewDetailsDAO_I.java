package com.cft.hogan.platform.ppm.services.massmaintenance.dao;

import java.util.List;

import com.cft.hogan.platform.ppm.services.massmaintenance.entity.ImportTaskReviewDetailEntity;

public interface ImportTaskReviewDetailsDAO_I {
	public List<ImportTaskReviewDetailEntity> findByImportTaskUUID(String importTaskUUID);
	public List<ImportTaskReviewDetailEntity> findPsetKeyByImportTaskUUIDAndStatus(String importTaskUUID, String status);

	public int Update(ImportTaskReviewDetailEntity importTaskReviewDetail);

	public int save(List<ImportTaskReviewDetailEntity> reviewList);
}
