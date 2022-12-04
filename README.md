**VINILOS**

Vinilos es una aplicación móvil que permite ofrecer a los coleccionistas y personas interesadas, grandes volúmenes de música recolectada desde esta misma.

**Requerimientos**

- Android Studio | última versión
- Android Gradle >= 7.3.1
- Gradle >= 7.4

**Instalación**

1. git clone al repositorio en su máquina local.
2. Use git para verificar que esté en la rama main
3. Abrir el proyecto en Android Studio

**Actualizar dependencias de Gradle**

1. Ir a la ventana de Gradle en la parte izaquieda de android studio.
2. Dar clic derecho sibre la opción de la ruta Vynils/app/debug
3. Seleccionar la opcion Reload Gradle Project
4. Seleccionar Run app.


**Ejecución de pruebas con Espresso**

1. Asegurarse que el proyecto esté sincronizado con el Gradle.
2. Ir a la ruta: src/androidTest/java/com/example/vynils/
3. Sobre el archivo Test.kt dar clic derecho.
4. Dar clic en run.


**APK para instalar en dispositivo físico**

El APK se encuentra en el directorio /apk de raíz del proyecto.

Ahora que tiene un archivo vynils.apk, debe instalarlo en su dispositivo Android.

Si  está configurado correctamente, la aplicación ahora debería aparecer en su lista de aplicaciones instaladas. Para enviar un .apk actualizado, debe desinstalarlo antes de ejecutar.
