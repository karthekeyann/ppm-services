package com.cft.hogan.platform.ppm.services.massmaintenance.dao;

import java.util.List;

import com.cft.hogan.platform.ppm.services.massmaintenance.entity.ImportTaskEntity;

public interface ImportTaskDAO_I {
	public ImportTaskEntity findByUUID(String uuid);

	public List<ImportTaskEntity> findByStatus(String status);

	public int UpdateStatus(ImportTaskEntity entity);

	public String save(ImportTaskEntity entity);

	public int delete(String uuid);
}
