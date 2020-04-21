package controller;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dto.Mensaje;
import dto.ProductoDto;
import entity.Producto;
import service.ProductoService;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

	@Autowired
	ProductoService productoService;

	@GetMapping("/lista")
	public ResponseEntity<List<Producto>> listar() {

		List<Producto> listaProductos = productoService.listar();
		return new ResponseEntity<List<Producto>>(listaProductos, HttpStatus.OK);
	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<Producto> obtenerPorId(@PathVariable("id") int id) {

		if (!productoService.existePorId(id)) {// si no existe el producto
			return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		}
		Producto producto = productoService.obtenerUno(id).get();

		return new ResponseEntity(producto, HttpStatus.OK);

	}

	@GetMapping("/detailname/{nombre}")
	public ResponseEntity<Producto> obtenerPorNombre(@PathVariable("nombre") String nombre) {

		if (!productoService.existePorNombre(nombre)) {// si no existe el producto
			return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		}
		Producto producto = productoService.obtenerPorNombre(nombre).get();

		return new ResponseEntity(producto, HttpStatus.OK);

	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody ProductoDto productoDto) {
		if (StringUtils.isBlank(productoDto.getNombre()))
			return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

		if (productoDto.getPrecio() < 0)
			return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

		if (productoService.existePorNombre(productoDto.getNombre()))
			return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
		Producto producto = new Producto(productoDto.getNombre(), productoDto.getPrecio());
		productoService.guardar(producto);
		return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> actualizar(@PathVariable("id") int id, @RequestBody ProductoDto productoDto) {

		if (!productoService.existePorId(id))// valido que id del producto exista
			return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);

		if (productoService.existePorNombre(productoDto.getNombre())
				&& productoService.obtenerPorNombre(productoDto.getNombre()).get().getId() != id)
			return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

		if (StringUtils.isBlank(productoDto.getNombre()))
			return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

		if (productoDto.getPrecio() < 0)
			return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

		Producto producto = productoService.obtenerUno(id).get();
		producto.setNombre(productoDto.getNombre());
		producto.setPrecio(productoDto.getPrecio());
		productoService.guardar(producto);
		return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") int id) {

		if (!productoService.existePorId(id))// valido que id del producto exista
			return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
		productoService.eliminar(id);
		return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);

	}
}
