# File_Encrypter_Decrypter

**Función:**

El programa se encarga de cifrar y decifrar archivos a partir de una clave proporcionada, utilizando estandar de 
cifrado AES (Advanced Encryption Standard) y obteniendo como resultado un archivo cifrado o decifrado ya sea el caso correspondiente.

**Desarrollo del programa:**

El porgrama se desarrollo utilizando Java versión 11 y el paquete javax.crypto para realizar las funciones y 
operaciones criptograficas necesarias. Por otro lado, este programa permite almacenar hash SHA256 en el archivo encriptado.

**Nota**: Si el archivo que ha sido encriptado es modificado el algoritmo AES no podra validar el archivo.


**Problemas:** 

Durante el desarrollo del proyecto se presentaron problemas para entender y aplicar el manejo de bytes de los archivos.

**Conclusiones:**

+ El cifrado y decifrado de archivos es extremadamente util para aplicaciones que manejan datos confidenciales o requieren 
seguridad de datos rigurosa.

+ El algoritmo AES es un cifrado de bloque simétrico que puede cifrar y descifrar datos. Ya que, este convierte los datos (texto legible) 
en un formato ininteligible denominado texto cifrado, mientras que el descifrado convierte este texto cifrado en texto legible (original)

+ Una función hash funciona en una sola dirección, esto quiere decir que de cualquier contenido podemos generar su hash, pero de un hash
no hay forma de generar el contenido asociado a él, salvo probando al azar hasta dar con el contenido. Por este motivo, SHA-256 es una de 
las funciones más usados por su equilibrio entre seguridad y coste computacional de generación, pues es un algoritmo muy eficiente para 
la alta resistencia de colisión que tiene.
***


**Instructivo de uso del programa:**


**Función 1 (Cifrar archivo):** 


![Inicio de porgrama](https://github.com/DSMontoyaP/File_Encrypter_Decrypter.git/images/inicioApp.png)


al ingresar al programa se le solicitara que cargue un archivo en texto legible y una contraseña para cifrar


+ Se selecciona el archivo que desea cifrar


+ Se ingresa la contraseña que su utilizara para cifrar.


+ Obtendra un archivo con la información encriptada


**Función 2 (Decifrar archivo):** 


Se le solicita cargar un archivo que contenga información encriptada y la contraseña que se utilizo para cifrarlo


+ Se selecciona y carga el archivo enciptado 


+ Ingresa la contraseña que utilizo para cifrar


+ Obtiene el archivo decifrado y se muestran los valores hash en color verde si el proceso concluyó de forma correcta 
