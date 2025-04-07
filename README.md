[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/Jd7ILUgB)

# Dado 13

| Nombre | Correo universitario | Github |
|--------|----------------------|--------|
| Pablo Ratón Macías | p.raton.2023@alumnos.urjc.es | praton2023 |
| Andrea Isabel Rodriguez Ochoa | a.rodriguez.2023@alumnos.urjc.es | dreamrcurio |
| Lucas Lotti Villar | l.lotti.2023@alumnos.urjc.es | LotusLupe |
| Eduardo Moreno Arollo | e.moreno.2023@alumnos.urjc.es | E-ed-bot |

## Entidades:
- **User**
- **Character**
- **Weapon**
- **Armor**

## Relaciones:
- **User - Character:** 1/1  
- **User - Weapon:** 1/N  
- **User - Armor:** 1/N  
- **Character - Weapon:** 1/1  
- **Character - Armor:** 1/1  

## Tipos de usuario y permisos

### Por defecto:  
Usuario sin registrar, interactúa mínimamente con la página. No puede comprar o equipar objetos, pero sí puede ver los objetos de la tienda o el perfil del resto de jugadores.

### Jugador:  
Usuario registrado, puede obtener monedas, comprar objetos, equiparlos y actualizar su perfil.

### Administrador:  
Usuario con permisos elevados, puede acceder al `/admin` para crear objetos o modificarlos, además de acceder y modificar el perfil o inventario de los usuarios.

## Imágenes en entidades
|Entidad |   Imagenes   |
| ------------ | ------------ |
|  User | 0  |   
| Character   | 1  |   
|  Weapon |  1 |   
|  Armor |  1 |   
## Diagrama de la BBDD
![Diagrama de base de datos](images/diagrama.png)


