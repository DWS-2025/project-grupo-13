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
Eliminación de herencia equipment, creación de la base de datos, adición de DTOs, corrección de errores sobre todo en el AdminController
#### 5 archivos más modificados:
`AdminController.java`, `header.html`, `ArmorService.java`, `Weapon.java`,`WeaponService.java`.
#### 5 commits destacados:
https://github.com/DWS-2025/project-grupo-13/commit/8bab4da0ed9483380d1359565beab7fbd0453d1b

https://github.com/DWS-2025/project-grupo-13/commit/6db2c727396ecd90ddef913a8bbaa0f9b79faba7

https://github.com/DWS-2025/project-grupo-13/commit/7e11e3eed3092e27b1d90885f02c97f24ae65078

https://github.com/DWS-2025/project-grupo-13/commit/3087aedeed0062243bbdeca4b8e717dbddfc200a

https://github.com/DWS-2025/project-grupo-13/commit/1e46a3b3e2c2ccdc55dc177cb4bb7736471b92a4

### Lucas
Muchas soluciones de errores, implementación base de datos de character y de la api rest, así como edición de los servicios para adaptarlos a los cambios que van saliendo

#### 5 archivos más modificados:
`listing.html`, `characterService.java`, `sessionController`,`index.html`,`restController.java`
#### 5 commits destacados:
https://github.com/DWS-2025/project-grupo-13/commit/6f153b981393b80b23f9c5108f0fbac36a08a316

https://github.com/DWS-2025/project-grupo-13/commit/01ae246e87606bd00cfbdea50f0f95146e62ae22

https://github.com/DWS-2025/project-grupo-13/commit/cd9d74e4fe4ab563405a3887f3b93471480a63de

https://github.com/DWS-2025/project-grupo-13/commit/753fb16195c1a28d4cdcff8584226d051a431a9d

https://github.com/DWS-2025/project-grupo-13/commit/f9314ad71f3ee161ab792c5d29711cb4367cffa3


### Eduardo
Comentarios, homogeneizar el formato (saltos de línea, espacios entre métodos...), revisión de errores en el código y base de datos, paginación de la tienda y separación entre weapon y armor de la tienda.
#### 5 archivos más modificados:
`listing_armors.html`, `listing_weapons.html`, `sessionController.java`, `User.java`,`WeaponService`.

#### 5 commits destacados
https://github.com/DWS-2025/project-grupo-13/commit/4db88a1f3f191f0e63d9cb1be47086e70661ea44

https://github.com/DWS-2025/project-grupo-13/commit/5f7699fc83891576ea5e81531aacff0cb965d100

https://github.com/DWS-2025/project-grupo-13/commit/4a5d344

https://github.com/DWS-2025/project-grupo-13/commit/1774d86f5e0d8ae33e9a71ae81fc3e8ef07c407b

https://github.com/DWS-2025/project-grupo-13/commit/0d11dee
