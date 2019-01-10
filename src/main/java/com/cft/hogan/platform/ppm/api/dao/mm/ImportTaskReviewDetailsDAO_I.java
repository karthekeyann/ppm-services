package com.cft.hogan.platform.ppm.api.dao.mm;

import java.util.List;

import com.cft.hogan.platform.ppm.api.entity.mm.ImportTaskReviewDetailEntity;

public interface ImportTaskReviewDetailsDAO_I {
	public List<ImportTaskReviewDetailEntity> findByImportTaskUUID(String importTaskUUID);
	public List<ImportTaskReviewDetailEntity> findPsetKeyByImportTaskUUIDAndStatus(String importTaskUUID, String status);

	public int Update(ImportTaskReviewDetailEntity importTaskReviewDetail);

	public int save(List<ImportTaskReviewDetailEntity> reviewList);
}
