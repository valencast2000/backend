package com.coop.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coop.business.BusinessException;
import com.coop.business.IProductoBusiness;
import com.coop.business.NotFoundException;
import com.coop.model.Producto;
import com.coop.model.dto.ProductoSintetico;

@RestController
@RequestMapping(Constantes.URL_PRODUCTOS)
public class ProductosRestService {

	@Autowired
	private IProductoBusiness productoBusiness;

	@GetMapping("")
	public ResponseEntity<List<Producto>> list(
			@RequestParam(required=false,defaultValue="@*@",value="q" ) String parteDelNombre, 
			@RequestParam(required=false,defaultValue="-1",value="pdesde" ) double precioDesde, 
			@RequestParam(required=false,defaultValue="-1",value="phasta" ) double precioHasta) {
		try {
			List<Producto> lista;
			if(!parteDelNombre.equals("@*@")) {
				lista=productoBusiness.list(parteDelNombre, 0,0);
			}else if(precioDesde!=-1 && precioHasta!=-1) {
				lista=productoBusiness.list(null, precioDesde,precioHasta);
			} else {
				lista=productoBusiness.list();
			}
			
			return new ResponseEntity<List<Producto>>(lista, HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Producto> load(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<Producto>(productoBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<Producto> add(@RequestBody Producto producto) {
		try {
			return new ResponseEntity<Producto>(productoBusiness.add(producto),
					HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("")
	public ResponseEntity<Producto> update(@RequestBody Producto producto) {
		try {
			return new ResponseEntity<Producto>(productoBusiness.update(producto),
					HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		try {
			productoBusiness.delete(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * GET /productos <- Obtener la lista de productos 
	 * GET /productos/{id} <- Obtener un producto cuyo id sea {id} 
	 * POST /productos <-Agregar un nuevo  producto (va en el body)
	 * PUT /productos <-modificar un producto existente (va en el body) 
	 * DELETE /productos/{id} <- Elimina un producto cuyo id sea {id}
	 */

	@PostMapping("/esta/es/una/prueba/")
	public ResponseEntity<String> prueba() {
		return new ResponseEntity<String>(new Date().toString(), HttpStatus.OK);
	}

	@GetMapping("/sintetico")
	public ResponseEntity<List<ProductoSintetico>> listadoSintetico(@RequestParam(value="precio_minimo") double precioMinimo) {
		try {
			return new ResponseEntity<List<ProductoSintetico>>(productoBusiness.listadoSintetico(precioMinimo), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<ProductoSintetico>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/cantidad_mas_caros_que")
	public ResponseEntity<Long> cantidadProductosMasCarosQue(@RequestParam(value="precio_minimo") double precioMinimo) {
		try {
			return new ResponseEntity<Long>(productoBusiness.cantidadProductosMasCarosQue(precioMinimo), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Long>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
