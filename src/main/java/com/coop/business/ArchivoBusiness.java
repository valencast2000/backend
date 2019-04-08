package com.coop.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coop.model.Archivo;
import com.coop.model.persistence.ArchivoRepository;

@Service
public class ArchivoBusiness implements IArchivoBusiness {
	
	@Autowired
	private ArchivoRepository archivoDAO;

	@Override
	public Archivo load(long id) throws BusinessException, NotFoundException {
		Optional<Archivo> o;
		try {
			o = archivoDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (o.isPresent())
			return o.get();
		else
			throw new NotFoundException();
	}

	@Override
	public Archivo add(Archivo archivo) throws BusinessException {
		try {
			return archivoDAO.save(archivo);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(long id) throws BusinessException {
		try {
			archivoDAO.deleteById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	

	@Override
	public Archivo update(Archivo archivo) throws BusinessException {
		try {
			return archivoDAO.save(archivo);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		
	}

	@Override
	public List<Archivo> list() throws BusinessException {
		try {
			return archivoDAO.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
