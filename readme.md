# Challenge Movie

Android studio Artic Fox Kotlin 1.5.21

Challenge Movie es un codigo de ejemplo para prueba tecnica empresa
se tomo de base proyecto de mi autoria en github GreatMovies Kotlin MVVM
url: https://github.com/juacosoft/GreatsMoviesKotlinMVVM.

Los requerimientos solicitados se agregaron al proyecto.

Desarrollado en kotlin android Patron MVVM,retrofit2, RxAndroid, DaggerHilt, RxJava , ViewBinding,flows, api desde https://developers.themoviedb.org proceso de consumo de respuestas JSON

#- Consideraciones Basado en patron de diseño MVVM y programacion reactiva se realizo aplicacion online, la implementacion o sincronizacion de archivos de manera offline realizado con persistencia de datos DAO la clase AppDataBase (Extiende de RoomDatabase) Sincroniza los datos traidos desde la api y estos estarian disponibles para ser injectados en los viewmodels correspondientes interface MoviesDao contiene las sentencias SQLite pertinentes para: insercion de datos, consulta y actualizacion
Pruebas unitarias a Room con DaggerHiltTesting


#Dependencias usadas
    * // Fragment
    * implementation "androidx.fragment:fragment-ktx:1.4.0"
    * implementation 'androidx.navigation:navigation-fragment-ktx:2.3.5'
    * implementation 'androidx.navigation:navigation-ui-ktx:2.3.5'
    * // Activity
    * implementation "androidx.activity:activity-ktx:1.2.2"
    * // ViewModel
    * implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
    * // LiveData
    *implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"

    // Retrofit
    * implementation "com.squareup.retrofit2:retrofit:2.9.0"
    * implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    //Corrutinas
    * implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6'
    //daggerhilt
    * implementation "com.google.dagger:hilt-android:$hilt_version"
    * implementation 'androidx.test.ext:junit-ktx:1.1.3'
    * kapt "com.google.dagger:hilt-android-compiler:$hilt_version"

    //Room Library

    * implementation "androidx.room:room-ktx:2.3.0"
    * kapt "androidx.room:room-compiler:2.3.0"
    * kapt "android.arch.persistence.room:compiler:1.0.0-alpha4"

    //Glide
    * implementation 'com.github.bumptech.glide:glide:4.12.0'
    * annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'

    //Swipy
    * implementation "com.github.omadahealth:swipy:1.2.3@aar"

    //ratingbar
    * implementation 'me.zhanghai.android.materialratingbar:library:1.4.0'

    //youtube player
    * implementation 'com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.5'