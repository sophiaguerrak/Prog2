# OBLIGATORIO PROGRAMACIÓN 2

Guerra, Tauber, Ferra y Borges.

**Importante:** asegurarse que en SpotifyMgr la variable MYPATH sea el directorio donde se ubica el archivo universal_top_spotify_songs.csv

## Diagrama UML de la solución
![UMLProg2 drawio (1)](https://github.com/sophiaguerrak/Prog2/assets/132523742/57a51465-0fc1-4692-acc5-5e17454573d0)



## Procesos de carga

Para cargar todos los datos del CSV se utilizó la clase Scanner. Este se saltea la primera línea del csv, ya que es donde se encuentran todos los nombres de las categorías de los datos. Se itera sobre los datos mientras la próxima línea no sea vacía, y se utiliza un separador para las comillas y comas entre los datos y un separador para la coma de la primera variable. Una vez obtenidos todos los datos procedemos a agregar todos los países a una LinkedList para tener registro de todos los países que cuentan con Top50 en Spotify. También nos aseguramos que si la canción tiene varios artistas, que no los tome como uno único sino por separado. Todos los artistas se almacenan en una LinkedList. 

Para los Top50, en cambio, decidimos utilizar un Hash dentro de otro Hash de la siguiente forma: <Date, Hash<String, Top50>>. Esto significa que primero filtramos por fecha para obtener los Top50 de todos los países para una misma fecha, y luego utilizando la sigla del país se obtiene el Top50 de ese país en esa fecha específica. A la hora de agregar canciones va a fijarse en la fecha y en la sigla del país, y va a proceder a guardar esa canción en el array de 50 canciones de Top50.


## Decisiones tomadas

Se utilizó un Hash dentro de otro Hash para reducir el orden de ejecución para acceder al Top50 de un país en una fecha específica.

Se decidió, para la función obtener5CancionesMasRepetidas, recorrer el Top50 de cada país en esa fecha específica y contar las apariciones de cada canción. Se utiliza un HashMap para poder acceder a la cantidad de apariciones de cada canción de una manera más rápida. Por separado se creó un Array el cual se ordena una vez que termina de recorrer los Top50  y devuelve las 5 canciones con más apariciones de ese día (ordenados de forma descendiente). En la función obtenerAparicionesArtista también se siguió la misma lógica que para las apariciones de las canciones, solamente que al ser un único artista no es necesario utilizar un HashMap, con un único contador basta. 

El orden de ejecución de obtener5CancionesMasRepetidas es O(n) ya que no recorre todos los datos del csv, sino que recorre ambos Hash en O(1), cada playlist tiene un orden de O(50)=O(1), y luego como debe recorrer esto para cada país sería O(n) el total.

Para obtenerTop7Artistas en un rango de fechas se utilizó el mismo razonamiento, solo que iterando para cada una de las fechas del rango. Para esto creamos una función auxiliar que recibe como entrada la fecha de inicio y de fin como String, y nos devuelve un array con todas las fechas (desde el día de inicio hasta el día de fin) utilizando la clase Calendar. Se utilizó el mismo razonamiento para obtenerCancionesTempoEspecifico, cambiando la condición a que el tempo estuviera entre dos valores específicos para aumentar el contador en uno.

Para obtenerTop10Canciones basta con acceder al Top50 del país ingresado para la fecha especificada y devolver los primeros 10 valores.

## Consumo de memoria y tiempo de ejecución

El consumo de memoria y tiempo de ejecución fueron incluidos como parte del código. Luego de invocar a cada función, se devuelve el tiempo de ejecución en milisegundos y también se devuelve la memoria máxima, la memoria total, la memoria libre y la memoria utilizada (memoria total - memoria libre). 

Se obtuvieron estos valores en promedio: 

 - *obtenerTop10Canciones:*
La función tomó 12 milisegundos en ejecutarse. 
Memoria máxima: 4096MB.
Memoria total: 990MB.
Memoria libre: 769MB. 
Memoria usada: 220MB.

 - *obtener5CancionesMasRepetidas:*
La función tomó 12 milisegundos en ejecutarse.
Memoria máxima: 4096MB
Memoria total: 978MB
Memoria libre: 566MB
Memoria usada: 411MB.
 
  - *obtenerTop7Artistas*
 La funcion tomo 26 milisegundos en ejecutarse.
Memoria máxima: 4096MB.
Memoria total: 990MB.
Memoria libre: 768MB.
Memoria usada: 221MB.

 - *obtenerAparicionesArtista*
 La funcion tomo 5 milisegundos en ejecutarse.
Memoria máxima: 4096MB.
Memoria total: 990MB.
Memoria libre: 767MB.
Memoria usada: 222MB.

 - *obtenerCancionesTempoEspecifico*
La funcion tomo 6581 milisegundos en ejecutarse.
Memoria máxima: 4096MB.
Memoria total: 990MB.
Memoria libre: 767MB.
Memoria usada: 222MB.
