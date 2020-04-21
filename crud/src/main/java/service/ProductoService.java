package service;

import org.springframework.transaction.annotation.*;
import entity.Producto;
import repository.ProductoRepository;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductoService {

	@Autowired
	ProductoRepository productoRepository;

	// Trae todos los registros
	public List<Producto> listar() {
		return productoRepository.findAll();
	}

	// Trae un registro especificado por id
	public Optional<Producto> obtenerUno(int id) {
		return productoRepository.findById(id);
	}

	// Trae un registro especificado por el nombre
	public Optional<Producto> obtenerPorNombre(String nombre) {
		return productoRepository.findByNombre(nombre);
	}

	// Guardar el producto
	public void guardar(Producto producto) {
		productoRepository.save(producto);
	}

	// Eliminar el producto
	public void eliminar(int id) {
		productoRepository.deleteById(id);
	}

	// detecta si existe un producto por id especifico
	public boolean existePorId(int id) {
		return productoRepository.existsById(id);
	}

	// detecta si existe un producto por nombre
	public boolean existePorNombre(String nombre) {
		return productoRepository.existsByNombre(nombre);
	}

}
