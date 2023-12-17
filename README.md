# StoreApiApp
![Logo_de_la_App](C:\Users\Yo\Downloads\DALL·E 2023-12-17 15.10.54 - A sleek and modern logo for an online shopping application. The logo should feature a stylized shopping cart, incorporating elements of digital techno.png)

## Requisitos
1. Consuma datos de internet. Se valorará que se consuma más de un recurso .
2. Use una BBDD local para persistir los datos. Debe tener tablas para los datos consumidos y alguna tabla adicional que sirva para crear modelos únicamente en local. Por ejemplo, para la APP de Pokémon deberíamos tener una tabla para los Pokémon y, podríamos, crear otra que me permitiera almacenar Equipos de Pokémon
3. La aplicación debe tener funcionalidad cuando pierda la conectividad a la red
4. Debe contener al menos 4 fragmentos con las siguientes características:
   4.1 Un RecyclerView que muestre datos de internet
   4.2 Una vista que muestre un detalle
   4.3. Otro RecyclerView que muestre algunos de los datos locales modelados
   4.3 Un fragmento que permita crear algún dato local
5. Debe hacer uso de algún intent implícito y de navegaciones internas (ya sea con intents o el componente de navegación)
6. Se valorará que visualmente se haga uso de Material Design y la interfaz de usuario sea atractiva
7. Se debe usar ViewModel y algún tipo de observable ya sea StateFlow como LiveData
8. Se deben usar corutinas para la mejora de la experiencia de usuario cuando así se requiera
9. Se valorará el uso de otras librerías como Coil, Glide o Hilt
10. La aplicación debe soportar la localización y se valorará que este en al menos dos idiomas

## Apartados de la Rúbrica
- Consumo de Datos de Internet: Utilizo una Api en la que uso solamente un recurso
- Uso de Base de Datos Local: He creado una base de datos local con los datos de la api y añadiendo mas campos, ademas de crear otra Entidad
- Funcionalidad sin Conectividad: La app funciona sin necesidad de Internet
- Fragmentos y Caracterásticas: Uso 4 fragmentos: Lista de productos, Detalle de los productos, Lista de productos en el carrito y para cambiar el nombre del carrito
- Uso de Intents y Navegación: Utilizo el intent para compartir la lista de productos del carrito, además de tener varias navegaciones
- Material Design y Atractivo Visual: He cambiado los colores, tanto para el modo oscuro y claro. He añadido botones con iconos intuitivos, además el contraste de los colores es correcto
- Uso de ViewModel y Observables: A lo largo de toda la App uso ViewModel, StateFlow, y Flow
- Uso de Corrutinas: He usado corrutinas por la mayor parte de la App para mejorar la experiencia del Usuario
- Uso de Librerías Adicionales: Se usan librerias como Hilt, Coil, Retrofit y Room.
- Soporte de Localización: La App detecta la localización y cambiará el idioma, se disponen de 6 idiomas distintos: español, catalan, gallego, inglés, francés y alemán


## Función de la App
La App consume productos de una tienda, por lo que la App trata de simular la gestión del carrito en una Tienda, al inicio sale la lista de productos y al clickar sobre ellos nos saldrá la información del producto y la opción de añadirlo al carrito, mediante el menú nos podremos desplazar al carrito donde nos saldrá la lista de productos y podremos cambiar su cantidad, ademas podremos cambiar el nombre dle carrito y compartir la lista de los productos
