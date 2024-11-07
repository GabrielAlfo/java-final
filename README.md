POST http://localhost:8080/clientes/agregar
{
  "nombre": "aa",
  "apellido": "aa",
  "dni": 12,
  "isactivo": true,
  "reputacion": 5
}

GET http://localhost:8080/clientes/buscar/1
GET http://localhost:8080/clientes
POST http://localhost:8080/productos/agregar
{
  "nombre": "aa",
  "precio": 100,
  "stock": 400
}

GET http://localhost:8080/productos/buscar/1
GET http://localhost:8080/productos/listar
POST http://localhost:8080/ventas/agregar
{
  "fecha": "2024-15-10",
  "total": 100,
  "cliente": {
    "id": 1
  }
}
GET http://localhost:8080/ventas/buscar/1
GET http://localhost:8080/ventas/listar
POST http://localhost:8080/venta-productos/agregar
{
  "venta": {
    "id": 1
  },
  "producto": {
    "id": 1
  },
  "cantidad": 3
}
GET http://localhost:8080/venta-productos/buscar/1
GET http://localhost:8080/venta-productos/listar
DELETE http://localhost:8080/venta-productos/eliminar/1
