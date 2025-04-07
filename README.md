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
![Diagrama de base de datos](grupo13/images/diagrama.png)

## Trabajo de cada integrante
### Pablo
Eliminacion de herencia en equipment, traspaso de varias clases a BBDD, readme.md, consultas dinámicas js en equipment manager del /admin, consultas en postman, correción de errores
#### 5 archivos más modificados:
`AdminController.java`, `editArmor.html`, `weaponService.java`, `eqmanager.js`,`readme.txt`.

#### 5 commits destacados
ELIMINACION DE HERENCIA (BRANCH ELIMINADA)

https://github.com/DWS-2025/project-grupo-13/commit/1397ea401d59cbd9361fb1879256d7f1258f8095

https://github.com/DWS-2025/project-grupo-13/commit/5e83b41b63eb1a8dc3306829b6a1bc7ef3b89f58

https://github.com/DWS-2025/project-grupo-13/commit/4acfd93e62a96c380ea046ffd31f6416a2755cac

https://github.com/DWS-2025/project-grupo-13/commit/a00edba25a4756915fcc4d665fb7b8c34d063256

### Andrea

### Lucas

### Eduardo